package com.asion.common.base;

import com.asion.common.util.JsonUtil;

/**
 * 基础数据模型
 * 
 * @author chenboyang
 *
 */
@Deprecated
public abstract class BaseModel {

	/**
	 * 日志记录
	 */
	/*protected Logger logger = Logger.getLogger(getClass());*/

	/**
	 * 模型浅度克隆
	 * 
	 * @return 克隆原型
	 */
	/*public <T> T shallowClone() {
		T t = null;
		try {
			t = (T) super.clone();
		} catch (CloneNotSupportedException e) {
			logger.error(e.getMessage(), e);
		}
		return t;
	}*/
	
	/**
	 * 模型深度克隆
	 * 
	 * @return 克隆原型
	 */
	/*public <T> T deepClone() {
		T t = null;
		ByteArrayOutputStream baos = null;
		ObjectOutputStream oos = null;
		ByteArrayInputStream bis = null;
		ObjectInputStream ois = null;
		try {
			baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			oos.writeObject(this);
			bis = new ByteArrayInputStream(baos.toByteArray());
			ois = new ObjectInputStream(bis);
			t = (T) ois.readObject();
		} catch (IOException | ClassNotFoundException e) {
			logger.error(e.getMessage(), e);
		} finally {
			IOUtils.closeQuietly(ois);
			IOUtils.closeQuietly(bis);
			IOUtils.closeQuietly(oos);
			IOUtils.closeQuietly(baos);
		}
		return t;
	}*/

	/**
	 * 重写toString方法以JSON字符串形式返回
	 * 
	 * @return JSON字符串
	 */
	public String toJson() {
		return JsonUtil.toJson(this);
	}
	
}
