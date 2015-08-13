package com.github.nreibel.xml.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.github.nreibel.xml.IAnnotatedXmlItem;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface XmlChild {

	boolean Required() default false;

	Class<? extends IAnnotatedXmlItem> Class();

}
