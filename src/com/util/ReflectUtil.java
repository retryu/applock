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
	 * ���������������Ҫ�ģ��ؼ���ʵ����������
	 * 
	 * @param aClazz
	 * @param aFieldName
	 * @return
	 */
	private static Field getClassField(Class aClazz, String aFieldName) {
		Field[] declaredFields = aClazz.getDeclaredFields();
		for (Field field : declaredFields) {
			// ע�⣺�����жϵķ�ʽ�������ַ����ıȽϡ���ɵ�ϣ������ܡ�Ҫֱ�ӷ���Field���������У����Է���Class��Ȼ����getDeclaredField(String
			// fieldName)�����ǣ�ʧ����
			if (field.getName().equals(aFieldName)) {
				return field;// define in this class
			}
		}

		Class superclass = aClazz.getSuperclass();
		if (superclass != null) {// �򵥵ĵݹ�һ��
			return getClassField(superclass, aFieldName);
		}
		return null;
	}
}
