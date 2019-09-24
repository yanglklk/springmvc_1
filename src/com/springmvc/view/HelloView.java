package com.springmvc.view;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.View;
@Component
public class HelloView implements View {

	@Override
	public String getContentType() {
		// 返回内容类型
		return "test/html";
	}

	@Override
	public void render(Map<String, ?> arg0, HttpServletRequest arg1, HttpServletResponse arg2) throws Exception {
		arg2.getWriter().println("hello view,time "+new Date());
		
	}

}
