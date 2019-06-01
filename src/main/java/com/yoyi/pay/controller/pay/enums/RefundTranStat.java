package com.yoyi.pay.controller.pay.enums;

import java.util.HashMap;
import java.util.Map;

/***
 *
 *
 * 描    述：订单处理状态
 *
 * 创 建 者： @author wl
 * 创建时间： 2019/4/18 11:11
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
public enum RefundTranStat {

    /**
     *退款成功
     */
    REFUND_TRAN_STAT_31("31", "退款成功"),

    /**
     *退款失败
     */
    REFUND_TRAN_STAT_32("32", "退款失败"),

    /**
     *退款状态可疑
     */
    REFUND_TRAN_STAT_33("33", "退款状态可疑"),

    /**
     *待审核
     */
    REFUND_TRAN_STAT_34("34", "待审核"),

    /**
     *内管审核通过
     */
    REFUND_TRAN_STAT_35("35", "内管审核通过"),

    /**
     *内管审核拒绝
     */
    REFUND_TRAN_STAT_36("36", "内管审核拒绝"),

    /**
     *打款成功(退银行卡)
     */
    REFUND_TRAN_STAT_37("37", "打款成功(退银行卡)"),

    /**
     *打款失败(退银行卡)
     */
    REFUND_TRAN_STAT_38("38", "打款失败(退银行卡)"),

    /**
     *待审核(担保)
     */
    REFUND_TRAN_STAT_40("40", "待审核(担保)"),

    /**
     *内管审核通过(担保)
     */
    REFUND_TRAN_STAT_43("43", "内管审核通过(担保)"),

    /**
     *内管审核拒绝(担保)
     */
    REFUND_TRAN_STAT_44("44", "内管审核拒绝(担保)");

    String _value;

    /**
     * 存放内容
     */
    String _Content;

    /**
     * 私有构造函数
     * @param value 枚举值
     * @param content 缓存内容
     * @return
     */
    RefundTranStat(String value, String content) {
        this._value = value;
        this._Content = content;
    }


    /**
     * 取得枚举对象值
     * @return 枚举对象值
     */
    public String getValue() {
        return this._value;
    }

    /**
     * 取得内容
     * @return 内容
     */
    public String getContent() {
        return this._Content;
    }

    /**
     * 根据值取得内容
     * @return 内容
     */
    public static RefundTranStat getBean(String value) {
        for (RefundTranStat e : RefundTranStat.values()) {
            if (value.equals(e.getValue())) {
                return e;
            }
        }
        return null;
    }

    public static String labelOf(String val) {
        if (getBean(val) != null) {
            return getBean(val).getContent();
        }
        return null;
    }

    private static final Map<String, String> lookup = new HashMap<>();

    static {
        for (RefundTranStat e : RefundTranStat.values()) {
            lookup.put(e.getContent(), e.getValue());
        }
    }

    public static String getLabel(String value) {
        return lookup.get(value);
    }
}
