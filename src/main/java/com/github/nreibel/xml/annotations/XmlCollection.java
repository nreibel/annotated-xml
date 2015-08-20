package com.github.nreibel.xml.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.github.nreibel.xml.AnnotatedXmlNode;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface XmlCollection {

	Class<? extends AnnotatedXmlNode> Class();

	String NodeName() default "";
}
