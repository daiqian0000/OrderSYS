package com.asion.business.web.websocket;

import javax.websocket.Session;

/**
 * WebSocket服务端处理接口
 * 
 * @author chenboyang
 *
 */
public interface WebSocket {

	/**
	 * 有会话连接成功
	 * 
	 * @param session
	 *            会话
	 */
	void onOpen(Session session);

	/**
	 * 会话连接关闭
	 */
	void onClose();

	/**
	 * 收到客户端消息
	 * 
	 * @param message
	 *            客户端发送过来的消息
	 * @param session
	 *            会话
	 */
	void onMessage(String message, Session session);

	/**
	 * 发生错误
	 * 
	 * @param session
	 *            客户端
	 * @param error
	 *            错误信息
	 */
	void onError(Session session, Throwable error);

	/**
	 * 发送消息到当前客户端
	 * 
	 * @param message
	 *            消息
	 */
	void sendMessage(String message);
	
	/**
	 * 发送消息到所有客户端
	 * 
	 * @param message
	 *            消息
	 */
	void sendMessageAll(String message);

}
