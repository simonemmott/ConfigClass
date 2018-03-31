package com.k2.ConfigClass;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.lang.invoke.MethodHandles;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * The ConfigUtil is a simple utility for populating configuration classes with JSON data from Java resources or an operating system file
 * 
 * @author simon
 *
 */
public class ConfigUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	
	/**
	 * A control variable to prevent recursive reading of the same file
	 */
	private static ThreadLocal<List<File>> readingFiles = new ThreadLocal<List<File>>();

	/**
	 * Create an instance of the given class reading its instance data from the resource or file identified by the @ConfigClass annotation
	 * 
	 * @param cls	The class to instantiate from the JSON source data file
	 * @return	An instance of the given class populated with the data defined in the resource or file
	 * 
	 * @param <C> The class to be read
	 */
	public static <C> C read(Class<C> cls) {
		
		return read(cls, null, null);
	}
	
	/**
	 * Create an instance of the given class reading its instance data from the given resource or file
	 * 
	 * @param cls	The class to instantiate from the JSON source data file
	 * @param file	The filename of the resource or OS file
	 * @return	An instance of the given class populated with the data defined in the resource or file
	 * 
	 * @param <C> The class to be read
	 */
	public static <C> C read(Class<C> cls, String file) {
		
		return read(cls, null, file);
	}
	
	/**
	 * Create an instance of the given class reading its instance data from the given resource or file
	 * 
	 * @param cls	The class to instantiate from the JSON source data file
	 * @param locationType	Overrides the value from the @ConfigClass annotation
	 * @return	An instance of the given class populated with the data defined in the resource or file
	 * 
	 * @param <C> The class to be read
	 */
	public static <C> C read(Class<C> cls, ConfigLocation locationType) {
		
		return read(cls, locationType, null);
	}
	
	/**
	 * Create an instance of the given class reading its instance data from the given resource or file
	 * 
	 * @param cls	The class to instantiate from the JSON source data file
	 * @param locationType	Overrides the value from the @ConfigClass annotation
	 * @param file	The filename of the resource or OS file
	 * @return	An instance of the given class populated with the data defined in the resource or file
	 * 
	 * @param <C> The class to be read
	 */
	public static <C> C read(Class<C> cls, ConfigLocation locationType, String file) {
		if (readingFiles.get() == null)
			readingFiles.set(new ArrayList<File>());
		
		ConfigClass configClass = cls.getAnnotation(ConfigClass.class);
		
		ConfigLocation location = (locationType==null) ? (configClass==null) ? ConfigLocation.RESOURCE : configClass.location() : locationType;
		String filename = (file==null) ? (configClass!=null && ! configClass.filename().equals("")) ? configClass.filename() : cls.getSimpleName()+".conf" : file ;
		String encoding = (configClass != null) ? configClass.encoding() : "UTF-8";
		String dateFormat = (configClass != null) ? configClass.dateFormat() : "yyyy-MM-dd HH:mm:ss";
		
		Gson gson = new GsonBuilder().setDateFormat(dateFormat).create();
		

		File configFile;
		
		switch(location) {
		case OS_FILE:
			configFile = new File(filename);
			break;
		case RESOURCE:
		default:
			URL url = cls.getResource(filename);
			if (url == null)
				return null;
			configFile = new File(url.getFile());
			break;
		
		}
				
		logger.trace("Reading config file: {}", configFile.getAbsolutePath());
		
		if (readingFiles.get().contains(configFile))
			return null;
		readingFiles.get().add(configFile);
		
		if (configFile.exists()) {
			try {
				FileInputStream fis = new FileInputStream(configFile);
				InputStreamReader isr = new InputStreamReader(fis, encoding);
				return gson.fromJson(isr, cls);
				
		
			} catch (FileNotFoundException e) {
				logger.error("THIS SHOULDN'T HAPPEN");
			} catch (IOException e) {
				logger.error("Unable to read configuration file {} - {}",e , configFile.getAbsolutePath(), e.getMessage());
			} finally {
				readingFiles.get().remove(configFile);
			}
		} else {
			logger.warn("The configuration file {} doesn't exist. Configuration not loaded!", configFile.getAbsolutePath());
		}
		
		return null;
	}

	/**
	 * Write the given object out to the given writer as a JSON object
	 * 
	 * @param out	The writer onto which to write the instance as a JSON object
	 * @param obj	The instance to write as a JSON object
	 * @return		The output writer after the JSON object was written for method chaining
	 */
	public static Writer write(Writer out, Object obj) {
		
		Class<?> cls = obj.getClass();

		ConfigClass configClass = cls.getAnnotation(ConfigClass.class);
		
		String dateFormat = (configClass != null) ? configClass.dateFormat() : "yyyy-MM-dd HH:mm:ss";
		
		Gson gson = new GsonBuilder().setDateFormat(dateFormat).setPrettyPrinting().create();
		
		gson.toJson(obj, out);
		
		return out;
		
	}


}
