package com.spring.cloud.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 应用获得Spring容器Bean 对象工具类
 * @author sunsx
 *
 */
public class BeanFactoryUtils implements ApplicationContextAware {
	private static boolean initContext = false;
	private static final Set<ApplicationContext> contexts = new ConcurrentHashSet<ApplicationContext>();

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		contexts.add(applicationContext);
		BeanFactoryUtils.initContext = true;
	}

	/**
	 * 根据bean的Class类型获取spring容器中实例化的Bean集合
	 * 
	 * @param type
	 *            bean的class类型
	 * @throws IllegalStateException
	 *             没有在spring的配置文件中配置BeanFactoryUtils
	 * @return 泛型 由调用的程序指定返回类型，如果查询不到返回null
	 */
	public static <T> T getBeanByClassType(Class<T> type)
			throws IllegalStateException {
		if (!initContext) {
			throw new IllegalStateException("ApplicationContext 尚未初始化");
		}

		for (ApplicationContext context : contexts) {
			return (T) context.getBean(type);
		}
		return null;
	}

	/**
	 * 根据bean的name(spring配置文件中的bean的id属性)获取spring容器中实例化的Bean
	 * 
	 * @param type
	 *            bean的class类型
	 * @param beanName
	 *            spring配置文件中的bean的id属性
	 * @throws IllegalStateException
	 *             没有在spring的配置文件中配置BeanFactoryUtils
	 * @return 泛型 由调用的程序指定返回类型，如果查询不到返回null
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(Class<T> type, String beanName)
			throws IllegalStateException {
		if (!initContext) {
			throw new IllegalStateException("ApplicationContext 尚未初始化");
		}
		for (ApplicationContext context : contexts) {
			if (context.containsBean(beanName)) {
				Object bean = context.getBean(beanName);
				if (type.isInstance(bean)) {
					return (T) bean;
				}
			}
		}
		return null;
	}

	/**
	 * 根据bean的name(spring配置文件中的bean的id属性)获取spring容器中实例化的Bean
	 * 
	 * @param beanName
	 *            (spring配置文件中的bean的id属性)
	 * @throws IllegalStateException
	 *             没有在spring的配置文件中配置BeanFactoryUtils
	 * @return 泛型 由调用的程序指定返回类型，如果查询不到返回null
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String beanName) throws IllegalStateException {
		if (!initContext) {
			throw new IllegalStateException("ApplicationContext 尚未初始化");
		}
		for (ApplicationContext context : contexts) {
			if (context.containsBean(beanName)) {
				return (T) context.getBean(beanName);
			}
		}
		return null;
	}

	public static String getEnvironment() {
		if (!initContext) {
			throw new IllegalStateException("ApplicationContext 尚未初始化");
		}
		for (ApplicationContext context : contexts) {
			try {
				return context.getEnvironment().getActiveProfiles()[0];
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static Map<String, Object> getBeansWithAnnotation(
			Class<? extends Annotation> annotationType) {
		Map<String, Object> beans = new HashMap<String, Object>();
		if (!initContext) {
			throw new IllegalStateException("ApplicationContext 尚未初始化");
		}
		for (ApplicationContext context : contexts) {
			beans.putAll(context.getBeansWithAnnotation(annotationType));
		}
		return beans;
	}

}
