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

		StringBuilder sb    = new StringBuilder();
		String[]      array = new String[strings.size()];
		
		array = strings.toArray(array);
		
		for (int i = 0 ; i < array.length - 1 ; i++) {
			sb.append(array[i]);
			sb.append(separator);
		}
		sb.append(array[array.length-1]);
		return sb.toString();
	}

	public static Element getFirstElementOrDie(Element element, String nodeName) throws NodeNotFoundException {
		Node n = element.getFirstChild();
		while(n != null) {
			if (n.getNodeType() == Node.ELEMENT_NODE && n.getNodeName().equals(nodeName)) return (Element) n;
			n = n.getNextSibling();
		}
		throw new NodeNotFoundException(element, nodeName);
	}
}
