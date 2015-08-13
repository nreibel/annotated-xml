package com.github.nreibel.xml;

import org.w3c.dom.Element;

import com.github.nreibel.xml.exceptions.AnnotatedXmlException;

public class AnnotatedXmlLeaf implements IAnnotatedXmlItem {

	private Element el = null;

	@Override
	public void doInitFields(Element element) throws AnnotatedXmlException {
		this.el = element;
	}

	public String getText() {
		return el.getTextContent();
	}
}
