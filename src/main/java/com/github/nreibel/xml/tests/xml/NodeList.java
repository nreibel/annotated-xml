package com.github.nreibel.xml.tests.xml;

import java.util.Collection;

import com.github.nreibel.xml.AnnotatedXmlNode;
import com.github.nreibel.xml.annotations.XmlCollection;

public class NodeList extends AnnotatedXmlNode {

	@XmlCollection(Class=Child.class, NodeName="child")
	public Collection<Child> children;
}
