package com.util;

import java.lang.reflect.Field;

public class ReflectUtil {
	/**
	 * get the field value in aObject by aFieldName
	 * 
	 * @param aObject
	 * @param aFieldName
	 * @return
	 */
	public static Object getFieldValue(Object aObject, String aFieldName) {
		Field field = getClassField(aObject.getClass(), aFieldName);// get the
																	// field in
																	// this
																	// object
		if (field != null) {
			field.setAccessible(true);
			try {
				return field.get(aObject);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}

	/**
	 * 这个方法，是最重要的，关键的实现在这里面
	 * 
	 * @param aClazz
	 * @param aFieldName
	 * @return
	 */
	private static Field getClassField(Class aClazz, String aFieldName) {
		Field[] declaredFields = aClazz.getDeclaredFields();
		for (Field field : declaredFields) {
			// 注意：这里判断的方式，是用字符串的比较。很傻瓜，但能跑。要直接返回Field。我试验中，尝试返回Class，然后用getDeclaredField(String
			// fieldName)，但是，失败了
			if (field.getName().equals(aFieldName)) {
				return field;// define in this class
			}
		}

		Class superclass = aClazz.getSuperclass();
		if (superclass != null) {// 简单的递归一下
			return getClassField(superclass, aFieldName);
		}
		return null;
	}
}
