# ConfigClass - v0.1.0

The ConfigClass project provides a simple mechanism to store and retrieve configuration data as JSON files, either as a Java resource or as a more general OS file.

Configuration classes are identified using the `@ConfigClass` annotation.

Configuration classes that extend the AutoConfigClass abstract class are automatically populated when they are instantiated

## Basic Example

The class as defined below

```java
package my.example;

@ConfigClass	
public class MyConfig {
	
	public String alias;
	public Long number;
}
``` 

Can be instantiated from the resource named `/my/example/MyConfig.conf` in the class path with the following java code

```java
	MyConfig config = ConfigUtil.read(MyConfig.class);
```

Where `/my/example/MyConfig.conf` is

```json
{
	"alias": "myAlias",
	"number": 1234
}
```

Javadoc documentation of this project can be found [here](https://simonemmott.github.io/ConfigClass/index.html)

### License

[GNU GENERAL PUBLIC LICENSE v3](http://fsf.org/)

## Getting Started

Download a jar file containing the latest version or fork this project and install in your IDE

Maven users can add this project using the following additions to the pom.xml file.
```maven
<dependencies>
    ...
    <dependency>
        <groupId>com.k2</groupId>
        <artifactId>ConfigClass</artifactId>
        <version>0.1.0</version>
    </dependency>
    ...
</dependencies>
```

### Working With ConfigClass

#### Auto Config Example

Configuration classes the extend the abstract class AutoConfigClass are automatically populated from their resource when they are instantiated

The class below 

```java
@ConfigClass	
public class MyAutoConfig extends AutoConfigClass<MyAutoConfig> {
	
	public String alias;
	public Long number;
}
```

Automatically populates itself from its resource when it is instantiated.

#### Config Class Detailed Example

The location of the configuration data can be specified with the @ConfigClass annotation.

```java
@ConfigClass	(
	filename="/opt/example/config/my.json"	// <--- (1)
	location=ConfigLocation.OS_FILE		// <-- (2)
	encoding="UTF-8"			// <-- (3)
	dateFormat="dd-MM-yyyy"			// <-- (4)
)
public class MyDetailedConfig {
	
	public String alias;
	public Long number;
	public Date date;
}
```

1. The filename of the resource defaults to the simple name of the class with a `.conf` extension. Specifying the file name in the @ConfigClass annotation overrides this default
1. By default the data is assumed to be a Java resource file. i.e. a file on the Java class path. Alternatively the file name can refer to a file anywhere on the operating system specifying that location is ConfigLocation.OS_FILE
1. The character encoding of the source data file can be specified. This value default to `UTF-8`
1. The date format of dates in the JSON data file can be specified. The default date format is `yyyy-MM-dd HH:mm:ss`












































