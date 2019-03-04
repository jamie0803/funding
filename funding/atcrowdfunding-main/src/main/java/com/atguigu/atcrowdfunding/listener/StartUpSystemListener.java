package com.atguigu.atcrowdfunding.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 监听ServletContext对象的创建和销毁.
 */
public class StartUpSystemListener implements ServletContextListener {

	//在ServletContext对象被创建时执行.
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext application = sce.getServletContext();
		String contextPath = application.getContextPath();
		application.setAttribute("APP_PATH", contextPath);
		System.out.println("将上下文路径存放到了application域中....");
	}

	//在ServletContext对象被销毁时执行.
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub

	}

}
