package com.github.nreibel.xml.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.github.nreibel.xml.exceptions.NodeNotFoundException;

public class Utils {

	/**
	 * Get all fields with a specifid annotation on them 
	 * @param clazz Class to look into
	 * @param annotationClass Annotation to search
	 * @return A map of the field and their annotations
	 */
	public static <T extends Annotation> Map<Field, T> getFieldsWithAnnotation(Class<?> clazz, Class<T> annotationClass) {
		Map<Field, T> map = new HashMap<>();

		while(clazz != null) {
			for (Field field : clazz.getDeclaredFields()) {
				T annotation = field.getAnnotation(annotationClass);
				if (annotation != null) map.put(field, annotation);
			}
			clazz = clazz.getSuperclass();
		}

		return map;
	}

	/**
	 * Set a field of an instance to a specific value
	 * @param field The field to set
	 * @param instance The instance on which the field must be set
	 * @param value The value to set
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static void setField(Field field, Object instance, Object value) throws IllegalArgumentException, IllegalAccessException {
		boolean accessible = field.isAccessible();
		field.setAccessible(true);
		field.set(instance, value);
		field.setAccessible(accessible);
	}

	/**
	 * Get the first <i>direct</i> child of an element with a given name
	 * @param element Parent element
	 * @param nodeName Tag name
	 * @return The first direct child with this tag name
	 * @throws NodeNotFoundException
	 */
	public static Element getFirstElementOrDie(Element element, String nodeName) throws NodeNotFoundException {
		Node n = element.getFirstChild();
		while(n != null) {
			if (n.getNodeType() == Node.ELEMENT_NODE && n.getNodeName().equals(nodeName)) return (Element) n;
			n = n.getNextSibling();
		}
		throw new NodeNotFoundException(element, nodeName);
	}
}
