package com.github.nreibel.xml;

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

}
