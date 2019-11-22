package com.inspur.ihealth.utils.security;

import java.io.UnsupportedEncodingException;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 功能：MD5签名处理核心文件，不需要修改
 * 版本：1.0
 * 修改日期：2014-05-30
 */
public class MD5Util {

	/**
	 * 签名字符串
	 * 
	 * @param text
	 *            需要签名的字符串
	 * @param key
	 *            密钥
	 * @param input_charset
	 *            编码格式
	 * @return 签名结果
	 */
	public static String sign(String text, String key, String input_charset) {
		text = text + key;
		return DigestUtils.md5Hex(getContentBytes(text, input_charset));
	}

	/**
	 * 签名字符串
	 * 
	 * @param text
	 *            需要签名的字符串
	 * @param sign
	 *            签名结果
	 * @param key
	 *            密钥
	 * @param input_charset
	 *            编码格式
	 * @return 签名结果
	 */
	public static boolean verify(String text, String sign, String key,
			String input_charset) {
		text = text + key;
		String mysign = DigestUtils
				.md5Hex(getContentBytes(text, input_charset));
		if (mysign.equals(sign)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @param content
	 * @param charset
	 * @return
	 * @throws SignatureException
	 * @throws UnsupportedEncodingException
	 */
	private static byte[] getContentBytes(String content, String charset) {
		if (StringUtils.isEmpty(charset)) {
			return content.getBytes();
		}
		try {
			return content.getBytes(charset);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(
					"MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);
		}
	}

	/**
	 * 根据map生成签名字符串
	 * 
	 * @title signFromMap
	 * @param paramMap
	 * @return
	 */
	public static String signFromMap(Map<String, String> paramMap, String key) {
		String sign_type = null;
		if (StringUtils.isBlank(sign_type)) {
			sign_type = "MD5";
		}
		Map<String, String> sPara = paraFilter(paramMap);
		// 生成签名结果
		String mysign = buildRequestMysign(sPara, sign_type, key);
		return mysign;
	}

	protected static String functionNameForGet(String fieldName) {
		char[] array = fieldName.toCharArray();
		if ('a' <= array[0] && array[0] <= 'z') {
			array[0] -= 32;
		}
		return "get" + String.valueOf(array);
	}

	/**
	 * 生成签名结果
	 * 
	 * @param sPara
	 *            要签名的数组
	 * @return 签名结果字符串
	 */
	private static String buildRequestMysign(Map<String, String> sPara,
			String sign_type, String key) {
		String prestr = createLinkString(sPara); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
		String mysign = "";
		if (sign_type.equals("MD5")) {
			mysign = sign(prestr, key, "utf-8");
		}
		return mysign;
	}

	/**
	 * 除去数组中的空值和签名参数
	 * 
	 * @param sArray
	 *            签名参数组
	 * @return 去掉空值与签名参数后的新签名参数组
	 */
	private static Map<String, String> paraFilter(Map<String, String> sArray) {

		Map<String, String> result = new HashMap<String, String>();
		if (sArray == null || sArray.size() <= 0) {
			return result;
		}
		for (String key : sArray.keySet()) {
			String value = sArray.get(key);
			result.put(key, value);
		}
		return result;
	}

	/**
	 * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
	 * 
	 * @param params
	 *            需要排序并参与字符拼接的参数组
	 * @return 拼接后字符串
	 */
	public static String createLinkString(Map<String, String> params) {

		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);

		StringBuffer prestr = new StringBuffer();

		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			String value = params.get(key);
			if (StringUtils.isBlank(value)) {
				continue;
			}
			if (prestr.length() > 0) {
				prestr.append("&");
			}
			prestr.append(key).append("=").append(value.trim());
		}
		return prestr.toString();
	}

	public static String md5(String s) {
		s = DigestUtils.md5Hex(s);
		return s;
	}

	public static void main(String[] args) {
		//获取电子健康卡Id
		Map<String, String> paramMap = new HashMap<>(32);
		paramMap.put("cardType", "01");
		paramMap.put("cardNo","130502198610220314");
		paramMap.put("name", "路振国");
		paramMap.put("phone","19904774066");
		paramMap.put("sex", "1");
		paramMap.put("appMode", "1");
		paramMap.put("nation", "01");
		paramMap.put("regChannel", "11");

		String appSecret = "8E627BDA54114732A202CD5DCA693E70";

		//签名：
		String rs = MD5Util.signFromMap(paramMap,appSecret);

		System.out.println(rs);
	}

}
