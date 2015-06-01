package com.github.nreibel.xml;

import java.util.Collection;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public interface IXmlItem {
	
	Document getDocument();
	
	Collection<? extends IXmlItem> getChildren();
	
	Collection<? extends IXmlAttribute> getAttributes();
	
	String getNodeName();
	
	Element toElement();
}
