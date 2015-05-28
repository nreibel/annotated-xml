package com.github.nreibel.xml.main;

import org.w3c.dom.Element;

import com.github.nreibel.xml.AnnotatedXmlItem;
import com.github.nreibel.xml.IXmlItemFactory;
import com.github.nreibel.xml.IXmlItem;
import com.github.nreibel.xml.annotations.XmlAttribute;


public class Child extends AnnotatedXmlItem {

	@XmlAttribute
	private String name = "defaultName";

	public Child(Element el, IXmlItem parent) {
		super(el, parent);
	}

	public static class Factory implements IXmlItemFactory<Child> {
		@Override
		public Child createItem(Element el, IXmlItem parent) {
			return new Child(el, parent);
		}
	}
}
