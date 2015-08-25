package com.github.nreibel.xml.exceptions;

import java.util.LinkedList;

import org.w3c.dom.Node;

import com.github.nreibel.xml.utils.NodeHierarchyIterator;

public class NodeNotFoundException extends AnnotatedXmlException {

	private static final long serialVersionUID = -3971178251457187612L;

	private final Node el;
	private final String nodeName;

	public NodeNotFoundException(Node el, String nodeName) {
		this.el = el;
		this.nodeName = nodeName;
	}

	@Override
	public String getMessage() {

		LinkedList<String> parts = new LinkedList<String>();
		for(Node n : new NodeHierarchyIterator(el)) {
			parts.addFirst(n.getNodeName());
		}

		String path = join(parts, " > ");
		String errorStr = String.format("Node '%s' not found in node '%s'", nodeName, path);
		return errorStr;
	}
}
