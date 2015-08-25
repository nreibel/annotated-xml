package com.github.nreibel.xml.tests.xml;

import com.github.nreibel.xml.AnnotatedXmlNode;
import com.github.nreibel.xml.annotations.XmlAttribute;
import com.github.nreibel.xml.annotations.XmlText;

public class Pair extends AnnotatedXmlNode {

	@XmlAttribute(Name="key", Required=true)
	public String key;
	
	@XmlText
	public String value;
}
