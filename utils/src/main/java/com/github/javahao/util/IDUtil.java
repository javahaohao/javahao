package com.github.javahao.util;

import java.util.UUID;

public class IDUtil{
	
	public static String createUUID(){
		return String.valueOf(UUID.randomUUID()).replaceAll("-", "");
	}
	
}
