package com.asion.business.web.websocket;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

import org.apache.log4j.Logger;

/**
 * WebSocket基础功能抽象类
 * 
 * @author chenboyang
 *
 */
public abstract class AbstractWebSocket implements WebSocket {

	/**
	 * 日志记录
	 */
	protected Logger logger = Logger.getLogger(getClass());

	/**
	 * 当前连接会话
	 */
	protected Session session;

	/**
	 * 位置信息WebSocket连接集合
	 * 
	 * @return 会话集合
	 */
	protected abstract CopyOnWriteArraySet<WebSocket> webSocketSet();

	/**
	 * 获取在线客户记录数
	 */
	public int getOnlineCount() {
		return webSocketSet().size();
	}

	@OnOpen
	public void onOpen(Session session) {
		this.session = session;
		webSocketSet().add(this);
	}

	@OnClose
	public void onClose() {
		webSocketSet().remove(this);
	}

	@OnMessage
	public void onMessage(String message, Session session) {
		logger.info(message);
	}

	@OnError
	public void onError(Session session, Throwable error) {
		logger.error(error.getMessage(), error);
	}

	@Override
	public void sendMessage(String message) {
		try {
			if (session.isOpen()) {
				session.getBasicRemote().sendText(message);
			}
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

	@Override
	public void sendMessageAll(String message) {
		for (WebSocket item : webSocketSet()) {
			item.sendMessage((String) message);
		}
	}

}
