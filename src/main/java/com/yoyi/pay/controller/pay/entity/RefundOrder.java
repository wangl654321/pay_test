package com.yoyi.pay.controller.pay.entity;

import java.math.BigDecimal;

/***
 *
 *
 * 描    述：支付退款
 *
 * 创 建 者：@author wl
 * 创建时间：2019/4/1811:06
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
public class RefundOrder {

    /**
     * 商户代码
     */
    private String merchantId;

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 退款流水号
     */
    private String tranSerialNo;

    /**
     * 退款总金额
     */
    private BigDecimal orderAmt;

    /**
     * 支付币种
     */
    private String curType;

    /**
     * 订单处理状态
     */
    private String tranStat;

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getTranSerialNo() {
        return tranSerialNo;
    }

    public void setTranSerialNo(String tranSerialNo) {
        this.tranSerialNo = tranSerialNo;
    }

    public BigDecimal getOrderAmt() {
        return orderAmt;
    }

    public void setOrderAmt(BigDecimal orderAmt) {
        this.orderAmt = orderAmt;
    }

    public String getCurType() {
        return curType;
    }

    public void setCurType(String curType) {
        this.curType = curType;
    }

    public String getTranStat() {
        return tranStat;
    }

    public void setTranStat(String tranStat) {
        this.tranStat = tranStat;
    }

    @Override
    public String toString() {
        return "RefundOrder{" +
                "merchantId='" + merchantId + '\'' +
                ", orderNo='" + orderNo + '\'' +
                ", tranSerialNo='" + tranSerialNo + '\'' +
                ", orderAmt='" + orderAmt + '\'' +
                ", curType='" + curType + '\'' +
                ", tranStat='" + tranStat + '\'' +
                '}';
    }
}
