package com.atguigu.atcrowdfunding.listener;

import com.atguigu.atcrowdfunding.util.Const;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * ServletContextListener(监听器)：监听服务器启动和关闭时触发的事件（重点监听ServletContext对象的创建和销毁）
 */
public class StartSystemInitListener implements ServletContextListener {
    /**
     * 服务器启动时执行的事件处理
     * @param servletContextEvent
     */
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext application = servletContextEvent.getServletContext();
        String contextPath = application.getContextPath();
        application.setAttribute(Const.PATH, contextPath);
    }

    /**
     * 服务器停止时执行的事件处理
     * @param servletContextEvent
     */
    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
