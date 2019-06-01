package com.yoyi.pay.controller.code;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/***
 *
 *
 * 描    述：微信支付宝二维码生成工具
 *
 * 创 建 者：@author wl
 * 创建时间：2019/4/315:02
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
public class AliPayAndWeChatCode {

    private static Logger logger = LogManager.getLogger();

    /**
     * 微信支付宝二维码生成
     *
     * @param code
     * @param response
     * @throws IOException
     */
    @RequestMapping("/aliPayAndWeChatCode")
    public void AliPayAndWeChatCode(String code, HttpServletResponse response) throws IOException {

        logger.info("微信支付宝二维码生成,code={}", code);
        ServletOutputStream stream = null;
        try {
            //图片大小
            int size = 280;
            stream = response.getOutputStream();
            QRCodeWriter writer = new QRCodeWriter();
            BitMatrix m = writer.encode(code, BarcodeFormat.QR_CODE, size, size);
            MatrixToImageWriter.writeToStream(m, "JPEG", stream);
        } catch (Exception e) {
            logger.error("微信支付宝二维码,生成失败 error,{}", e);
        } finally {
            if (stream != null) {
                stream.flush();
                stream.close();
            }
        }
    }
}
