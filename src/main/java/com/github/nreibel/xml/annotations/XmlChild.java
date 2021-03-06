package com.github.nreibel.xml.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.github.nreibel.xml.AnnotatedXmlNode;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface XmlChild {

	/**
	 * Throw an exception if the attribute is not found
	 * @return True if the attribute is required, false otherwise
	 */
	boolean Required() default false;

	/**
	 * The tag name of the node. If none is specified, the field name is used
	 * @return Tag name of the node
	 */
	String NodeName() default "";

	/**
	 * The class that describes this child node
	 * @return A class object
	 */
	Class<? extends AnnotatedXmlNode> Class();
}
