package com.k2.ConfigClass;

import com.k2.Util.ObjectUtil;

/**
 * The AutoConfigClass is an abstract class that can be extended to allow a class annotated with @ConfigClass to automatically load its resource data upon instantiation
 * 
 * @author simon
 *
 * @param <T> The type of the class that has extended the AutoConfigClass
 */
public abstract class AutoConfigClass<T> {
	
	/**
	 * Create an instance of the AutoConfigClass and populate it from the resource defined by the @ConfigClass annotation
	 */
	@SuppressWarnings("unchecked")
	protected AutoConfigClass() {

		T config = ConfigUtil.read((Class<T>)this.getClass());
		if (config != null)
			ObjectUtil.copy(config, this);
		
	}

	/**
	 * Create an instance of the AutoConfigClass populating the class's data using the given resource
	 * @param resource	The JSON resource from which to read this instances data 
	 */
	@SuppressWarnings("unchecked")
	protected AutoConfigClass(String resource) {

		T config = ConfigUtil.read((Class<T>)this.getClass(), resource);
		if (config != null)
			ObjectUtil.copy(config, this);
		
	}

	/**
	 * Create an instance of the AutoConfigClass populating the class's data using the given resource or file
	 * @param location	Identifies whether the given resource name is a resource or an OS file
	 * @param resource	The JSON resource or file from which to read this instances data
	 */
	@SuppressWarnings("unchecked")
	protected AutoConfigClass(ConfigLocation location, String resource) {

		T config = ConfigUtil.read((Class<T>)this.getClass(), location, resource);
		if (config != null)
			ObjectUtil.copy(config, this);
		
	}

}
