package com.yoyi.pay.utils;

import java.math.BigDecimal;

/***
 *
 *
 * 描    述：
 *
 * 创 建 者：@author wl
 * 创建时间：2019/4/1714:59
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
public class BalanceQuery {

    /**
     * 商户代码
     */
    private String merchantId;

    /**
     * 可用余额
     */
    private BigDecimal avaBalance;

    /**
     * 总余额
     */
    private BigDecimal balance;

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public BigDecimal getAvaBalance() {
        return avaBalance;
    }

    public void setAvaBalance(BigDecimal avaBalance) {
        this.avaBalance = avaBalance;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "BalanceQuery{" +
                "merchantId='" + merchantId + '\'' +
                ", avaBalance=" + avaBalance +
                ", balance=" + balance +
                '}';
    }
}
