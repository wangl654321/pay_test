package com.yoyi.pay.utils;

/***
 *
 *
 * 描    述：汇款充值xml转entity
 *
 * 创 建 者： @author wl
 * 创建时间： 2019/1/23 15:09
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
public class BankRow {

    /**
     * 通道名称
     */
    private String bankName;

    /**
     * 通道代码
     */
    private String bankID;

    /**
     * 跨行代码
     */
    private String otherBankID;

    /**
     * 支持的银行卡类型 01：借记卡 02：信用卡 X：借记/信用卡都支持
     */
    private String cardType;

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankID() {
        return bankID;
    }

    public void setBankID(String bankID) {
        this.bankID = bankID;
    }

    public String getOtherBankID() {
        return otherBankID;
    }

    public void setOtherBankID(String otherBankID) {
        this.otherBankID = otherBankID;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    @Override
    public String toString() {
        return "BankRow [bankName=" + bankName + ", bankID=" + bankID + ", otherBankID=" + otherBankID + ", cardType=" + cardType + "]";
    }

}
