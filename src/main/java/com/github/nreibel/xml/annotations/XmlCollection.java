package com.github.nreibel.xml.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.github.nreibel.xml.AnnotatedXmlNode;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface XmlCollection {

	/**
	 * The class that describes the child nodes
	 * @return A class object
	 */
	Class<? extends AnnotatedXmlNode> Class();

	/**
	 * The tag name of the child nodes. If none is specified, the field name is used
	 * @return Tag name of the child nodes
	 */
	String NodeName() default "";
}
