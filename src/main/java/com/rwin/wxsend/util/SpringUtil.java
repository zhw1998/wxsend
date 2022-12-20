package com.rwin.wxsend.util;

import com.alibaba.druid.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import java.io.File;


/**
 * spring 
 * @author chenli
 * @date 2016-3-8
 * @version 1.0
 */
public class SpringUtil {
	private final static Logger logger = LoggerFactory.getLogger(SpringUtil.class);
	private static ServletContext context;
	private static WebApplicationContext appContext;

	/**
	 * 初始化Spring容器(需在web起动时就初始化)
	 * @param ac
	 */
	public synchronized static void initContext(WebApplicationContext ac){
		if(appContext == null){
			context = ac.getServletContext();
			appContext = ac;
		}
	}
	
	/**
	 * 返回容器 环境上下文
	 * @return
	 */
	public static ServletContext getContext(){
		return context;
	}
	
	/**
	 * 返回Spring容器 环境上下文
	 * @return
	 */
	public static WebApplicationContext getAppContext(){
		return appContext;
	}
	
	/**
	 * 根据类返回实例.
	 * <BR> 不建议使用。如果当前类有多个实现则无法定位到具体使用哪个实现类
	 * @param t
	 * @return
	 */
	public static <T> T getBean(Class<T> t){
		Assert.notNull(appContext);
		return (T)appContext.getBean(t);
	}
	
	/**
	 * 根据发布名称返回实例.
	 * @param name
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name){
		Assert.notNull(name);
		return (T)appContext.getBean(name);
	}
	
	/**
	 * 根据发布名称返回实例.
	 * @param name
	 * @return
	 */
	public static boolean containsBean(String name){
		Assert.notNull(name);
		return appContext.containsBean(name);
	}
	
	/**
	 * 根据类全称返回实例.
	 * @param name
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBeanByClassName(String name){
		if (StringUtils.isEmpty(name)) {
			return null;
		}
		try {
			Class<?> cls=Class.forName(name);
			return (T)appContext.getBean(cls);
		} catch (ClassNotFoundException e) {
			logger.error("根据类全称返回实例失败，找不到类："+name,e);
			return null;
		}
	}

	/**
	 * 获取当前项目的发布路径
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String getProjectPath(){
		return context.getRealPath("/");
	}

	/**
	 * 根据当前项目的绝对路径返回系统的绝对路径。
	 * 如path 为/down/test.html 。项目部署在c:/test/project 则返回c:/test/project/down/test.html
	 * @param path 项目的绝对路径。
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String getPath(String path){
		return context.getRealPath(path);
	}

	/**
	 * 根据classpath相对路径获取 在系统中的绝对路径。
	 * 如relativelyPath 为test/a.txt 。项目部署在c:/test/project 则返回c:/test/project/WEB-INF/classes/test/a.txt
	 * @param relativelyPath
	 * @return
	 */
	public static String getClassPath(String relativelyPath) {
		return SpringUtil.getContext().getRealPath("WEB-INF" + File.separator + "classes" + File.separator + relativelyPath);
	}
}
