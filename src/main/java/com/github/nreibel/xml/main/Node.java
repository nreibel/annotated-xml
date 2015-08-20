package com.github.nreibel.xml.main;

import java.util.Collection;

import com.github.nreibel.xml.AnnotatedXmlNode;
import com.github.nreibel.xml.annotations.XmlCollection;

public class Node extends AnnotatedXmlNode {

	@XmlCollection(Class=Child.class, NodeName="child")
	private Collection<Child> children;

	public Collection<Child> getChildren() {
		return children;
	}
}
