package com.github.nreibel.xml;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.github.nreibel.xml.utils.Utils;

public abstract class AbstractXmlItem implements IXmlItem {

	private final IXmlItem parent;

	public AbstractXmlItem(IXmlItem parent) {
		this.parent = parent;
	}

	@Override
	public Document getDocument() {
		if (parent == null) {
			String message = String.format("One of %s parent classes must implement getDocument()", this.getClass().getSimpleName());
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
		Element root = this.getDocument().createElement(this.getNodeName());

		for(IXmlItem i : this.getChildren()) {
			root.appendChild(i.toElement());
		}

		for(IXmlAttribute attr : this.getAttributes()) {
			root.setAttribute(attr.getName(), attr.getValue());
		}

		return root;
	}

	private final String getAttributesString() {
		Collection<String> strings = new LinkedList<>();

		for(IXmlAttribute attr : this.getAttributes()) {
			String str = String.format("%s=\"%s\"", attr.getName(), attr.getValue());
			strings.add(str);
		}

		return Utils.join(strings,  " ");
	}

	@Override
	public String toString() {

		if (this.getChildren().isEmpty()) {
			if (this.getAttributes().isEmpty()) return String.format("<%s/>", this.getNodeName());
			else return String.format("<%s %s/>", this.getNodeName(), this.getAttributesString());
		}
		else {
			String head = null;
			String foot = String.format("</%s>", this.getNodeName());

			if (this.getAttributes().isEmpty()) head = String.format("<%s>\n", this.getNodeName());
			else head = String.format("<%s %s>\n", this.getNodeName(), this.getAttributesString());

			StringBuilder sb = new StringBuilder();

			sb.append(head);
			for (IXmlItem child : this.getChildren()) {
				String childStr = child.toString().replaceAll("(?m)^", "  ");
				sb.append(childStr);
				sb.append("\n");
			}
			sb.append(foot);

			return sb.toString();
		}
	}
}
