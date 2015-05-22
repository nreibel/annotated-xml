package com.github.nreibel.xml.exceptions;

import java.util.LinkedList;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.github.nreibel.xml.utils.NodeHierarchyIterator;
import com.github.nreibel.xml.utils.Utils;

public class AttributeNotFoundException extends Exception {

	private static final long serialVersionUID = 886717166013093573L;

	private final Node el;
	private final String name;

	public AttributeNotFoundException(Element el, String name) {
		this.el = el;
		this.name = name;
	}

	@Override
	public String getMessage() {

		LinkedList<String> parts = new LinkedList<>();
		for(Node n : new NodeHierarchyIterator(el)) {
			parts.addFirst(n.getNodeName());
		}

		String path = Utils.join(parts, " > ");
		String errorStr = String.format("Attribute '%s' not found in node '%s'", name, path);
		return errorStr;
	}
}
