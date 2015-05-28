package com.github.nreibel.xml.impl;

import com.github.nreibel.xml.IXmlAttribute;

public class DefaultXmlAttribute implements IXmlAttribute {

	private final String name;
	private final String value;
	
	public DefaultXmlAttribute(String name, String value) {
		this.name = name;
		this.value = value;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getValue() {
		return value;
	}
	
	@Override
	public String toString() {
		return String.format("%s{%s=%s}", this.getClass().getSimpleName(), name, value);
	}
}
