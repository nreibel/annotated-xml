package com.github.nreibel.xml.impl;

import org.w3c.dom.Element;

import com.github.nreibel.xml.IXmlItemFactory;
import com.github.nreibel.xml.IXmlItem;
import com.github.nreibel.xml.XmlCollection;

public class LeafCollectionFactory implements IXmlItemFactory<XmlCollection<XmlLeaf>> {

	@Override
	public XmlCollection<XmlLeaf> createItem(Element el, IXmlItem parent) {
		return new XmlCollection<XmlLeaf>(XmlLeaf.Factory.class, el, parent);
	}
}
