package com.yoyi.pay.config;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/***
 *
 *
 * 描    述：
 *
 * 创 建 者：@author wl
 * 创建时间：2019/4/2814:41
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
@Controller
public class IndexController {

    /**
     * 协议支付,页面跳转
     *
     * @return
     */
    @RequestMapping(value = "/settle")
    public String toJsp() {
        return "index/settle";
    }

    /**
     * 协议支付,页面跳转
     *
     * @return
     */
    @RequestMapping(value = "/protocol")
    public String protocol() {
        return "index/protocol";
    }
}
