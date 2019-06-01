package com.yoyi.pay.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.util.StringUtils;

import java.io.*;
import java.net.SocketException;


/***
 *
 *
 * 描    述：
 *
 * 创 建 者： @author wl
 * 创建时间： 2019/3/29 9:46
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
public class FtpUtil {

    private final static Log logger = LogFactory.getLog(FtpUtil.class);

    /**
     * 获取FTPClient对象
     *
     * @param ftp
     * @return
     */
    public static FTPClient getFTPClient(Ftp ftp) {
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient = new FTPClient();
            // 连接FTP服务器
            ftpClient.connect(ftp.getIpAddr(), ftp.getPort());
            // 登陆FTP服务器
            ftpClient.login(ftp.getUserName(), ftp.getPwd());
            // 中文支持
            ftpClient.setControlEncoding("UTF-8");
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();
            if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
                logger.info("未连接到FTP，用户名或密码错误。");
                ftpClient.disconnect();
            } else {
                logger.info("FTP连接成功。");
            }
        } catch (SocketException e) {
            e.printStackTrace();
            logger.info("FTP的IP地址可能错误，请正确配置。");
        } catch (IOException e) {
            e.printStackTrace();
            logger.info("FTP的端口错误,请正确配置。");
        }
        return ftpClient;
    }

    /**
     * 从FTP服务器下载文件
     *
     * @param ftpPath        FTP服务器中文件所在路径 格式： /aa
     * @param localPath      下载到本地的位置 格式：H:/download
     * @param fileName       FTP服务器上要下载的文件名称
     */
    public static void downloadFtpFile(Ftp ftp, String ftpPath, String localPath, String fileName) {

        FTPClient ftpClient = null;
        try {
            //连接ftp服务
            ftpClient = getFTPClient(ftp);
            ftpClient.changeWorkingDirectory(ftpPath);
            //远程文件名称不存在,下载远程路径所有文件
            if (StringUtils.isEmpty(fileName)) {
                FTPFile[] ftpFiles = ftpClient.listFiles();
                for (FTPFile ftpFile : ftpFiles) {
                    fileDownload(ftpClient, localPath, ftpFile.getName());
                }
            } else {
                //远程文件名称存在,下载远程路径文件
                fileDownload(ftpClient, localPath, fileName);
            }
            ftpClient.logout();
        } catch (FileNotFoundException e) {
            logger.error("没有找到" + ftpPath + "文件");
        } catch (SocketException e) {
            logger.error("连接FTP失败,", e);
        } catch (IOException e) {
            logger.error("文件读取错误,", e);
        }
    }


    /**
     * 远程下载
     *
     * @param localPath 本地地址
     * @param fileName  远程文件名称
     * @param ftpClient 远程工具
     * @throws IOException
     */
    private static void fileDownload(FTPClient ftpClient, String localPath, String fileName) throws IOException {
        //编码文件格式,解决中文文件名
        String f_ame = new String(fileName.getBytes("GBK"), FTP.DEFAULT_CONTROL_ENCODING);
        File localFile = new File(localPath + File.separatorChar + fileName);
        OutputStream os = new FileOutputStream(localFile);
        ftpClient.retrieveFile(f_ame, os);
        os.close();
    }


    /**
     * 向FTP服务器上传文件
     *
     * @param ftpPath  FTP服务器基础目录
     * @param filename FTP服务器文件存放路径。例如分日期存放：/2015/01/01。文件的路径为basePath+filePath
     * @param input    输入流
     * @return
     */
    public static boolean uploadFile(Ftp ftp, String ftpPath, String filename, InputStream input) {
        boolean result = false;
        FTPClient ftpClient = new FTPClient();
        try {
            int reply;
            ftpClient = getFTPClient(ftp);
            ftpClient.changeWorkingDirectory(ftpPath);

            reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
                return false;
            }
            //编码文件名，支持中文文件名
            filename = new String(filename.getBytes("GBK"), FTP.DEFAULT_CONTROL_ENCODING);
            //上传文件
            if (!ftpClient.storeFile(filename, input)) {
                return false;
            }
            input.close();
            ftpClient.logout();
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {


        Ftp ftp = new Ftp();
        ftp.setIpAddr("60.12.221.84");
        ftp.setPort(2100);
        ftp.setUserName("M100002565");
        ftp.setPwd("Y08#272D");

        String ftpPath = "/duizhangwenjian";
        String fileName = "项目说明.txt";

        try {
            //下载文件夹下的所有文件
            FtpUtil.downloadFtpFile(ftp, ftpPath, "D:/yoyi", "");
            //下载指定的文件
             FtpUtil.downloadFtpFile(ftp, ftpPath, "D:/yoyi", fileName);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }
    }
}
