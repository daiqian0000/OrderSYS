package com.asion.common.encrypt.bytec;

import java.io.UnsupportedEncodingException;

/**
 * 字节加密接口实现类
 * 
 * @author chenboyang
 *
 */
public class SimpleByteEncoder extends ByteCoder implements ByteEncoder {

	/**
	 * 根据字节获取对应的加密字节
	 * 
	 * @param b
	 *            字节
	 * @return 加字节
	 */
	private byte encodeByte(byte b) {
		int center = EXTENT / 2;
		if (b % 2 == 0) {
			b = (byte) ((center + b) / 2);
		} else {
			if (b > 0) {
				b = (byte) ((-(b + 1) + MIN_VALUE) / 2);
			} else {
				b = (byte) ((Math.abs(b + 1) / 2) - (center / 2));
			}
		}
		return b;
	}

	public byte encode(byte b, int num) {
		if (num >= ENCODE_LIMIT[0]) {
			if (num > ENCODE_LIMIT[1]) {
				num = ENCODE_LIMIT[1];
			}
			for (int i = 0; i < num; i++) {
				b = encodeByte(b);
			}
		}
		return b;
	}

	public byte encode(byte b) {
		return encode(b, DEFAULT_ENCRYPT_NUM);
	}

	public byte[] encode(byte[] bytes, int num) {
		if (bytes != null && bytes.length > 0) {
			byte[] newBytes = new byte[bytes.length];
			for (int i = 0; i < bytes.length; i++) {
				newBytes[i] = encode(bytes[i], num);
			}
			bytes = newBytes;
		}
		return bytes;
	}

	public byte[] encode(byte[] bytes) {
		return encode(bytes, DEFAULT_ENCRYPT_NUM);
	}

	public String encode(String str, int num) {
		if (str != null && !str.isEmpty() && num > 0) {
			try {
				str = new String(encode(str.getBytes(), num), CHARSET);
			} catch (UnsupportedEncodingException e) {
				logger.error(e.getMessage(), e);
			}
		}
		return str;
	}

	public String encode(String str) {
		return encode(str, DEFAULT_ENCRYPT_NUM);
	}

}
