package com.github.javahao.util;

import java.util.Properties;

public class PropertiesUtil {
	/**
	 * 属性文件加载对象
	 */
	private static Properties properties = FileUtil.readPropertiesUtil("resources.properties", FileUtil.CLASSPATH);

	public static void setProperties(Properties properties) {
		PropertiesUtil.properties = properties;
	}

	/**
	 * 从reources.xml中获取值
	 * @param key k值
	 * @return 结果
	 */
	public static String getProperty(String key){
		return properties.getProperty(key);
	}
}
