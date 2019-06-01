package com.yoyi.pay.controller;

import java.io.Serializable;

/***
 *
 *
 * 描    述：甬易通知
 *
 * 创 建 者：@author wl
 * 创建时间：2019/1/2310:43
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
public class Notify implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 接口名称
     */
    private String interfaceName;

    /**
     * 版本号
     */
    private String version;

    /**
     * 通知结果数据
     */
    private String tranData;

    /**
     * 甬易对通知结果的签名数据
     */
    private String signData;

    /**
     * 主商户号
     */
    private String merchantId;

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getTranData() {
        return tranData;
    }

    public void setTranData(String tranData) {
        this.tranData = tranData;
    }

    public String getSignData() {
        return signData;
    }

    public void setSignData(String signData) {
        this.signData = signData;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    @Override
    public String toString() {
        return "TransFer{" +
                "interfaceName='" + interfaceName + '\'' +
                ", version='" + version + '\'' +
                ", tranData='" + tranData + '\'' +
                ", signData='" + signData + '\'' +
                ", merchantId='" + merchantId + '\'' +
                '}';
    }
}
