package com.github.nreibel.xml.tests.xml;

import com.github.nreibel.xml.AnnotatedXmlNode;
import com.github.nreibel.xml.annotations.XmlAttribute;


public class Child extends AnnotatedXmlNode {

	@XmlAttribute
	public String name = "defaultName";
}
