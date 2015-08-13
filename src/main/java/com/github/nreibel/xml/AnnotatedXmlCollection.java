package com.github.nreibel.xml;

import java.util.Collection;
import java.util.LinkedList;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.github.nreibel.xml.exceptions.AnnotatedXmlException;

public abstract class AnnotatedXmlCollection<T extends IAnnotatedXmlItem> implements IAnnotatedXmlItem {

	private final Collection<T> children = new LinkedList<>();

	abstract protected Class<T> getChildClass();
	abstract protected String getChildNodeName();

	@Override
	public final void doInitFields(Element el) throws AnnotatedXmlException {

		Class<T> clazz = this.getChildClass();

		NodeList nl = el.getElementsByTagName(this.getChildNodeName());

		for (int i = 0 ; i < nl.getLength() ; i++) {
			Element childNode = (Element) nl.item(i);
			try {
				T leaf = clazz.newInstance();
				leaf.doInitFields(childNode);
				children.add(leaf);
			}
			catch (InstantiationException | IllegalAccessException e) {
				throw new AnnotatedXmlException("Can't create object of class " + clazz, e);
			}
		}
	}

	public Collection<T> getChildren() {
		return children;
	}
}
