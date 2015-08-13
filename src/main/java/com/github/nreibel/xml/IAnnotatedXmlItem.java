package com.github.nreibel.xml;

import org.w3c.dom.Element;

import com.github.nreibel.xml.exceptions.AnnotatedXmlException;

public interface IAnnotatedXmlItem {
	void doInitFields(Element element) throws AnnotatedXmlException;
}