package com.github.nreibel.xml;

public interface Descriptor<T extends AnnotatedXmlItem> {
	T createItem(IXmlItem parent);
}
