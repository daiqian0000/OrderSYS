package com.asion.common.encrypt.bytec;

import java.io.UnsupportedEncodingException;

/**
 * 字节解密接口实现类
 * 
 * @author chenboyang
 *
 */
public class SimpleByteDecoder extends ByteCoder implements ByteDecoder {

	/**
	 * 根据加密字节获取对应的解密字节
	 * 
	 * @param b
	 *            加密字节
	 * @return 解密字节
	 */
	private byte decodeByte(byte b) {
		if (b < 0) {
			b = (byte) (MIN_VALUE + (Math.abs(b) * 2) - 1);
		} else {
			b = (byte) (MIN_VALUE + (b * 2));
		}
		return b;
	}

	public byte decode(byte b, int num) {
		if (num >= ENCODE_LIMIT[0]) {
			if (num > ENCODE_LIMIT[1]) {
				num = ENCODE_LIMIT[1];
			}
			for (int i = 0; i < num; i++) {
				b = decodeByte(b);
			}
		}
		return b;
	}

	public byte decode(byte b) {
		return decode(b, DEFAULT_ENCRYPT_NUM);
	}

	public byte[] decode(byte[] bytes, int num) {
		if (bytes != null && bytes.length > 0) {
			byte[] newBytes = new byte[bytes.length];
			for (int i = 0; i < bytes.length; i++) {
				newBytes[i] = decode(bytes[i], num);
			}
			bytes = newBytes;
		}
		return bytes;
	}

	public byte[] decode(byte[] bytes) {
		return decode(bytes, DEFAULT_ENCRYPT_NUM);
	}

	public String decode(String str, int num) {
		if (str != null && !str.isEmpty() && num > 0) {
			try {
				str = new String(decode(str.getBytes(CHARSET), num));
			} catch (UnsupportedEncodingException e) {
				logger.error(e.getMessage(), e);
			}
		}
		return str;
	}

	public String decode(String str) {
		return decode(str, DEFAULT_ENCRYPT_NUM);
	}

}
