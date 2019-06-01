package com.yoyi.pay.utils;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.*;
import java.util.Date;
import java.util.Enumeration;

/***
 *
*
* 描    述：证书签名工具类
*
* 创 建 者： @author 刘迪
* 创建时间： 2015-05-04 11:35
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
public class CertSignUtil {

    public static final String RSA_SHA1_SIGN = "SHA1withRSA";

    public static final String CERT_TYPE_JKS = "JKS";

    public static final String CERT_TYPE_PKCS12 = "PKCS12";

    /**
     * 数字签名函数入口
     *
     * @param plainBytes    待签名明文字节数组
     * @param privateKey    签名使用私钥
     * @param signAlgorithm 签名算法
     * @return 签名后的字节数组
     * @throws Exception
     */
    public static byte[] digitalSign(byte[] plainBytes, PrivateKey privateKey, String signAlgorithm) throws Exception {
        try {
            Signature signature = Signature.getInstance(signAlgorithm);
            signature.initSign(privateKey);
            signature.update(plainBytes);
            byte[] signBytes = signature.sign();
            return signBytes;
        } catch (NoSuchAlgorithmException e) {
            throw new Exception(e);
        } catch (InvalidKeyException e) {
            throw new Exception(e);
        } catch (SignatureException e) {
            throw new Exception(e);
        }
    }

    /**
     * 验证数字签名函数入口
     *
     * @param plainBytes    待验签明文字节数组
     * @param signBytes     待验签签名后字节数组
     * @param publicKey     验签使用公钥
     * @param signAlgorithm 签名算法
     * @return 验签是否通过
     * @throws Exception
     */
    public static boolean verifyDigitalSign(byte[] plainBytes, byte[] signBytes, PublicKey publicKey, String signAlgorithm) throws Exception {
        boolean isValid = false;
        try {
            Signature signature = Signature.getInstance(signAlgorithm);
            signature.initVerify(publicKey);
            signature.update(plainBytes);
            isValid = signature.verify(signBytes);
            return isValid;
        } catch (NoSuchAlgorithmException e) {
            throw new Exception(e);
        } catch (InvalidKeyException e) {
            throw new Exception(e);
        } catch (SignatureException e) {
            throw new Exception(e);
        }
    }

    /**
     * 获取RSA公钥对象
     *
     * @param certStr cer文件内容
     */
    public static PublicKey getRSAPublicKeyByString(String certStr) throws CertificateExpiredException, CertificateNotYetValidException, Exception {
        PublicKey pubKey = null;
        CertificateFactory certificatefactory = CertificateFactory.getInstance("X.509");
        X509Certificate cert = (X509Certificate) certificatefactory.generateCertificate(new ByteArrayInputStream(certStr.getBytes()));
        cert.checkValidity(new Date());
        pubKey = cert.getPublicKey();
        return pubKey;
    }

    /**
     * 获取RSA公钥对象(base64格式的cer证书)
     *
     * @param filePath cer文件位置
     */
    public static PublicKey getRSAPublicKeyByFile(String filePath) throws CertificateExpiredException, CertificateNotYetValidException, Exception {
        InputStream in = null;

        try {
            in = new FileInputStream(filePath);
            PublicKey pubKey = null;
            CertificateFactory certificatefactory = CertificateFactory.getInstance("X.509");
            X509Certificate cert = (X509Certificate) certificatefactory.generateCertificate(in);
            cert.checkValidity(new Date());
            pubKey = cert.getPublicKey();
            return pubKey;
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
            }
        }
    }

    /**
     * RSA加密
     *
     * @param plainBytes      明文字节数组
     * @param publicKey       公钥
     * @param keyLength       密钥bit长度
     * @param reserveSize     padding填充字节数，预留11字节
     * @param cipherAlgorithm 加解密算法，一般为RSA/ECB/PKCS1Padding
     * @return 加密后字节数组，不经base64编码
     * @throws Exception
     */
    public static byte[] RSAEncrypt(byte[] plainBytes, PublicKey publicKey, int keyLength, int reserveSize, String cipherAlgorithm) throws Exception {
        int keyByteSize = keyLength / 8; // 密钥字节数
        int encryptBlockSize = keyByteSize - reserveSize; // 加密块大小=密钥字节数-padding填充字节数
        int nBlock = plainBytes.length / encryptBlockSize;// 计算分段加密的block数，向上取整
        if ((plainBytes.length % encryptBlockSize) != 0) { // 余数非0，block数再加1
            nBlock += 1;
        }

        try {
            Cipher cipher = Cipher.getInstance(cipherAlgorithm);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);

            // 输出buffer，大小为nBlock个keyByteSize
            ByteArrayOutputStream outbuf = new ByteArrayOutputStream(nBlock * keyByteSize);
            // 分段加密
            for (int offset = 0; offset < plainBytes.length; offset += encryptBlockSize) {
                int inputLen = plainBytes.length - offset;
                if (inputLen > encryptBlockSize) {
                    inputLen = encryptBlockSize;
                }

                // 得到分段加密结果
                byte[] encryptedBlock = cipher.doFinal(plainBytes, offset, inputLen);
                // 追加结果到输出buffer中
                outbuf.write(encryptedBlock);
            }

            outbuf.flush();
            outbuf.close();
            return outbuf.toByteArray();
        } catch (NoSuchAlgorithmException e) {
            throw new Exception(e);
        } catch (NoSuchPaddingException e) {
            throw new Exception(e);
        } catch (InvalidKeyException e) {
            throw new Exception(e);
        } catch (IllegalBlockSizeException e) {
            throw new Exception(e);
        } catch (BadPaddingException e) {
            throw new Exception(e);
        } catch (IOException e) {
            throw new Exception(e);
        }
    }

    /**
     * RSA解密
     *
     * @param encryptedBytes  加密后字节数组
     * @param privateKey      私钥
     * @param keyLength       密钥bit长度
     * @param reserveSize     padding填充字节数，预留11字节
     * @param cipherAlgorithm 加解密算法，一般为RSA/ECB/PKCS1Padding
     * @return 解密后字节数组，不经base64编码
     * @throws Exception
     */
    public static byte[] RSADecrypt(byte[] encryptedBytes, PrivateKey privateKey, int keyLength, int reserveSize, String cipherAlgorithm)
            throws Exception {
        int keyByteSize = keyLength / 8; // 密钥字节数
        int decryptBlockSize = keyByteSize - reserveSize; // 解密块大小=密钥字节数-padding填充字节数
        int nBlock = encryptedBytes.length / keyByteSize;// 计算分段解密的block数，理论上能整除

        try {
            Cipher cipher = Cipher.getInstance(cipherAlgorithm);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);

            // 输出buffer，大小为nBlock个decryptBlockSize
            ByteArrayOutputStream outbuf = new ByteArrayOutputStream(nBlock * decryptBlockSize);
            // 分段解密
            for (int offset = 0; offset < encryptedBytes.length; offset += keyByteSize) {
                // block大小: decryptBlock 或 剩余字节数
                int inputLen = encryptedBytes.length - offset;
                if (inputLen > keyByteSize) {
                    inputLen = keyByteSize;
                }

                // 得到分段解密结果
                byte[] decryptedBlock = cipher.doFinal(encryptedBytes, offset, inputLen);
                // 追加结果到输出buffer中
                outbuf.write(decryptedBlock);
            }

            outbuf.flush();
            outbuf.close();
            return outbuf.toByteArray();
        } catch (NoSuchAlgorithmException e) {
            throw new Exception(e);
        } catch (NoSuchPaddingException e) {
            throw new Exception(e);
        } catch (InvalidKeyException e) {
            throw new Exception(e);
        } catch (IllegalBlockSizeException e) {
            throw new Exception(e);
        } catch (BadPaddingException e) {
            throw new Exception(e);
        } catch (IOException e) {
            throw new Exception(e);
        }
    }

    /**
     * AES加密
     *
     * @param plainBytes      明文字节数组
     * @param keyBytes        密钥字节数组
     * @param keyAlgorithm    密钥算法
     * @param cipherAlgorithm 加解密算法
     * @param IV              随机向量
     * @return 加密后字节数组，不经base64编码
     * @throws Exception
     */
    public static byte[] AESEncrypt(byte[] plainBytes, byte[] keyBytes, String keyAlgorithm, String cipherAlgorithm, String IV) throws Exception {
        try {
            // AES密钥长度为128bit、192bit、256bit，默认为128bit
            if (keyBytes.length % 8 != 0 || keyBytes.length < 16 || keyBytes.length > 32) {
                throw new Exception("AES密钥长度不合法");
            }

            Cipher cipher = Cipher.getInstance(cipherAlgorithm);
            SecretKey secretKey = new SecretKeySpec(keyBytes, keyAlgorithm);
            if (IV != null && !"".equals(IV.trim())) {
                IvParameterSpec ivspec = new IvParameterSpec(IV.getBytes());
                cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivspec);
            } else {
                cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            }

            byte[] encryptedBytes = cipher.doFinal(plainBytes);

            return encryptedBytes;
        } catch (NoSuchAlgorithmException e) {
            throw new Exception(e);
        } catch (NoSuchPaddingException e) {
            throw new Exception(e);
        } catch (InvalidKeyException e) {
            throw new Exception(e);
        } catch (InvalidAlgorithmParameterException e) {
            throw new Exception(e);
        } catch (BadPaddingException e) {
            throw new Exception(e);
        } catch (IllegalBlockSizeException e) {
            throw new Exception(e);
        }
    }

    /**
     * AES解密
     *
     * @param encryptedBytes  密文字节数组，不经base64编码
     * @param keyBytes        密钥字节数组
     * @param keyAlgorithm    密钥算法
     * @param cipherAlgorithm 加解密算法
     * @param IV              随机向量
     * @return 解密后字节数组
     * @throws Exception
     */
    public static byte[] AESDecrypt(byte[] encryptedBytes, byte[] keyBytes, String keyAlgorithm, String cipherAlgorithm, String IV) throws Exception {
        try {
            // AES密钥长度为128bit、192bit、256bit，默认为128bit
            if (keyBytes.length % 8 != 0 || keyBytes.length < 16 || keyBytes.length > 32) {
                throw new Exception("AES密钥长度不合法");
            }

            Cipher cipher = Cipher.getInstance(cipherAlgorithm);
            SecretKey secretKey = new SecretKeySpec(keyBytes, keyAlgorithm);
            if (IV != null && !"".equals(IV.trim())) {
                IvParameterSpec ivspec = new IvParameterSpec(IV.getBytes());
                cipher.init(Cipher.DECRYPT_MODE, secretKey, ivspec);
            } else {
                cipher.init(Cipher.DECRYPT_MODE, secretKey);
            }

            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

            return decryptedBytes;
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("NoSuchAlgorithmException");
        } catch (NoSuchPaddingException e) {
            throw new Exception("NoSuchPaddingException");
        } catch (InvalidKeyException e) {
            throw new Exception("InvalidKeyException");
        } catch (InvalidAlgorithmParameterException e) {
            throw new Exception("InvalidAlgorithmParameterException");
        } catch (BadPaddingException e) {
            throw new Exception("BadPaddingException");
        } catch (IllegalBlockSizeException e) {
            throw new Exception("IllegalBlockSizeException");
        }
    }

    /**
     * 获取keystore
     *
     * @param pfxcertfile 证书文件
     * @param keypwd      证书密码
     * @param type        证书类型
     * @return
     */
    public static KeyStore getKeyInfo(String pfxcertfile, String keypwd, String type) throws Exception {
        try {
            KeyStore ks = null;
            if (CERT_TYPE_JKS.equals(type)) {
                ks = KeyStore.getInstance(type);
            } else if (CERT_TYPE_PKCS12.equals(type)) {
                int res = Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
                if (res == -1) {
                    System.out.println("已存在BCprovider");
                }
                ks = KeyStore.getInstance(type, "BC");
            } else {
                throw new Exception("证书格式只支持JKS和PKC12");
            }
            FileInputStream fis = new FileInputStream(pfxcertfile);
            char[] nPassword = null;
            nPassword = null == keypwd || "".equals(keypwd.trim()) ? null : keypwd.toCharArray();
            ks.load(fis, nPassword);
            fis.close();
            return ks;
        } catch (Exception e) {
            if ((e instanceof KeyStoreException) && CERT_TYPE_PKCS12.equals(type)) {
                Security.removeProvider("BC");
            }
            e.printStackTrace();
            throw new Exception("获取证书keystore失败");
        }
    }

    /**
     * 获取证书私钥
     *
     * @param pfxcertfile 证书文件
     * @param keypwd      证书密码
     * @param type        证书类型
     * @return
     */
    public static PrivateKey getRSACertPrivateKey(String pfxcertfile, String keypwd, String type) throws CertificateExpiredException, CertificateNotYetValidException, Exception {
        KeyStore ks = getKeyInfo(pfxcertfile, keypwd, type);
        Enumeration<String> aliasenum = ks.aliases();
        String keyAlias = null;
        if (aliasenum.hasMoreElements()) {
            keyAlias = aliasenum.nextElement();
        }
        PrivateKey privateKey = null;
        X509Certificate cert = (X509Certificate) ks.getCertificate(keyAlias);
        cert.checkValidity(new Date());
        privateKey = (PrivateKey) ks.getKey(keyAlias, keypwd.toCharArray());
        return privateKey;
    }

    /**
     * 获取证书公钥
     *
     * @param pfxcertfile 证书文件
     * @param keypwd      证书密码
     * @param type        证书类型
     * @return
     */
    public static PublicKey getRSACertPublicKey(String pfxcertfile, String keypwd, String type) throws Exception {
        try {
            KeyStore ks = getKeyInfo(pfxcertfile, keypwd, type);
            Enumeration<String> aliasenum = ks.aliases();
            String keyAlias = null;
            if (aliasenum.hasMoreElements()) {
                keyAlias = aliasenum.nextElement();
            }
            Certificate cert = ks.getCertificate(keyAlias);
            PublicKey publicKey = null;
            publicKey = (PublicKey) cert.getPublicKey();
            return publicKey;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("获取证书公钥失败");
        }
    }

    /**
     * 获取证书实体
     *
     * @param pfxcertfile 证书文件
     * @param keypwd      证书密码
     * @param type        证书类型
     * @return
     */
    public static X509Certificate getRSACert(String pfxcertfile, String keypwd, String type) throws Exception {
        try {
            KeyStore ks = getKeyInfo(pfxcertfile, keypwd, type);
            Enumeration<String> aliasenum = ks.aliases();
            String keyAlias = null;
            if (aliasenum.hasMoreElements()) {
                keyAlias = aliasenum.nextElement();
            }
            X509Certificate cert = (X509Certificate) ks.getCertificate(keyAlias);
            cert.checkValidity(new Date());
            return cert;
        } catch (CertificateExpiredException e) {   // 过期
            e.printStackTrace();
            throw new Exception("证书已过期");
        } catch (CertificateNotYetValidException e) {   // 过期
            e.printStackTrace();
            throw new Exception("证书未生效");
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("获取证书失败");
        }
    }

}
