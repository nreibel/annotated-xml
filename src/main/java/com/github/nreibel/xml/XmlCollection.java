package com.github.nreibel.xml;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.github.nreibel.xml.exceptions.AnnotationParsingException;
import com.github.nreibel.xml.exceptions.AttributeNotFoundException;
import com.github.nreibel.xml.exceptions.NodeNotFoundException;
import com.github.nreibel.xml.utils.FilteringIterator;
import com.github.nreibel.xml.utils.FilteringIterator.Filter;

public class XmlCollection extends AnnotatedXmlItem {

	private String nodeName;
	private final Collection<XmlLeaf> children = new LinkedList<>();

	private XmlCollection(IXmlItem parent) {
		super(parent);
	}

	@Override
	public void doInitFields(Element el) throws AttributeNotFoundException, AnnotationParsingException, NodeNotFoundException {

		nodeName = el.getNodeName();

		NodeList nl = el.getElementsByTagName("*");
		
		for (int i = 0 ; i < nl.getLength() ; i++) {
			Element child = (Element) nl.item(i);
			XmlLeaf leaf = new XmlLeaf(this);
			leaf.doInitFields(child);
			children.add(leaf);
		}
	}

	@Override
	public Collection<? extends IXmlItem> getChildren() {
		return children;
	}

	@Override
	public String getNodeName() {
		return nodeName;
	}

	@Override
	public Collection<? extends IXmlAttribute> getAttributes() {
		return Collections.emptyList();
	}

	public Iterable<XmlLeaf> getChildrenByTag(final String tag) {
		Filter<XmlLeaf> filter = new Filter<XmlLeaf>() {
			@Override public boolean matches(XmlLeaf obj) {
				return obj.getNodeName().equals(tag);
			}
		};

		return new FilteringIterator<XmlLeaf>(children.iterator(), filter);
	}

	public static class FactoryImpl implements Descriptor<XmlCollection> {

		@Override
		public XmlCollection createItem(IXmlItem parent) {
			return new XmlCollection(parent);
		}
	}
}
