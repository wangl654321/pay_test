package com.yoyi.pay.controller;

import com.yoyi.pay.utils.YoYiPayUtil;

import java.util.Map;

/***
 *
 *
 * 描    述：
 *
 * 创 建 者：@author wl
 * 创建时间：2019/4/210:41
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
public class Money {
    public static void main(String[] args) throws Exception {
        String xml = "<?xml version=\"1.0\" encoding=\"GBK\" ?><B2CRes><returnCode>0000</returnCode><returnMsg>查询成功</returnMsg><customerId>C100013405</customerId><bankList><bankAcc><accountNo>68794945687897487</accountNo><company>枫林晚宾馆</company><cityNo>3310</cityNo><provinceCode>3310</provinceCode><unionBankNo>323331000001</unionBankNo><sonName>浙江网商银行股份有限公司</sonName><bankId>323</bankId><settleFlag>0</settleFlag></bankAcc><bankAcc><accountNo>6879494568789798</accountNo><company>枫林晚宾馆</company><cityNo>3310</cityNo><provinceCode>3310</provinceCode><unionBankNo>323331000001</unionBankNo><sonName>浙江网商银行股份有限公司</sonName><bankId>323</bankId><settleFlag>0</settleFlag></bankAcc></bankList></B2CRes>";
        Map<String, Object> list = YoYiPayUtil.xmlParseMap(xml);
        System.out.println(list);
    }
}
