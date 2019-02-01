package com.hkkj.oa.common.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.hkkj.oa.controller.admin.UserController;
import com.mysql.jdbc.log.LogUtils;

public class AESCipher {
	private static final Logger log = LogManager.getLogger(AESCipher.class);
	private static final String IV_STRING = "A-16-Byte-String";
	private static final String charset = "UTF-8";
	private static final String defaultKey = "hkoa2018hkoa2018";

	/**
	 * 加密
	 */
	public static String aesEncryptString(String content) {
		return aesEncryptString(content, defaultKey);
	}

	/**
	 * 加密
	 */
	public static String aesEncryptString(String content, String key) {
		try {
			byte[] contentBytes = content.getBytes(charset);
			byte[] keyBytes = key.getBytes(charset);
			byte[] encryptedBytes = aesEncryptBytes(contentBytes, keyBytes);
			return Base64.encodeBase64String(encryptedBytes);
		} catch (Exception e) {
			log.error(AESCipher.class, e);
		}
		return null;
	}

	/**
	 * 解密
	 */
	public static String aesDecryptString(String content) {
		return aesDecryptString(content, defaultKey);
	}

	/**
	 * 解密
	 */
	public static String aesDecryptString(String content, String key) {
		try {
			byte[] encryptedBytes = Base64.decodeBase64(content);
			byte[] keyBytes = key.getBytes(charset);
			byte[] decryptedBytes = aesDecryptBytes(encryptedBytes, keyBytes);
			return new String(decryptedBytes, charset);
		} catch (Exception e) {
			log.error(AESCipher.class, e);
		}
		return null;
	}

	public static byte[] aesEncryptBytes(byte[] contentBytes, byte[] keyBytes) {
		try {
			return cipherOperation(contentBytes, keyBytes, Cipher.ENCRYPT_MODE);
		} catch (Exception e) {
			log.error(AESCipher.class, e);
		}
		return null;
	}

	public static byte[] aesDecryptBytes(byte[] contentBytes, byte[] keyBytes) {
		try {
			return cipherOperation(contentBytes, keyBytes, Cipher.DECRYPT_MODE);
		} catch (Exception e) {
			log.error(AESCipher.class, e);
		}
		return null;
	}

	private static byte[] cipherOperation(byte[] contentBytes, byte[] keyBytes, int mode) {
		try {
			SecretKeySpec secretKey = new SecretKeySpec(keyBytes, "AES");
			byte[] initParam = IV_STRING.getBytes(charset);
			IvParameterSpec ivParameterSpec = new IvParameterSpec(initParam);

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(mode, secretKey, ivParameterSpec);

			return cipher.doFinal(contentBytes);
		} catch (Exception e) {
			log.error(AESCipher.class, e);
		}
		return null;
	}

	/**
	 * 测试
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		String key = "hkzb2017hkzb2017";
		String string = AESCipher.aesEncryptString("123456", key);
		System.out.println(string);
		System.out.println(AESCipher.aesDecryptString(string, key));
		System.out.println("admin:"+AESCipher.aesEncryptString("admin"));
		System.out.println("1000:"+AESCipher.aesEncryptString("1000"));
		System.out.println("1001:"+AESCipher.aesEncryptString("1001"));
		System.out.println("1002:"+AESCipher.aesEncryptString("1002"));
		System.out.println("1003:"+AESCipher.aesEncryptString("1003"));
	}

}
