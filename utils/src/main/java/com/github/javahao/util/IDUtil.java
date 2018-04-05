package com.github.javahao.util;

import java.util.UUID;

public class IDUtil{
	/**
	 * 创建uuid
	 * @return uuid
	 */
	public static String createUUID(){
		return String.valueOf(UUID.randomUUID()).replaceAll("-", "");
	}

	/**
	 * 创建雪花算法id
	 * @return id
	 */
	public static long snkwflakeId(){
		return new SnowflakeIdWorker(0, 0).nextId();
	}
}
