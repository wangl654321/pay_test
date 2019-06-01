package com.yoyi.pay.utils;

import com.jcraft.jsch.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.StringUtils;

import java.io.*;
import java.util.*;

/***
 *
 *
 * 描    述：类说明 sftp工具类
 *
 * 创 建 者： @author wl
 * 创建时间： 2019/4/2 15:29
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
public class SftpUtil {

    private static Logger logger = LogManager.getLogger();

    private ChannelSftp sftp = null;

    private Session session = null;

    /**
     * 连接sftp服务器
     */
    public ChannelSftp connect(Ftp ftp) {

        try {
            JSch jsch = new JSch();
            if (null != ftp.getPrivateKey()) {
                //设置私钥
                jsch.addIdentity(ftp.getPrivateKey());
            }
            session = jsch.getSession(ftp.getUserName(), ftp.getIpAddr(), ftp.getPort());
            if (null != ftp.getPwd()) {
                session.setPassword(ftp.getPwd());
            }
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect();
            Channel channel = session.openChannel("sftp");
            channel.connect();
            sftp = (ChannelSftp) channel;
        } catch (JSchException e) {
            logger.error("连接sftp服务器,异常,用户或密码错误,{}", e);
        }
        return sftp;
    }

    /**
     * 关闭连接 server
     */
    public void logout() throws Exception {
        if (this.sftp != null) {
            if (this.sftp.isConnected()) {
                this.sftp.disconnect();
                this.sftp = null;
                logger.info("sftp,关闭连接");
            }
        }

        if (this.session != null) {
            if (this.session.isConnected()) {
                this.session.disconnect();
                this.session = null;
                logger.info("session,关闭连接");
            }
        }
    }


    /**
     * 上传文件
     * 将输入流的数据上传到sftp作为文件。文件完整路径=basePath+directory
     *
     * @param basePath     服务器的基础路径
     * @param directory    上传到该目录
     * @param sftpFileName sftp端文件名
     * @param input        输入流
     */
    public void upload(ChannelSftp sftp, String basePath, String directory, String sftpFileName, InputStream input) throws SftpException {
        try {
            sftp.cd(basePath);
            sftp.cd(directory);
        } catch (SftpException e) {
            //目录不存在，则创建文件夹
            String[] dirs = directory.split("/");
            String tempPath = basePath;
            for (String dir : dirs) {
                if (null == dir || "".equals(dir)) {
                    continue;
                }
                tempPath += "/" + dir;
                try {
                    sftp.cd(tempPath);
                } catch (SftpException ex) {
                    sftp.mkdir(tempPath);
                    sftp.cd(tempPath);
                }
            }
        }
        //上传文件
        sftp.put(input, sftpFileName);
    }


    /**
     * 下载文件。
     *
     * @param directory 下载目录
     * @param fileName  下载的文件
     * @param localPath 存在本地的路径
     */
    public void download(Ftp ftp, String directory, String localPath, String fileName) throws Exception {

        connect(ftp);
        try {
            if (directory != null && !"".equals(directory)) {
                sftp.cd(directory);
            }
            if (StringUtils.isEmpty(fileName)) {
                logger.info("sftp,文件批量下载");
                Vector<?> vector = listFiles(directory);
                Iterator it = vector.iterator();
                while (it.hasNext()) {
                    ChannelSftp.LsEntry entry = (ChannelSftp.LsEntry) it.next();
                    SftpATTRS attrs = entry.getAttrs();
                    if (!attrs.isDir()) {
                        String name = entry.getFilename();
                        fileOutputStream(localPath, name);
                    }
                }
            } else {
                logger.info("sftp,单个文件下载");
                fileOutputStream(localPath, fileName);
            }
        } catch (Exception e) {
            throw new Exception();
        } finally {
            logout();
        }
    }


    /**
     * 读取下载文件
     *
     * @param ftp       sftp连接信息
     * @param directory 下载目录
     * @param fileName  下载的文件名称
     * @return
     * @throws Exception
     */
    public List<String> read(Ftp ftp, String directory, String fileName) throws Exception {

        //连接sftp
        connect(ftp);
        //保存读取的参数
        List<String> list = new ArrayList<>();
        InputStream inputStream = null;
        try {
            if (directory != null && !"".equals(directory)) {
                sftp.cd(directory);
            }
            inputStream = sftp.get(fileName);
        }catch (Exception e){
            logger.error("下载的文件不存在");
        }
        try {
            if(null != inputStream){
                //缓冲读
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                //读取每行
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    if (null != line && !"".equals(line)) {
                        String key = line.replaceAll("\\|", ",");
                        list.add(key);
                    }
                }
                //关闭流
                bufferedReader.close();
                inputStream.close();
            }
        } catch (Exception e) {
            logger.error("读取下载文件异常,", e);
        } finally {
            logout();
        }
        return list;
    }


    /**
     * 单个文件下载
     *
     * @param localPath 本地下载位置
     * @param fileName  下载文件名称
     * @throws IOException
     * @throws SftpException
     */
    private void fileOutputStream(String localPath, String fileName) throws Exception {

        OutputStream output = null;
        try {
            File file = new File(localPath + "/" + fileName);
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            output = new FileOutputStream(file);
            sftp.get(fileName, output);
            output.close();
        } catch (Exception e) {
            throw new Exception("单个文件下载异常");
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    throw new Exception("关闭流异常" + e.getMessage());
                }
            }
        }
    }

    /**
     * 下载文件
     *
     * @param directory    下载目录
     * @param downloadFile 下载的文件名
     * @return 字节数组
     */
    public byte[] download(String directory, String downloadFile) throws SftpException, IOException {

        if (directory != null && !"".equals(directory)) {
            sftp.cd(directory);
        }
        InputStream is = sftp.get(downloadFile);
        byte[] fileData = new byte[is.available()];
        return fileData;
    }


    /**
     * 删除文件
     *
     * @param directory      要删除文件所在目录
     * @param deleteFileName 要删除的文件
     */
    public void delete(String directory, String deleteFileName) throws SftpException {
        sftp.cd(directory);
        sftp.rm(deleteFileName);
    }


    /**
     * 列出目录下的文件
     *
     * @param directory 要列出的目录
     */
    public Vector<?> listFiles(String directory) throws SftpException {
        Vector ls = sftp.ls(directory);
        return ls;
    }
}