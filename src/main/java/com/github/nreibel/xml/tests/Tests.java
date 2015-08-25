package com.github.nreibel.xml.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;

import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Element;

import com.github.nreibel.xml.tests.xml.XmlRoot;

public class Tests {

	private static XmlRoot root = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		File f = new File("sample.xml");
		Element el = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(f).getDocumentElement();
		root = new XmlRoot();
		root.doInitFields(el);
	}

	@Test
	public void read_attributes() {
		assertEquals("Test required attribute",    "1.1",          root.version);
		assertEquals("Test optional attribute",    "default name", root.name);
		assertEquals("Test custom attribute name", "custom",       root.customName);
	}

	@Test
	public void test_leaf() {
		assertEquals("Test leaf nodes",            "Some text...",                               root.leaf);
		assertEquals("Test leaf with custom name", "This node name is an illegal variable name", root.illegalNode);
	}

	@Test
	public void test_text() {
		assertEquals("Test text content", "some value", root.pair.value);
	}

	@Test
	public void test_child() {
		assertNotNull("Instantiate child node",                  root.pair);
		assertNotNull("Instantiate child node with custom name", root.nodeList);
	}

	@Test
	public void test_collections() {
		assertEquals("Test child collection size", 3, root.children.size());
		assertEquals("Test child collection size", 3, root.nodeList.children.size());
	}
}
