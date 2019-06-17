package com.yoyi.pay.controller.file;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/***
 *
 *
 * 描    述：支付请求公共常量类
 *
 * 创 建 者： @author wl
 * 创建时间： 2019/3/28 11:30
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
@Configuration
@PropertySource(value = "classpath:${spring.profiles.active}/resource.properties")
public class FtpUtils {

    /**
     * 远程地址
     */
    @Value("${ftp_ip}")
    private String ftpIp;

    /**
     * 端口
     */
    @Value("${ftp_port}")
    private int ftpPort;

    /**
     * 文件路径
     */
    @Value("${ftp_path}")
    private String ftpPath;

    /**
     * 商户号
     */
    @Value("${ftp_name}")
    private String ftpName;

    /**
     * 密码
     */
    @Value("${ftp_password}")
    private String ftpPassword;

    public String getFtpIp() {
        return ftpIp;
    }

    public int getFtpPort() {
        return ftpPort;
    }

    public String getFtpPath() {
        return ftpPath;
    }

    public String getFtpName() {
        return ftpName;
    }

    public String getFtpPassword() {
        return ftpPassword;
    }
}
