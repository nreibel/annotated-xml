package com.github.nreibel.xml;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

import org.w3c.dom.Element;

import com.github.nreibel.xml.annotations.XmlAttribute;
import com.github.nreibel.xml.annotations.XmlChild;
import com.github.nreibel.xml.exceptions.AnnotatedXmlException;
import com.github.nreibel.xml.exceptions.AnnotationParsingException;
import com.github.nreibel.xml.exceptions.AttributeNotFoundException;
import com.github.nreibel.xml.exceptions.NodeNotFoundException;
import com.github.nreibel.xml.impl.DefaultXmlAttribute;
import com.github.nreibel.xml.utils.Utils;

public abstract class AnnotatedXmlItem extends AbstractXmlItem {

	private final Element                   element;
	private final Collection<IXmlItem>      children   = new LinkedList<>();
	private final Collection<IXmlAttribute> attributes = new LinkedList<>();

	public AnnotatedXmlItem(Element el, IXmlItem parent) {
		super(parent);
		element = el;
	}

	protected final Element getElement() {
		return element;
	}

	@Override
	public String getNodeName() {
		return element.getNodeName();
	}

	@Override
	public Collection<? extends IXmlItem> getChildren() {
		return children;
	}

	@Override
	public Collection<? extends IXmlAttribute> getAttributes() {
		return attributes;
	}

	public void doInitFields() throws AnnotatedXmlException {

		// Init child nodes
		Map<Field, XmlChild> mapChildren = Utils.getFieldsWithAnnotation(this.getClass(), XmlChild.class);
		for(Entry<Field, XmlChild> entry : mapChildren.entrySet()) {

			Field field = entry.getKey();
			XmlChild annotation = entry.getValue();

			try {
				Element childNode = Utils.getFirstElementOrDie(element, field.getName());
				IXmlItemFactory<?> desc = annotation.Factory().newInstance();
				AnnotatedXmlItem it = desc.createItem(childNode, this);
				it.doInitFields();
				Utils.setField(field, this, it);
				children.add(it);
			}
			catch (NodeNotFoundException e) { if (annotation.Required()) throw e; }
			catch(IllegalAccessException | InstantiationException e) { throw new AnnotationParsingException(e); }
		}

		// Init attributes
		Map<Field, XmlAttribute> mapAttributes = Utils.getFieldsWithAnnotation(this.getClass(), XmlAttribute.class);
		for(Entry<Field, XmlAttribute> entry : mapAttributes.entrySet()) {

			Field field = entry.getKey();
			XmlAttribute annotation = entry.getValue();

			if ( element.hasAttribute(field.getName()) ) {
				try {
					Object value = element.getAttribute(field.getName());
					Utils.setField(field, this, value.toString());
					IXmlAttribute attr = new DefaultXmlAttribute(field.getName(), value.toString());
					attributes.add(attr);
				}
				catch (Exception e) {
					throw new AnnotationParsingException(e);
				}
			}
			else if (annotation.Required()) throw new AttributeNotFoundException(element, field.getName());
		}
	}
}
