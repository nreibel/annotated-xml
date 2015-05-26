package com.github.nreibel.xml;

import org.w3c.dom.Element;

import com.github.nreibel.xml.exceptions.AnnotationParsingException;
import com.github.nreibel.xml.exceptions.AttributeNotFoundException;
import com.github.nreibel.xml.exceptions.NodeNotFoundException;


public class XmlLeaf extends AnnotatedXmlItem {

	private String nodeName;
	private String value = null;

	public XmlLeaf(IXmlItem parent) {
		super(parent);
	}

	@Override
	public void doInitFields(Element el) throws AttributeNotFoundException, AnnotationParsingException, NodeNotFoundException {
		value = el.getTextContent();
		nodeName = el.getNodeName();
	}

	public String getValue() {
		return value;
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

	public static class FactoryImpl implements Descriptor<XmlLeaf> {

		@Override
		public XmlLeaf createItem(IXmlItem parent) {
			return new XmlLeaf(parent);
		}
	}
}
