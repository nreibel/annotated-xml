package com.github.nreibel.xml.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Utils {
	
	public static <T extends Annotation> Map<Field, T> getFieldsWithAnnotation(Class<?> clazz, Class<T> annotationClass) {
		Map<Field, T> map = new HashMap<>();

		for (Field field : clazz.getDeclaredFields()) {
			T annotation = field.getAnnotation(annotationClass);
			if (annotation != null) map.put(field, annotation);
		}

		return map;
	}

	public static void forceSetField(Field f, Object instance, Object value) throws IllegalArgumentException, IllegalAccessException {
		boolean accessible = f.isAccessible();
		f.setAccessible(true);
		f.set(instance, value);
		f.setAccessible(accessible);
	}

	public static String join(Collection<String> strings, String separator) {
		String[] array = new String[strings.size()];
		return Utils.join(strings.toArray(array), separator);
	}
	
	public static String join(String[] strings, String separator) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0 ; i < strings.length - 1 ; i++) {
			sb.append(strings[i]);
			sb.append(separator);
		}
		sb.append(strings[strings.length-1]);
		return sb.toString();
	}
}
