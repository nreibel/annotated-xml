package com.github.nreibel.xml.tests.xml;

import java.util.Collection;

import com.github.nreibel.xml.AnnotatedXmlNode;
import com.github.nreibel.xml.annotations.XmlAttribute;
import com.github.nreibel.xml.annotations.XmlChild;
import com.github.nreibel.xml.annotations.XmlCollection;
import com.github.nreibel.xml.annotations.XmlLeaf;


public class XmlRoot extends AnnotatedXmlNode {

	@XmlAttribute(Required=true)
	public String version = "default";

	@XmlAttribute
	public String name = "default name";
	
	@XmlAttribute(Name="with-custom-name")
	public String customName;

	@XmlLeaf
	public String leaf = "default";

	@XmlLeaf(NodeName="ILLEGAL-JAVA-NAME")
	public String illegalNode = "default";

	@XmlCollection(Class=Child.class, NodeName="child")
	public Collection<Child> children;
	
	@XmlChild(Class=NodeList.class, NodeName="node-list")
	public NodeList nodeList;
	
	@XmlChild(Class=Pair.class)
	public Pair pair;
}
