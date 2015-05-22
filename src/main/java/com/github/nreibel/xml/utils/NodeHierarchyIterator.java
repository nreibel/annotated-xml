package com.github.nreibel.xml.utils;

import java.util.Iterator;

import org.w3c.dom.Node;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class NodeHierarchyIterator implements Iterator<Node>, Iterable<Node> {

	private Node current = null;

	public NodeHierarchyIterator(Node node) {
		this.current = node;
	}

	@Override
	public boolean hasNext() {
		return current != null;
	}

	@Override
	public Node next() {
		Node toReturn = current;
		current = current.getParentNode();
		return toReturn;
	}

	@Override
	public void remove() {
		throw new NotImplementedException();
	}

	@Override
	public Iterator<Node> iterator() {
		return this;
	}
}