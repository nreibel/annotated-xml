package com.github.nreibel.xml.main;

import java.io.File;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;


public class Main {

	public static void main(String[] args) throws Exception {
		File f = new File("sample.xml");

		Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(f);
		XmlRoot root = new XmlRoot(doc);
		root.doInitFields();

		System.out.println(root);
	}
}
