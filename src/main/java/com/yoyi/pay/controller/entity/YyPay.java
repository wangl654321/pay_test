package com.yoyi.pay.controller.entity;

/***
 *
 *
 * 描    述: yy_pay 甬易对账文件表
 *
 * 创 建 者: @author wl
 * 创建时间: 2019/04/15 15:27
 * 创建描述: 
 *
 * 修 改 者: 
 * 修改时间: 
 * 修改描述: 
 *
 * 审 核 者: 
 * 审核时间: 
 * 审核描述: 
 *
 */
public class YyPay {

    /**
     * 主键
     */
    private Integer pid;

    /**
     * 商户订单号
     */
    private String orderNo;

    /**
     * 支付流水号
     */
    private String flowNo;

    /**
     * 订单金额
     */
    private Double orderAmt;

    /**
     * 币种
     */
    private String curType;

    /**
     * 支付成功时间
     */
    private String tradeTime;

    /**
     * 订单处理状态(0:未支付,1:已支付；,2:支付失败,31:退款成功（退甬易宝）,37:打款成功(退银行卡))
     */
    private String status;

    /**
     * 订单类型(10:支付,30:退款)
     */
    private String type;

    /**
     * 通道编码
     */
    private String bankId;

    /**
     * 通道名称
     */
    private String bankName;

    /**
     * 交易人姓名
     */
    private String customerName;

    /**
     * 交易人身份证号码
     */
    private String customerNo;

    /**
     * 交易手续费
     */
    private Double fee;

    /**
     * 退款订单号
     */
    private String refundNo;

    /**
     * 主键
     * @return pid 主键
     */
    public Integer getPid() {
        return pid;
    }

    /**
     * 主键
     * @param pid 主键
     */
    public void setPid(Integer pid) {
        this.pid = pid;
    }

    /**
     * 商户订单号
     * @return order_no 商户订单号
     */
    public String getOrderNo() {
        return orderNo;
    }

    /**
     * 商户订单号
     * @param orderNo 商户订单号
     */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    /**
     * 支付流水号
     * @return flow_no 支付流水号
     */
    public String getFlowNo() {
        return flowNo;
    }

    /**
     * 支付流水号
     * @param flowNo 支付流水号
     */
    public void setFlowNo(String flowNo) {
        this.flowNo = flowNo == null ? null : flowNo.trim();
    }

    /**
     * 订单金额
     * @return order_amt 订单金额
     */
    public Double getOrderAmt() {
        return orderAmt;
    }

    /**
     * 订单金额
     * @param orderAmt 订单金额
     */
    public void setOrderAmt(Double orderAmt) {
        this.orderAmt = orderAmt;
    }

    /**
     * 币种
     * @return cur_type 币种
     */
    public String getCurType() {
        return curType;
    }

    /**
     * 币种
     * @param curType 币种
     */
    public void setCurType(String curType) {
        this.curType = curType == null ? null : curType.trim();
    }

    /**
     * 支付成功时间
     * @return trade_time 支付成功时间
     */
    public String getTradeTime() {
        return tradeTime;
    }

    /**
     * 支付成功时间
     * @param tradeTime 支付成功时间
     */
    public void setTradeTime(String tradeTime) {
        this.tradeTime = tradeTime == null ? null : tradeTime.trim();
    }

    /**
     * 订单处理状态(0:未支付,1:已支付；,2:支付失败,31:退款成功（退甬易宝）,37:打款成功(退银行卡))
     * @return status 订单处理状态(0:未支付,1:已支付；,2:支付失败,31:退款成功（退甬易宝）,37:打款成功(退银行卡))
     */
    public String getStatus() {
        return status;
    }

    /**
     * 订单处理状态(0:未支付,1:已支付；,2:支付失败,31:退款成功（退甬易宝）,37:打款成功(退银行卡))
     * @param status 订单处理状态(0:未支付,1:已支付；,2:支付失败,31:退款成功（退甬易宝）,37:打款成功(退银行卡))
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * 订单类型(10:支付,30:退款)
     * @return type 订单类型(10:支付,30:退款)
     */
    public String getType() {
        return type;
    }

    /**
     * 订单类型(10:支付,30:退款)
     * @param type 订单类型(10:支付,30:退款)
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * 通道编码
     * @return bank_id 通道编码
     */
    public String getBankId() {
        return bankId;
    }

    /**
     * 通道编码
     * @param bankId 通道编码
     */
    public void setBankId(String bankId) {
        this.bankId = bankId == null ? null : bankId.trim();
    }

    /**
     * 通道名称
     * @return bank_name 通道名称
     */
    public String getBankName() {
        return bankName;
    }

    /**
     * 通道名称
     * @param bankName 通道名称
     */
    public void setBankName(String bankName) {
        this.bankName = bankName == null ? null : bankName.trim();
    }

    /**
     * 交易人姓名
     * @return customer_name 交易人姓名
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * 交易人姓名
     * @param customerName 交易人姓名
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName == null ? null : customerName.trim();
    }

    /**
     * 交易人身份证号码
     * @return customer_no 交易人身份证号码
     */
    public String getCustomerNo() {
        return customerNo;
    }

    /**
     * 交易人身份证号码
     * @param customerNo 交易人身份证号码
     */
    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo == null ? null : customerNo.trim();
    }

    /**
     * 交易手续费
     * @return fee 交易手续费
     */
    public Double getFee() {
        return fee;
    }

    /**
     * 交易手续费
     * @param fee 交易手续费
     */
    public void setFee(Double fee) {
        this.fee = fee;
    }

    /**
     * 退款订单号
     * @return refund_no 退款订单号
     */
    public String getRefundNo() {
        return refundNo;
    }

    /**
     * 退款订单号
     * @param refundNo 退款订单号
     */
    public void setRefundNo(String refundNo) {
        this.refundNo = refundNo == null ? null : refundNo.trim();
    }
}