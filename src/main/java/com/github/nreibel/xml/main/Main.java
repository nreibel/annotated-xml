package com.github.nreibel.xml.main;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.github.nreibel.xml.exceptions.AnnotatedXmlException;


public class Main {

	public static void main(String[] args) throws SAXException, IOException, ParserConfigurationException {
		File f = new File("sample.xml");

		Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(f);
		XmlRoot root = new XmlRoot(doc);

		try {
			root.doInitFields();
			System.out.println(root);
		}
		catch (AnnotatedXmlException e) {
			e.printStackTrace();
		}
	}
}
