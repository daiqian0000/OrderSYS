package com.asion.common.message;

import java.util.List;

import com.asion.common.message.model.VerifyInfo;

/**
 * 手机短信发送服务接口
 * 
 * @author chenboyang
 *
 */
public interface MessageSendService {

	/**
	 * 发送失败
	 */
	String FAIL = "fail";
	
	/**
	 * 单条消息发送
	 * 
	 * @param mobile
	 *            手机号
	 * @param text
	 *            消息文本
	 * @return 操作结果
	 */
	Object singleMessageSend(String mobile, String text);

	/**
	 * 消息批量发送
	 * 
	 * @param mobiles
	 *            手机号集合
	 * @param text
	 *            消息文本
	 * @return 操作结果
	 */
	Object batchMessageSend(List<String> mobiles, String text);

	/**
	 * 消息批量发送不同手机号
	 * 
	 * @param mobiles
	 *            手机号集合
	 * @param texts
	 *            消息文本集合
	 * @return 操作结果
	 */
	Object multiMessageSend(List<String> mobiles, List<String> texts);
	
	/**
	 * 发送验证码至手机号
	 * 
	 * @param mobile
	 *            手机号
	 * @return 验证码或发送失败标识（fail）
	 */
	String messageVerify(String mobile);
	
	/**
	 * 发送验证码至手机号
	 * 
	 * @param mobile
	 *            手机号
	 * @param verifyInfo
	 *            验证码配置信息
	 * 
	 * @return 验证码或发送失败标识（fail）
	 */
	String messageVerify(String mobile, VerifyInfo verifyInfo);

	/**
	 * 校验手机号验证码发送间隔
	 * 
	 * @param mobile
	 *            手机号
	 * @return 是否在间隔时间外
	 */
	boolean verifySendInterval(String mobile);

	/**
	 * 校验手机号验证码是否有效
	 * 
	 * @param mobile
	 *            手机号
	 * @param verifyCode
	 *            验证码
	 * @return 是否有效
	 */
	boolean verifyCodeTimeout(String mobile, String verifyCode);

}
