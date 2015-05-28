package com.github.nreibel.xml;

import java.util.Collection;
import java.util.LinkedList;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.github.nreibel.xml.exceptions.AnnotationParsingException;
import com.github.nreibel.xml.exceptions.AttributeNotFoundException;
import com.github.nreibel.xml.exceptions.NodeNotFoundException;

public class XmlCollection<T extends AnnotatedXmlItem> extends AnnotatedXmlItem {

	private final Collection<T> children = new LinkedList<>();
	private final Class<? extends IXmlItemFactory<T>> factoryClass;

	public XmlCollection(Class<? extends IXmlItemFactory<T>> factory, Element el, IXmlItem parent) {
		super(el, parent);
		this.factoryClass = factory;
	}

	@Override
	public void doInitFields() throws AttributeNotFoundException, AnnotationParsingException, NodeNotFoundException {

		IXmlItemFactory<T> factory = null;

		try {
			factory = factoryClass.newInstance();
		} catch (Exception e) {
			throw new AnnotationParsingException(e);
		}

		NodeList nl = this.getElement().getElementsByTagName("*");

		for (int i = 0 ; i < nl.getLength() ; i++) {
			Element child = (Element) nl.item(i);
			T leaf = factory.createItem(child, this);
			leaf.doInitFields();
			children.add(leaf);
		}
	}

	@Override
	public Collection<T> getChildren() {
		return children;
	}

	/*
	public Iterable<XmlLeaf> getChildrenByTag(final String tag) {
		Filter<XmlLeaf> filter = new Filter<XmlLeaf>() {
			@Override public boolean matches(XmlLeaf obj) {
				return obj.getNodeName().equals(tag);
			}
		};

		return new FilteringIterator<XmlLeaf>(children.iterator(), filter);
	}

	public static abstract class Factory<T extends AnnotatedXmlItem, F extends Descriptor<T>> implements Descriptor<XmlCollection<T, F>> {

		@Override
		public XmlCollection<T, F> createItem(Element el, IXmlItem parent) {
			return new XmlCollection<T, F>(el, parent);
		}
	}
	 */
}
