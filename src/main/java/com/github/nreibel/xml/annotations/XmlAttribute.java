package com.github.nreibel.xml.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation captures an XML attribute into a String
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface XmlAttribute {

	/**
	 * Throw an exception if the attribute is not found
	 * @return True if the attribute is required, false otherwise
	 */
	boolean Required() default false;

	/**
	 * The name of the attribute. If none is specified, the field name is used
	 * @return Name of the attribute
	 */
	String Name() default "";
}
