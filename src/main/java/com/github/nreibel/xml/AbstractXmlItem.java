package com.github.nreibel.xml;

import java.io.StringWriter;
import java.util.Collection;
import java.util.Collections;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public abstract class AbstractXmlItem implements IXmlItem {

	private final IXmlItem parent;

	public AbstractXmlItem(IXmlItem parent) {
		this.parent = parent;
	}

	@Override
	public Document getDocument() {
		if (parent == null) {
			String method = Thread.currentThread().getStackTrace()[1].getMethodName();
			String message = String.format("One of %s parent classes must implement %s()", this.getClass().getSimpleName(), method);
			throw new NullPointerException(message);
		}
		return parent.getDocument();
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

	@Override
	public String toString() {
		try {
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			StreamResult result = new StreamResult(new StringWriter());
			DOMSource source = new DOMSource(this.toElement());
			transformer.transform(source, result);
			return result.getWriter().toString();
		}
		catch (TransformerException e) {
			e.printStackTrace();
			return null;
		}
	}
}
