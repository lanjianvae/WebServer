package com.lanjian.exception.handler;

import java.io.IOException;

import com.lanjian.exception.base.ServletException;
import com.lanjian.response.ServletResponse;

/**
 * @explain 会根据异常对应的状态码设置response的状态
 * @author lanjian
 * @date 2019年3月2日
 */
public class ExceptionHandler {
	public void handle(ServletException e, ServletResponse response) {
		e.printStackTrace();
		try {
			response.flush(e.getStatus());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
