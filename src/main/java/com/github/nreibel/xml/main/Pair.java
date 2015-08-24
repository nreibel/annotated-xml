package com.github.nreibel.xml.main;

import com.github.nreibel.xml.AnnotatedXmlNode;
import com.github.nreibel.xml.annotations.XmlAttribute;
import com.github.nreibel.xml.annotations.XmlText;

public class Pair extends AnnotatedXmlNode {

	@XmlAttribute(Name="key", Required=true)
	private String key;
	
	@XmlText
	private String content;

	public String getKey() {
		return key;
	}

	public String getValue() {
		return content;
	}
}
