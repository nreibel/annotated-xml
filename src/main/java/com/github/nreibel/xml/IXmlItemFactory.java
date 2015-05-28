package com.github.nreibel.xml;

import org.w3c.dom.Element;

public interface IXmlItemFactory<T extends AnnotatedXmlItem> {
	T createItem(Element el, IXmlItem parent);
}
