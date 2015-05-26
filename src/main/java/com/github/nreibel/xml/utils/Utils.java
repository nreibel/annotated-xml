package com.github.nreibel.xml.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.github.nreibel.xml.exceptions.NodeNotFoundException;

public class Utils {

	public static <T extends Annotation> Map<Field, T> getFieldsWithAnnotation(Class<?> clazz, Class<T> annotationClass) {
		Map<Field, T> map = new HashMap<>();

		for (Field field : clazz.getDeclaredFields()) {
			T annotation = field.getAnnotation(annotationClass);
			if (annotation != null) map.put(field, annotation);
		}

		return map;
	}

	public static void setField(Field f, Object instance, Object value) throws IllegalArgumentException, IllegalAccessException {
		boolean accessible = f.isAccessible();
		f.setAccessible(true);
		f.set(instance, value);
		f.setAccessible(accessible);
	}

	public static Object getField(Field f, Object instance) throws IllegalArgumentException, IllegalAccessException {
		boolean accessible = f.isAccessible();
		f.setAccessible(true);
		Object value = f.get(instance);
		f.setAccessible(accessible);
		
		return value;
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

	public static String getElementValueOrDie(Element el, String tagName) throws NodeNotFoundException {
		try {
			return getFirstElementOrDie(el, tagName).getTextContent();
		}
		catch(Exception ex) {
			throw new NodeNotFoundException(el, tagName);
		}
	}

	public static Element getFirstElementOrDie(Element el, String tagName) throws NodeNotFoundException {
		Node ret = null;
		ret = el.getElementsByTagName(tagName).item(0);
		if (ret == null) throw new NodeNotFoundException(el, tagName);
		return Element.class.cast(ret);
	}
}
