package com.github.nreibel.xml;

import java.util.Collection;
import java.util.LinkedList;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.github.nreibel.xml.exceptions.AnnotatedXmlException;
import com.github.nreibel.xml.exceptions.AnnotationParsingException;

public class XmlCollection<T extends AnnotatedXmlItem> extends AnnotatedXmlItem {

	private final Collection<T> children = new LinkedList<>();
	private final Class<? extends IXmlItemFactory<T>> factoryClass;

	public XmlCollection(Class<? extends IXmlItemFactory<T>> factory, Element el, IXmlItem parent) {
		super(el, parent);
		this.factoryClass = factory;
	}

	@Override
	public Collection<T> getChildren() {
		return children;
	}
	
	@Override
	public void doInitFields() throws AnnotatedXmlException {

		IXmlItemFactory<T> factory = null;

		try {
			factory = factoryClass.newInstance();
		} catch (Exception e) {
			throw new AnnotationParsingException(e);
		}

		NodeList nl = this.getElement().getElementsByTagName("*");

		for (int i = 0 ; i < nl.getLength() ; i++) {
			Element child = (Element) nl.item(i);
			T leaf = factory.createItem(child, this);
			leaf.doInitFields();
			children.add(leaf);
		}
	}
}
