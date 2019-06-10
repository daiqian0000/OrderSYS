package com.asion.common.encrypt.charc;

import java.util.HashMap;
import java.util.Map;

/**
 * 字符哈希解密接口实现类
 * 
 * @author chenboyang
 *
 */
public class SimpleCharDecoder extends CharCoder implements CharDecoder {

	/**
	 * 解密哈希容器
	 */
	private final Map<Character, Character> decodeMap = new HashMap<Character, Character>();

	/**
	 * 构造字符解密器
	 */
	public SimpleCharDecoder() {
		init();
	}

	/**
	 * 初始化解密器
	 */
	private void init() {
		for (int i = 0; i < CHAR_CODES.length; i++) {
			decodeMap.put(CHAR_CODES[i], CHAR_CODES[decodePosition(i)]);
		}
	}

	/**
	 * 根据加密位获取对应的解密位
	 * 
	 * @param index
	 *            加密位
	 * @return 解密位
	 */
	private int decodePosition(int index) {
		int length = CHAR_CODES.length;
		if (length > index) {
			int center = length / 2;
			if (index == center) {
				index = length - 1;
			} else if (index < center) {
				index = length - ((index + 1) * 2);
			} else if (index > center) {
				index = ((index - center) - 1) * 2;
			}
		}
		return index;
	}

	public String decode(String str) {
		if (str != null && !str.isEmpty()) {
			char[] chars = str.toCharArray();
			char[] newChars = new char[chars.length];
			for (int i = 0; i < chars.length; i++) {
				if (decodeMap.containsKey(chars[i])) {
					newChars[i] = decodeMap.get(chars[i]);
				} else {
					newChars[i] = chars[i];
				}
			}
			str = String.valueOf(newChars);
		}
		return str;
	}

	public String decode(String str, int num) {
		if (num >= ENCODE_LIMIT[0]) {
			if (num > ENCODE_LIMIT[1]) {
				num = ENCODE_LIMIT[1];
			}
			for (int i = 0; i < num; i++) {
				str = decode(str);
			}
		}
		return str;
	}

}
