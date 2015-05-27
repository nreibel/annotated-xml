package com.github.nreibel.xml;

import java.util.Collection;
import java.util.Collections;

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

	public String getTextContent() {
		return value;
	}

	@Override
	public String getNodeName() {
		return nodeName;
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
		Element el = this.getDocument().createElement(nodeName);
		el.setTextContent(value);
		return el;
	}

	@Override
	public String toString() {
		return String.format("<%s>%s</%s>", nodeName, value, nodeName);
	}

	public static class FactoryImpl implements Descriptor<XmlLeaf> {

		@Override
		public XmlLeaf createItem(IXmlItem parent) {
			return new XmlLeaf(parent);
		}
	}
}
