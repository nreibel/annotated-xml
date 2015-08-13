package com.github.nreibel.xml.main;

import java.util.Collection;

import com.github.nreibel.xml.AnnotatedXmlLeaf;
import com.github.nreibel.xml.AnnotatedXmlNode;
import com.github.nreibel.xml.annotations.XmlAttribute;
import com.github.nreibel.xml.annotations.XmlChild;


public class XmlRoot extends AnnotatedXmlNode {

	@XmlAttribute(Required=true)
	private String version = "default";

	@XmlAttribute(Required=false)
	private String name = "defaultName";

	@XmlChild(Class=Child.class)
	private Child child = null;

	@XmlChild(Class=Items.class)
	private Items items = null;
	
	@XmlChild(Class=AnnotatedXmlLeaf.class)
	private AnnotatedXmlLeaf leaf = null;

	public String getVersion() {
		return version;
	}

	public String getName() {
		return name;
	}

	public Child getChild() {
		return child;
	}

	public Collection<Child> getItems() {
		return items.getChildren();
	}
	
	public String getLeaf() {
		return leaf.getText();
	}
}
