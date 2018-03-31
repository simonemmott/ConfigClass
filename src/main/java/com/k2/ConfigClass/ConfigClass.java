package com.k2.ConfigClass;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * The ConfigClass annotation identifies a class an being a configuration class.
 * 
 * Configuration classes read their instance data from a file. Either a resource file or a general OS file.
 * in both cases the file must be a JSON representation of the annotated class.
 * 
 * The class will read its resource automatically if it is an extension of the AutoConfigClass or if it is instantiated using the
 * one of the ConfigUtil.read(...) methods
 * @author simon
 *
 */
@Retention(RUNTIME)
@Target({TYPE})
public @interface ConfigClass {
	/**
	 * @return The resource name from which to read this classes instance data. Defaults to name simple name of the class with ".conf" on the end
	 */
	public String filename() default "";
	/**
	 * @return	The enumeration identifying whether the filename identifies a resource or a more general OS file. Defaults to RESOURCE
	 */
	public ConfigLocation location() default ConfigLocation.RESOURCE;
	/**
	 * @return	The character encode of the resource. Defaults to 'UTF-8'
	 */
	public String encoding() default "UTF-8";
	/**
	 * @return	The java format of Dates in the resource. Defaults to 'yyyy-MM-dd HH:mm:ss'
	 */
	public String dateFormat() default "yyyy-MM-dd HH:mm:ss";
}
