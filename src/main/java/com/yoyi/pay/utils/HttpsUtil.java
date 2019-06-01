package com.yoyi.pay.utils;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.*;
import java.io.IOException;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/***
 *
 *
 * 描 述：绕过证书认证的https请求
 *
 * 创 建 者： @author W.X
 * 创建时间： 2016年9月28日 下午7:50:28
 * 创建描述：
 *
 * 修 改 者：
 * 修改时间：
 * 修改描述：
 *
 * 审 核 者：
 * 审核时间：
 * 审核描述：
 *
 */
public class HttpsUtil {

    /**
     * 定义全局日志
     */

    /**
     * 将最大连接数增加到200
     */
    private int maxTotalConnect = 200;

    /**
     * 将每个路由基础的连接数增加到20
     */
    private int defaultMaxPerRoute = 100;

    /**
     * 请求时间更改为30秒（连接一个url的连接等待时间）
     */
    private int requestTimeout = 30000;

    /**
     * 连接超时时间为10秒（获取response的返回等待时间）
     */
    private int timeout = 30000;

    private SSLConnectionSocketFactory socketFactory = null;

    /**
     * 请求配置
     */
    private RequestConfig requestConfig = null;

    private CloseableHttpClient client = null;

    private PoolingHttpClientConnectionManager cm = null;

    private static final HttpsUtil withoutAuthHttpsClient = new HttpsUtil();

    private HttpsUtil() {

        final HostnameVerifier hostnameVerifier = new HostnameVerifier() {
            @Override
            public boolean verify(String s, SSLSession sslSession) {
                return true;
            }
        };
        System.setProperty("jsse.enableSNIExtension", "false");
        socketFactory = new SSLConnectionSocketFactory(createIgnoreVerifySSL(), hostnameVerifier);
        // 初始化RequestConfig 创建可用的scheme
        requestConfig = RequestConfig
                .custom()
                .setCookieSpec(CookieSpecs.STANDARD_STRICT)
                .setExpectContinueEnabled(true)
                .setTargetPreferredAuthSchemes(Arrays.asList(AuthSchemes.NTLM, AuthSchemes.DIGEST))
                .setProxyPreferredAuthSchemes(Arrays.asList(AuthSchemes.BASIC))
                .setConnectionRequestTimeout(requestTimeout)
                .setConnectTimeout(timeout).build();
        // 创建和连接套接字的连接工厂
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder
                .<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.INSTANCE)
                .register("https", socketFactory).build();
        // 初始化连接管理
        cm = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        cm.setDefaultMaxPerRoute(defaultMaxPerRoute);
        cm.setMaxTotal(maxTotalConnect);
        // 创建httpclient
        client = HttpClients.custom().setConnectionManager(cm).setDefaultRequestConfig(requestConfig).build();
    }

    /**
     * 绕过验证
     *
     * @return
     * @throws Exception
     */
    private SSLContext createIgnoreVerifySSL() {

        final X509Certificate[] _AcceptedIssuers = new X509Certificate[]{};
        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("TLS");
            // 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法
            X509TrustManager tm = new X509TrustManager() {
                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return _AcceptedIssuers;
                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }
            };
            sslContext.init(null, new TrustManager[]{tm}, new SecureRandom());
        } catch (Exception e) {
        }
        return sslContext;
    }

    /**
     * 对外暴露获取实例的接口
     *
     * @return
     */
    public synchronized static HttpsUtil getInstance() {
        return withoutAuthHttpsClient;
    }

    /**
     * 请求
     *
     * @param url
     * @param params
     * @param charset
     * @param headers
     * @return
     * @throws IOException
     */
    public String call(String url, String params, String charset, Header... headers) {

        // 开始时间
        String realCharset = (charset == null) ? "UTF-8" : charset;
        CloseableHttpResponse response = null;
        String content = "";
        try {
            HttpPost post = new HttpPost(url);
            StringEntity entity = new StringEntity(params, realCharset);
            post.setEntity(entity);
            post.setConfig(requestConfig);
            if (headers != null) {
                post.setHeaders(headers);
            }
            response = client.execute(post);
            content = EntityUtils.toString(response.getEntity(), realCharset);
        } catch (Exception e) {
            System.out.println(e);
        }
        return content;
    }

    /**
     * POST发送
     *
     * @param url
     * @param params
     * @param charset
     * @return
     * @throws Exception
     */
    public String post(String url, Map<String, String> params, String charset) {

        String realReqCharset = (charset == null) ? "UTF-8" : charset;
        // 开始时间
        HttpPost post = new HttpPost(url);
        // url格式编码
        UrlEncodedFormEntity uefEntity = null;
        // 创建参数列表
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        CloseableHttpResponse response = null;
        String content = "";
        try {
            if (null != params) {
                for (Map.Entry<String, String> entity : params.entrySet()) {
                    list.add(new BasicNameValuePair(entity.getKey(), entity.getValue()));
                }
                uefEntity = new UrlEncodedFormEntity(list, realReqCharset);
                post.setEntity(uefEntity);
            }
            System.out.println("POST 请求...." + post.getURI());
            response = client.execute(post);
            // 结束时间
            content = EntityUtils.toString(response.getEntity(), realReqCharset);
        } catch (Exception e) {
            System.out.println("POST发送异常 " + e);
        }
        return content;
    }


    /**
     * get发送
     *
     * @param url
     * @param params
     * @return
     */
    public String get(String url, Map<String, String> params, Header... headers) {

        // 开始时间
        StringBuilder stringBuilder = new StringBuilder();
        CloseableHttpResponse response = null;
        String content = "";
        try {
            // 创建参数列表
            if (null != params) {
                for (Map.Entry<String, String> entity : params.entrySet()) {
                    String value = entity.getValue();
                    if (null != value && "" != value) {
                        if (stringBuilder.length() == 0) {
                            stringBuilder.append("?");
                        } else {
                            stringBuilder.append("&");
                        }
                        stringBuilder.append(entity.getKey() + "=");
                        stringBuilder.append(URLEncoder.encode(entity.getValue(), "UTF-8"));
                    }
                }
            }
            url = url + stringBuilder.toString();
            HttpGet httpGet = new HttpGet(url);
            if (headers != null) {
                httpGet.setHeaders(headers);
            }
            response = client.execute(httpGet);
            // 结束时间
            content = EntityUtils.toString(response.getEntity(), "UTF-8");
        } catch (Exception e) {
            System.out.println("get发送异常 " + e);
        }
        return content;
    }

    public int getMaxTotalConnect() {
        return maxTotalConnect;
    }

    public void setMaxTotalConnect(int maxTotalConnect) {
        this.maxTotalConnect = maxTotalConnect;
    }

    public int getDefaultMaxPerRoute() {
        return defaultMaxPerRoute;
    }

    public void setDefaultMaxPerRoute(int defaultMaxPerRoute) {
        this.defaultMaxPerRoute = defaultMaxPerRoute;
    }

    public int getRequestTimeout() {
        return requestTimeout;
    }

    public void setRequestTimeout(int requestTimeout) {
        this.requestTimeout = requestTimeout;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public RequestConfig getRequestConfig() {
        return requestConfig;
    }

    public void setRequestConfig(RequestConfig requestConfig) {
        this.requestConfig = requestConfig;
    }
}