package com.yoyi.pay.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/***
 *
 *
 * 描    述：默认访问/WEB-INF/index.jsp
 *
 * 创 建 者：@author wl
 * 创建时间：2019/6/114:13
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
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:/WEB-INF/index.jsp");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }
}
