package com.github.nreibel.xml;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Map.Entry;

import org.w3c.dom.Element;

import com.github.nreibel.xml.annotations.XmlAttribute;
import com.github.nreibel.xml.annotations.XmlChild;
import com.github.nreibel.xml.exceptions.AnnotatedXmlException;
import com.github.nreibel.xml.exceptions.AttributeNotFoundException;
import com.github.nreibel.xml.exceptions.NodeNotFoundException;
import com.github.nreibel.xml.utils.Utils;

public class AnnotatedXmlNode implements IAnnotatedXmlItem {

	/* (non-Javadoc)
	 * @see com.github.nreibel.xml.IAnnotatedXmlItem#doInitFields(org.w3c.dom.Element)
	 */
	@Override
	public void doInitFields(Element element) throws AnnotatedXmlException {

		// Init child nodes
		Map<Field, XmlChild> mapChildren = Utils.getFieldsWithAnnotation(this.getClass(), XmlChild.class);
		for(Entry<Field, XmlChild> entry : mapChildren.entrySet()) {

			Field field = entry.getKey();
			XmlChild annotation = entry.getValue();

			try {
				Element el = Utils.getFirstElementOrDie(element, field.getName());
				IAnnotatedXmlItem item = annotation.Class().newInstance();
				item.doInitFields(el);
				Utils.setField(field, this, item);
			}
			catch (NodeNotFoundException e) { if (annotation.Required()) throw e; }
			catch(IllegalAccessException | InstantiationException e) { throw new AnnotatedXmlException(e); }
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
				}
				catch (Exception e) {
					throw new AnnotatedXmlException(e);
				}
			}
			else if (annotation.Required()) throw new AttributeNotFoundException(element, field.getName());
		}
	}
}
