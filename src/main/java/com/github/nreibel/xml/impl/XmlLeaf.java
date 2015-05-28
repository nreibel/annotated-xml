package com.github.nreibel.xml.impl;

import java.util.Collection;
import java.util.Collections;

import org.w3c.dom.Element;

import com.github.nreibel.xml.AnnotatedXmlItem;
import com.github.nreibel.xml.IXmlItemFactory;
import com.github.nreibel.xml.IXmlAttribute;
import com.github.nreibel.xml.IXmlItem;
import com.github.nreibel.xml.exceptions.AnnotationParsingException;
import com.github.nreibel.xml.exceptions.AttributeNotFoundException;
import com.github.nreibel.xml.exceptions.NodeNotFoundException;


public class XmlLeaf extends AnnotatedXmlItem {

	private String value = null;

	public XmlLeaf(Element el, IXmlItem parent) {
		super(el, parent);
	}
	
	@Override
	public void doInitFields() throws AttributeNotFoundException, AnnotationParsingException, NodeNotFoundException {
		value = this.getElement().getTextContent();
	}

	public String getTextContent() {
		return value;
	}
	
	@Override
	public Collection<? extends IXmlItem> getChildren() {
		return Collections.emptyList();
	}

	@Override
	public Collection<? extends IXmlAttribute> getAttributes() {
		return Collections.emptyList();
	}

	@Override
	public Element toElement() {
		Element el = super.toElement();
		el.setTextContent(value);
		return el;
	}

	@Override
	public String toString() {
		return String.format("<%s>%s</%s>", this.getNodeName(), value, this.getNodeName());
	}

	public static class Factory implements IXmlItemFactory<XmlLeaf> {

		@Override
		public XmlLeaf createItem(Element el, IXmlItem parent) {
			return new XmlLeaf(el, parent);
		}
	}
}
