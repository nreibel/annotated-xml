package com.github.nreibel.xml;

import org.w3c.dom.Element;

public class XmlItemWithValue extends AbstractXmlItem {

	private final String nodeName;
	private String value;

	public XmlItemWithValue(IXmlItem parent, String nodeName) {
		this(parent, nodeName, null);
	}

	public XmlItemWithValue(IXmlItem parent, String nodeName, String value) {
		super(parent);
		this.nodeName = nodeName;
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String getNodeName() {
		return nodeName;
	}

	@Override
	public Element toElement() {
		Element el = super.toElement();
		el.setTextContent(value);
		return el;
	}

	@Override
	public String toString() {
		String str = String.format("%s{name=%s;value=%s}", this.getClass().getSimpleName(), nodeName, value);
		return str;
	}
}
