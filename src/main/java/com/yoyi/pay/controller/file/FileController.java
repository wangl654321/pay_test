package com.yoyi.pay.controller.file;

import com.yoyi.pay.service.YyPayService;
import com.yoyi.pay.utils.Ftp;
import com.yoyi.pay.utils.FtpUtil;
import com.yoyi.pay.utils.SftpUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 *
 *
 * 描    述：对账文件下载
 *
 * 创 建 者： @author wl
 * 创建时间： 2019/3/28 11:30
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
@RequestMapping("/file")
public class FileController {

    private static Logger logger = LogManager.getLogger();

    @Autowired
    private YyPayService yyPayService;

    /**
     * 对账文件下载跳转
     *
     * @return
     */
    @RequestMapping(value = "/download")
    public String toJsp() {
        return "file/file";
    }

    /**
     * 对账文件下载
     *
     * @param ftp
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/toDownload")
    public String registered(HttpServletRequest request, Ftp ftp) {

        String type = request.getParameter("type");
        logger.info("对账文件下载方式,{}", type);
        String sftp = "sftp";
        try {
            logger.info("对账文件下载===>开始");
            if (!sftp.equals(type)) {
                FtpUtil.downloadFtpFile(ftp, ftp.getPath(), ftp.getLocalPath(), ftp.getFileName());
            } else {
                SftpUtil sftpUtil = new SftpUtil();
                sftpUtil.download(ftp, ftp.getPath(), ftp.getLocalPath(), ftp.getFileName());
            }
            return "0";
        } catch (Exception e) {
            logger.error("对账文件下载,异常,{}", e);
            return "1";
        }
    }

    /**
     * 对账文件下载
     *
     * @param ftp
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/save")
    public String registered(Ftp ftp) {

        ftp.setIpAddr("60.12.221.84");
        ftp.setPort(21126);
        ftp.setPath("/duizhangwenjian");
        ftp.setUserName("M100002677");
        ftp.setPwd("H04#247F");
        ftp.setFileName("M100002677_20190515.txt");
        try {
            logger.info("对账文件下载===>开始");
            SftpUtil sftpUtil = new SftpUtil();
            //文件对应表字段
            String[] attribute = {"merchantId", "orderNo", "flowNo", "orderAmt", "curType", "tradeTime", "status", "type", "bankId", "bankName", "customerName", "customerNo", "fee", "refundNo"};

            List<String> list = sftpUtil.read(ftp, ftp.getPath(), ftp.getFileName());
            List<Map<String, String>> listMap = new ArrayList<>();
            list.forEach(key -> {
                //分割
                String[] split = key.split(",");
                Map<String, String> map = new HashMap<>(16);
                for (int i = 0; i < split.length; i++) {
                    String value = split[i];
                    if (!StringUtils.isEmpty(value)) {
                        map.put(attribute[i], value);
                    }
                }
                listMap.add(map);
            });

            int countNum = 1000;
            //计算总共有多少条数据
            int size = list.size();
            //判断需要循环多少次
            int count = size / countNum;
            List<Map<String, String>> subList = null;
            for (int i = 0; i <= count; i++) {
                //每次初始化subList
                if (i != count) {
                    subList = listMap.subList(i * countNum, (i + 1) * countNum);
                } else {
                    subList = listMap.subList(i * countNum, size);
                }
                if (!subList.isEmpty()) {
                    //yyPayService.batchInsert(subList);
                }
            }
            return  String.valueOf(size);
        } catch (Exception e) {
            logger.error("对账文件下载,异常,{}", e);
            return "1";
        }
    }
}
