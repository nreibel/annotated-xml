package com.github.nreibel.xml.main;

import org.w3c.dom.Element;

import com.github.nreibel.xml.IXmlItemFactory;
import com.github.nreibel.xml.IXmlItem;
import com.github.nreibel.xml.XmlCollection;

public class ChildCollectionFactory implements IXmlItemFactory<XmlCollection<Child>> {

	@Override
	public XmlCollection<Child> createItem(Element el, IXmlItem parent) {
		return new XmlCollection<Child>(Child.Factory.class, el, parent);
	}
}
