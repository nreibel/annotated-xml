package com.github.nreibel.xml;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.w3c.dom.Element;

import com.github.nreibel.xml.annotations.XmlAttribute;
import com.github.nreibel.xml.annotations.XmlChild;
import com.github.nreibel.xml.exceptions.AnnotationParsingException;
import com.github.nreibel.xml.exceptions.AttributeNotFoundException;
import com.github.nreibel.xml.exceptions.NodeNotFoundException;
import com.github.nreibel.xml.impl.DefaultXmlAttribute;
import com.github.nreibel.xml.utils.Utils;

public abstract class AnnotatedXmlItem extends AbstractXmlItem {

	private final Element element;
	
	public AnnotatedXmlItem(Element el, IXmlItem parent) {
		super(parent);
		element = el;
	}
	
	public final Element getElement() {
		return element;
	}

	@Override
	public String getNodeName() {
		return element.getNodeName();
	}

	public void doInitFields() throws AttributeNotFoundException, AnnotationParsingException, NodeNotFoundException {
		
		// Init child nodes
		Map<Field, XmlChild> children = Utils.getFieldsWithAnnotation(this.getClass(), XmlChild.class);
		for(Entry<Field, XmlChild> entry : children.entrySet()) {

			Field field = entry.getKey();
			XmlChild annotation = entry.getValue();

			try {
				Element childNode = Utils.getFirstElementOrDie(element, field.getName());
				IXmlItemFactory<?> desc = annotation.Factory().newInstance();
				AnnotatedXmlItem it = desc.createItem(childNode, this);
				it.doInitFields();
				Utils.setField(field, this, it);
			}
			catch (NodeNotFoundException e) {
				if (annotation.Required()) throw e;
			}
			catch(Exception e) {
				throw new AnnotationParsingException(e);
			}
		}

		// Init attributes
		Map<Field, XmlAttribute> attributes = Utils.getFieldsWithAnnotation(this.getClass(), XmlAttribute.class);
		for(Entry<Field, XmlAttribute> entry : attributes.entrySet()) {

			Field field = entry.getKey();
			XmlAttribute annotation = entry.getValue();

			try {
				String value = element.getAttribute(field.getName());
				if (!value.isEmpty()) Utils.setField(field, this, value);
				else if (annotation.Required()) throw new AttributeNotFoundException(element, field.getName());
			}
			catch (Exception e) {
				throw new AnnotationParsingException(e);
			}
		}
	}

	@Override
	public Collection<? extends IXmlItem> getChildren() {
		List<IXmlItem> list = new LinkedList<>();

		Map<Field, XmlChild> leafs = Utils.getFieldsWithAnnotation(this.getClass(), XmlChild.class);
		for(Field field : leafs.keySet()) {

			try {
				IXmlItem item = (IXmlItem) Utils.getField(field, this);
				if (item != null) list.add(item);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return list;
	}

	@Override
	public Collection<? extends IXmlAttribute> getAttributes() {
		List<IXmlAttribute> list = new LinkedList<>();

		Map<Field, XmlAttribute> leafs = Utils.getFieldsWithAnnotation(this.getClass(), XmlAttribute.class);
		for(Field field : leafs.keySet()) {

			try {
				String value = Utils.getField(field, this).toString();
				DefaultXmlAttribute item = new DefaultXmlAttribute(field.getName(), value);
				list.add(item);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}

		return list;
	}
}
