package com.github.nreibel.xml.exceptions;

import java.util.Collection;

public class AnnotatedXmlException extends Exception {

	private static final long serialVersionUID = -5026102165483369200L;

	public AnnotatedXmlException() {
		super();
	}

	public AnnotatedXmlException(String message, Throwable cause) {
		super(message, cause);
	}

	public AnnotatedXmlException(String message) {
		super(message);
	}

	public AnnotatedXmlException(Throwable cause) {
		super(cause);
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
}
