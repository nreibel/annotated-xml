package com.github.nreibel.xml.main;

import com.github.nreibel.xml.AnnotatedXmlCollection;

public class Items extends AnnotatedXmlCollection<Child> {

	@Override
	protected Class<Child> getChildClass() {
		return Child.class;
	}

	@Override
	protected String getChildNodeName() {
		return "child";
	}
}
