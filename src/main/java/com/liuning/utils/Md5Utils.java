package com.liuning.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.context.support.StaticApplicationContext;

/**
 * 
 * @author liuning
 *
 */
public class Md5Utils {
	
	/**
	 * 加盐
	 */
	private static String  saltString="asdgsfdfdfsdfgssggfddgfdfg";
	
	/**
	 * 对密码进行加密
	 * @param pwd
	 * @return
	 */
	public static String md5(String pwd) {
		//对密码进行加密
		return DigestUtils.md5Hex(pwd + saltString);
	}
}
