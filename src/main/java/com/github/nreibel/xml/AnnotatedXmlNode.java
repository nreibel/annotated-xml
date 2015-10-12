package com.github.nreibel.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.github.nreibel.xml.annotations.XmlAttribute;
import com.github.nreibel.xml.annotations.XmlChild;
import com.github.nreibel.xml.annotations.XmlCollection;
import com.github.nreibel.xml.annotations.XmlLeaf;
import com.github.nreibel.xml.annotations.XmlText;
import com.github.nreibel.xml.exceptions.AnnotatedXmlException;
import com.github.nreibel.xml.exceptions.AttributeNotFoundException;
import com.github.nreibel.xml.exceptions.NodeNotFoundException;
import com.github.nreibel.xml.utils.Utils;

public class AnnotatedXmlNode {

	public void tryLoad(File f) throws AnnotatedXmlException, FileNotFoundException {
		InputStream is = new FileInputStream(f);
		this.tryLoad(is);
	}

	public void tryLoad(InputStream stream) throws AnnotatedXmlException {
		try {
			Element root = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(stream).getDocumentElement();
			this.doInitFields(root);
		}
		catch(Exception ex) {
			throw new AnnotatedXmlException("Can't load XML data : " + ex.getMessage(), ex);
		}
	}

	public void doInitFields(Element element) throws AnnotatedXmlException {
		doInitText(element);
		doInitLeaves(element);
		doInitAttributes(element);
		doInitChildren(element);
		doInitCollections(element);
	}

	private final Object parseFromString(Class<?> clazz, String value) {
		if (clazz == Boolean.TYPE) return Boolean.parseBoolean(value);
		if (clazz == Integer.TYPE) return Integer.parseInt(value);
		if (clazz == Double.TYPE)  return Double.parseDouble(value);
		return value;
	}

	private final void doInitText(Element element) throws AnnotatedXmlException {
		Map<Field, XmlText> map = Utils.getFieldsWithAnnotation(this.getClass(), XmlText.class);
		for(Field field : map.keySet()) {
			try {
				Utils.setField(field, this, element.getTextContent());
			}
			catch(IllegalAccessException e) {
				throw new AnnotatedXmlException(e);
			}
		}
	}

	private final void doInitLeaves(Element element) throws AnnotatedXmlException {
		Map<Field, XmlLeaf> map = Utils.getFieldsWithAnnotation(this.getClass(), XmlLeaf.class);
		for(Entry<Field, XmlLeaf> entry : map.entrySet()) {

			Field   field      = entry.getKey();
			XmlLeaf annotation = entry.getValue();

			String nodeName = annotation.NodeName();
			if (nodeName.isEmpty()) nodeName = field.getName();

			try {
				Element el = Utils.getFirstElementOrDie(element, nodeName);
				Utils.setField(field, this, el.getTextContent());
			}
			catch (NodeNotFoundException e) {
				if (annotation.Required()) throw e;
			}
			catch(IllegalAccessException e) {
				throw new AnnotatedXmlException(e);
			}
		}
	}

	private final void doInitAttributes(Element element) throws AnnotatedXmlException {
		Map<Field, XmlAttribute> map = Utils.getFieldsWithAnnotation(this.getClass(), XmlAttribute.class);
		for(Entry<Field, XmlAttribute> entry : map.entrySet()) {

			Field        field      = entry.getKey();
			XmlAttribute annotation = entry.getValue();

			String attrName = annotation.Name();
			if (attrName.isEmpty()) attrName = field.getName();

			if (element.hasAttribute(attrName)) {
				try {
					String value = element.getAttribute(attrName);
					Object parsedValue = parseFromString(field.getType(), value);
					Utils.setField(field, this, parsedValue);
				}
				catch (Exception e) {
					throw new AnnotatedXmlException(e);
				}
			}
			else if (annotation.Required()) {
				throw new AttributeNotFoundException(element, attrName);
			}
		}
	}

	private final void doInitChildren(Element element) throws AnnotatedXmlException {
		Map<Field, XmlChild> map = Utils.getFieldsWithAnnotation(this.getClass(), XmlChild.class);
		for(Entry<Field, XmlChild> entry : map.entrySet()) {

			Field    field      = entry.getKey();
			XmlChild annotation = entry.getValue();

			String nodeName = annotation.NodeName();
			if (nodeName.isEmpty()) nodeName = field.getName();

			try {
				Element el = Utils.getFirstElementOrDie(element, nodeName);
				AnnotatedXmlNode item = annotation.Class().newInstance();
				item.doInitFields(el);
				Utils.setField(field, this, item);
			}
			catch (NodeNotFoundException e) {
				if (annotation.Required()) throw e;
			}
			catch(IllegalAccessException e) {
				throw new AnnotatedXmlException(e);
			}
			catch(InstantiationException e) {
				throw new AnnotatedXmlException(e);
			}
		}
	}

	private final void doInitCollections(Element element) throws AnnotatedXmlException {
		Map<Field, XmlCollection> map = Utils.getFieldsWithAnnotation(this.getClass(), XmlCollection.class);
		for(Entry<Field, XmlCollection> entry : map.entrySet()) {

			Field         field      = entry.getKey();
			XmlCollection annotation = entry.getValue();

			String nodeName = annotation.NodeName();
			if (nodeName.isEmpty()) nodeName = field.getName();

			List<AnnotatedXmlNode> list = new LinkedList<AnnotatedXmlNode>();

			Node node = element.getFirstChild();
			while(node != null) {
				if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals(nodeName)) {
					try {
						Element el = (Element) node;
						AnnotatedXmlNode item = annotation.Class().newInstance();
						item.doInitFields(el);
						list.add(item);
					}
					catch(InstantiationException e) {
						throw new AnnotatedXmlException(e);
					}
					catch(IllegalAccessException e) {
						throw new AnnotatedXmlException(e);
					}
				}

				node = node.getNextSibling();
			}

			try {
				List<AnnotatedXmlNode> finalList = Collections.unmodifiableList(new ArrayList<AnnotatedXmlNode>(list));
				Utils.setField(field, this, finalList);
			}
			catch (IllegalAccessException e) {
				throw new AnnotatedXmlException(e);
			}
			catch (IllegalArgumentException e) {
				throw new AnnotatedXmlException(e);
			}
		}
	}
}
