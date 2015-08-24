package com.github.nreibel.xml.main;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import com.github.nreibel.xml.exceptions.AnnotatedXmlException;


public class Main {

	public static void main(String[] args) throws SAXException, IOException, ParserConfigurationException {
		File f = new File("sample.xml");

		Document doc  = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(f);
		Element  root = doc.getDocumentElement();
		XmlRoot  xml  = new XmlRoot();

		try {
			xml.doInitFields(root);
		}
		catch (AnnotatedXmlException e) {
			e.printStackTrace();
		}

		System.out.println(xml.getVersion());
		System.out.println(xml.getName());
		System.out.println(xml.getLeaf());
		System.out.println(xml.getIllegalNode());
		System.out.println(xml.getChildren());
		System.out.println(xml.getNode().getChildren());
		
		System.out.println(xml.getPair().getKey() + " => " + xml.getPair().getValue());
	}
}
