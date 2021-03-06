package com.lanjian.session;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.lanjian.context.WebApplication;

/**
 * @explain session域
 * @author lanjian
 * @date 2019年3月2日
 */
public class HttpSession {
	// 用于标识不同的用户
	private String id;
	// session域
	private Map<String, Object> attributes;
	// session是否有效，默认有效，过期就将该值设为false
	private boolean isValid;
	// 用于判断sessiion是否过期，标准为当前时间-上次访问时间 >= 阈值
	private Instant lastAccessed;

	public HttpSession(String id) {
		this.id = id;
		this.attributes = new ConcurrentHashMap<>();
		this.isValid = true;
		this.lastAccessed = Instant.now();
	}

	/**
	 * @explain 使当前session失效，之后就无法读写当前session了。并会清除session数据，并且在servletContext中删除此session
	 */
	public void invalidate() {
		this.isValid = false;
		this.attributes.clear();
		// 在ServletContext管理的session中去掉该session
		WebApplication.getServletContext().invalidateSession(this);
	}

	public Object getAttribute(String key) {
		if (isValid) {
			this.lastAccessed = Instant.now();
			return attributes.get(key);
		}
		throw new IllegalStateException("session has invalidated");
	}

	public void setAttribute(String key, Object value) {
		if (isValid) {
			this.lastAccessed = Instant.now();
			attributes.put(key, value);
		} else {
			throw new IllegalStateException("session has invalidated");
		}
	}

	public String getId() {
		return id;
	}

	public Instant getLastAccessed() {
		return lastAccessed;
	}

	public void removeAttribute(String key) {
		attributes.remove(key);
	}

}
