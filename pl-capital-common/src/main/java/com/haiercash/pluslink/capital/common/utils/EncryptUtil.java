package com.haiercash.pluslink.capital.common.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public final class EncryptUtil {
    protected static final Log logger = LogFactory.getLog(EncryptUtil.class);
    private static final String DES = "DES";

    public EncryptUtil() {
    }

    public static String MD5Encode(String sourceString) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        return byte2hex(md.digest(sourceString.getBytes()));
    }

    public static byte[] MD5Encode(byte[] source) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        return md.digest(source);
    }

    public static String byte2hex(byte[] bytes) {
        StringBuffer buf = new StringBuffer(bytes.length * 2);

        for (int i = 0; i < bytes.length; ++i) {
            if ((bytes[i] & 255) < 16) {
                buf.append("0");
            }

            buf.append(Long.toString((long) (bytes[i] & 255), 16));
        }

        return buf.toString();
    }

    public static byte[] hex2byte(String str) {
        if (str == null) {
            return null;
        } else {
            str = str.trim();
            int len = str.length();
            if (len != 0 && len % 2 != 1) {
                byte[] b = new byte[len / 2];

                try {
                    for (int e = 0; e < str.length(); e += 2) {
                        b[e / 2] = (byte) Integer.decode("0x" + str.substring(e, e + 2)).intValue();
                    }

                    return b;
                } catch (Exception var4) {
                    return null;
                }
            } else {
                return null;
            }
        }
    }

    public static byte[] desEncrypt(byte[] src, byte[] key) throws Exception {
        SecureRandom sr = new SecureRandom();
        DESKeySpec dks = new DESKeySpec(key);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey securekey = keyFactory.generateSecret(dks);
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(1, securekey, sr);
        return cipher.doFinal(src);
    }

    public static byte[] desDecrypt(byte[] src, byte[] key) throws Exception {
        SecureRandom sr = new SecureRandom();
        DESKeySpec dks = new DESKeySpec(key);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey securekey = keyFactory.generateSecret(dks);
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(2, securekey, sr);
        return cipher.doFinal(src);
    }

    public static byte[] MD5HMAC(byte[] key, byte[] data) throws NoSuchAlgorithmException {
        byte length = 64;
        byte[] ipad = new byte[length];
        byte[] opad = new byte[length];

        for (int actualKey = 0; actualKey < 64; ++actualKey) {
            ipad[actualKey] = 54;
            opad[actualKey] = 92;
        }

        byte[] var13 = key;
        byte[] keyArr = new byte[length];
        if (key.length > length) {
            var13 = MD5Encode(key);
        }

        int kIpadXorResult;
        for (kIpadXorResult = 0; kIpadXorResult < var13.length; ++kIpadXorResult) {
            keyArr[kIpadXorResult] = var13[kIpadXorResult];
        }

        if (var13.length < length) {
            for (kIpadXorResult = var13.length; kIpadXorResult < keyArr.length; ++kIpadXorResult) {
                keyArr[kIpadXorResult] = 0;
            }
        }

        byte[] var14 = new byte[length];

        for (int firstAppendResult = 0; firstAppendResult < length; ++firstAppendResult) {
            var14[firstAppendResult] = (byte) (keyArr[firstAppendResult] ^ ipad[firstAppendResult]);
        }

        byte[] var15 = new byte[var14.length + data.length];

        int firstHashResult;
        for (firstHashResult = 0; firstHashResult < var14.length; ++firstHashResult) {
            var15[firstHashResult] = var14[firstHashResult];
        }

        for (firstHashResult = 0; firstHashResult < data.length; ++firstHashResult) {
            var15[firstHashResult + keyArr.length] = data[firstHashResult];
        }

        byte[] var16 = MD5Encode(var15);
        byte[] kOpadXorResult = new byte[length];

        for (int secondAppendResult = 0; secondAppendResult < length; ++secondAppendResult) {
            kOpadXorResult[secondAppendResult] = (byte) (keyArr[secondAppendResult] ^ opad[secondAppendResult]);
        }

        byte[] var17 = new byte[kOpadXorResult.length + var16.length];

        int hmacMd5Bytes;
        for (hmacMd5Bytes = 0; hmacMd5Bytes < kOpadXorResult.length; ++hmacMd5Bytes) {
            var17[hmacMd5Bytes] = kOpadXorResult[hmacMd5Bytes];
        }

        for (hmacMd5Bytes = 0; hmacMd5Bytes < var16.length; ++hmacMd5Bytes) {
            var17[hmacMd5Bytes + keyArr.length] = var16[hmacMd5Bytes];
        }

        byte[] var18 = MD5Encode(var17);
        return var18;
    }

    public static String desDecryptString(String originalString) {
        StringBuilder newString = new StringBuilder();

        try {
            String e = new String(desDecrypt(hex2byte(originalString), "bv2013fr".getBytes()));
            newString.append(e);
        } catch (Exception var3) {
            newString.append(originalString);
        }

        return newString.toString();
    }

    public static String AesEncrypt(String sSrc, String sKey) throws Exception {
        if (sKey == null) {
            System.out.print("Key为空null");
            return null;
        } else if (sKey.length() != 16) {
            System.out.print("Key长度不是16位");
            return null;
        } else {
            byte[] raw = sKey.getBytes("utf-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(1, skeySpec);
            byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));
            return (new Base64()).encodeToString(encrypted);
        }
    }

    public static String AesDecrypt(String sSrc, String sKey) throws Exception {
        try {
            if (sKey == null) {
                System.out.print("Key为空null");
                return null;
            } else if (sKey.length() != 16) {
                System.out.print("Key长度不是16位");
                return null;
            } else {
                byte[] ex = sKey.getBytes("utf-8");
                SecretKeySpec skeySpec = new SecretKeySpec(ex, "AES");
                Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
                cipher.init(2, skeySpec);
                byte[] encrypted1 = (new Base64()).decode(sSrc);

                try {
                    byte[] e = cipher.doFinal(encrypted1);
                    String originalString = new String(e, "utf-8");
                    return originalString;
                } catch (Exception var8) {
                    System.out.println(var8.toString());
                    return null;
                }
            }
        } catch (Exception var9) {
            System.out.println(var9.toString());
            return null;
        }
    }

    public static String simpleEncrypt(String sSrc) {
        byte[] bas = sSrc.getBytes();

        int half;
        for (half = 0; half < bas.length; ++half) {
            bas[half] = (byte) (~bas[half]);
        }

        half = bas.length / 2;

        for (int i = 0; i < half; ++i) {
            if (i % 2 == 1) {
                byte b = bas[i];
                bas[i] = bas[i + half];
                bas[i + half] = b;
            }
        }

        return (new Base64()).encodeToString(bas);
    }

    public static String simpleDecrypt(String sSrc) {
        byte[] bas = (new Base64()).decode(sSrc);
        int half = bas.length / 2;

        int i;
        for (i = 0; i < half; ++i) {
            if (i % 2 == 1) {
                byte b = bas[i];
                bas[i] = bas[i + half];
                bas[i + half] = b;
            }
        }

        for (i = 0; i < bas.length; ++i) {
            bas[i] = (byte) (~bas[i]);
        }

        return new String(bas);
    }
}
