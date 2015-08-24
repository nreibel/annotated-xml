package com.github.nreibel.xml.main;

import java.util.Collection;

import com.github.nreibel.xml.AnnotatedXmlNode;
import com.github.nreibel.xml.annotations.XmlAttribute;
import com.github.nreibel.xml.annotations.XmlChild;
import com.github.nreibel.xml.annotations.XmlCollection;
import com.github.nreibel.xml.annotations.XmlLeaf;


public class XmlRoot extends AnnotatedXmlNode {

	@XmlAttribute(Required=true)
	private String version = "default";

	@XmlAttribute
	private String name = "defaultName";

	@XmlLeaf
	private String leaf = "default";

	@XmlLeaf(NodeName="ILLEGAL-JAVA-NAME")
	private String illegalNode = "default";

	@XmlCollection(Class=Child.class, NodeName="child")
	private Collection<Child> children;
	
	@XmlChild(Class=Node.class)
	private Node node;
	
	@XmlChild(Class=Pair.class)
	private Pair pair;
	
	public String getVersion() {
		return version;
	}

	public String getName() {
		return name;
	}

	public Collection<Child> getChildren() {
		return children;
	}

	public String getLeaf() {
		return leaf;
	}

	public Node getNode() {
		return node;
	}

	public String getIllegalNode() {
		return illegalNode;
	}
	
	public Pair getPair() {
		return pair;
	}
}
