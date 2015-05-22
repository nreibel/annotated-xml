package com.github.nreibel.xml;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.w3c.dom.Element;

import com.github.nreibel.xml.annotations.XmlAttribute;
import com.github.nreibel.xml.annotations.XmlLeaf;
import com.github.nreibel.xml.exceptions.AnnotationParsingException;
import com.github.nreibel.xml.exceptions.AttributeNotFoundException;
import com.github.nreibel.xml.exceptions.NodeNotFoundException;
import com.github.nreibel.xml.utils.Utils;

public abstract class AnnotatedXmlItem extends AbstractXmlItem {

	public AnnotatedXmlItem(IXmlItem parent) {
		super(parent);
	}

	public final void doInitFields(Element el) throws AnnotationParsingException, NodeNotFoundException, AttributeNotFoundException {

		// Read leaf nodes
		Map<Field, XmlLeaf> leafs = Utils.getFieldsWithAnnotation(this.getClass(), XmlLeaf.class);
		for(Entry<Field, XmlLeaf> entry : leafs.entrySet()) {
			Field field = entry.getKey();
			XmlLeaf annotation = entry.getValue();

			try {
				String value = getElementValue(el, field.getName());
				Utils.forceSetField(field, this, value);
			}
			catch (IllegalArgumentException | IllegalAccessException e) {
				throw new AnnotationParsingException(e);
			}
			catch(NodeNotFoundException ex) {
				if (annotation.Required()) throw ex;
			}
		}

		// Read node attributes
		Map<Field, XmlAttribute> attributes = Utils.getFieldsWithAnnotation(this.getClass(), XmlAttribute.class);
		for(Entry<Field, XmlAttribute> entry : attributes.entrySet()) {
			Field field = entry.getKey();
			XmlAttribute annotation = entry.getValue();

			try {
				String value = el.getAttribute(field.getName());
				if (!value.isEmpty()) Utils.forceSetField(field, this, value);
				else if (annotation.Required()) throw new AttributeNotFoundException(el, field.getName());
			}
			catch (IllegalArgumentException | IllegalAccessException e) {
				throw new AnnotationParsingException(e);
			}
		}
	}

	@Override
	public final Collection<? extends IXmlItem> getChildren() {
		List<IXmlItem> list = new LinkedList<>();

		Map<Field, XmlLeaf> leafs = Utils.getFieldsWithAnnotation(this.getClass(), XmlLeaf.class);
		for(Field field : leafs.keySet()) {

			XmlItemWithValue item = new XmlItemWithValue(this, field.getName());

			String value = null;

			try {
				boolean accessible = field.isAccessible();
				field.setAccessible(true);
				value = field.get(this).toString();
				field.setAccessible(accessible);
			}
			catch (Exception e) {
				e.printStackTrace();
			}

			item.setValue(value);
			list.add(item);
		}

		return list;
	}

	@Override
	public final Collection<? extends IXmlAttribute> getAttributes() {
		List<IXmlAttribute> list = new LinkedList<>();

		Map<Field, XmlAttribute> leafs = Utils.getFieldsWithAnnotation(this.getClass(), XmlAttribute.class);
		for(Field field : leafs.keySet()) {

			String value = null;

			try {
				boolean accessible = field.isAccessible();
				field.setAccessible(true);
				value = field.get(this).toString();
				field.setAccessible(accessible);
			}
			catch (Exception e) {
				e.printStackTrace();
			}

			DefaultXmlAttribute item = new DefaultXmlAttribute(field.getName(), value);
			list.add(item);
		}

		return list;
	}

}
