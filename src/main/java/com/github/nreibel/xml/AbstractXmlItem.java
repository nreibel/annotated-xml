package com.github.nreibel.xml;

import java.util.Collection;
import java.util.Collections;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.github.nreibel.xml.exceptions.NodeNotFoundException;

public abstract class AbstractXmlItem implements IXmlItem {

	private final IXmlItem parent;

	public AbstractXmlItem(IXmlItem parent) {
		this.parent = parent;
	}

	@Override
	public Document getDocument() {
		return parent != null ? parent.getDocument() : null;
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
		Element root = getDocument().createElement(this.getNodeName());

		for(IXmlItem i : this.getChildren()) {
			root.appendChild(i.toElement());
		}

		for(IXmlAttribute attr : this.getAttributes()) {
			root.setAttribute(attr.getName(), attr.getValue());
		}

		return root;
	}

	public static String getElementValue(Element el, String tagName) throws NodeNotFoundException {
		try {
			return getFirstElement(el, tagName).getTextContent();
		}
		catch(Exception ex) {
			throw new NodeNotFoundException(el, tagName);
		}
	}

	public static Element getFirstElement(Element el, String tagName) throws NodeNotFoundException {
		try {
			return (Element) el.getElementsByTagName(tagName).item(0);
		}
		catch(Exception ex) {
			throw new NodeNotFoundException(el, tagName);
		}
	}
}
