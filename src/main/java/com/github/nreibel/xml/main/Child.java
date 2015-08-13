package com.github.nreibel.xml.main;

import com.github.nreibel.xml.AnnotatedXmlNode;
import com.github.nreibel.xml.annotations.XmlAttribute;


public class Child extends AnnotatedXmlNode {

	@XmlAttribute
	private String name = "defaultName";

	public String getName() {
		return name;
	}
}
