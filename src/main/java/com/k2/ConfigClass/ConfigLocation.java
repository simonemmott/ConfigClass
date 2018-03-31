package com.k2.ConfigClass;

/**
 * This enumeration identifies whether a filename should be treated as a java resource (i.e. relative to the class path) or a more general OS file
 * @author simon
 *
 */
public enum ConfigLocation {
	/**
	 * The file name identifies a Java resource
	 */
	RESOURCE,
	/**
	 * The filename identifies a general file on the operating system
	 */
	OS_FILE
}
