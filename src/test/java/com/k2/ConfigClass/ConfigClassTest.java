package com.k2.ConfigClass;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.StringWriter;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.k2.Util.DateUtil;


public class ConfigClassTest {
	
	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	@Test
	public void readAutoConfigTest() {
		
		AutoConfig config = new AutoConfig();
		
		assertEquals(DateUtil.toDate("2018-01-01", "yyyy-MM-dd"), config.date);
		assertEquals("thisIsTheAlias", config.alias);
		
		assertEquals(4, config.messages.size());
		
	}
	
	@Test
	public void overrifdeFileAutoConfigTest() {
		
		AutoConfig config = new AutoConfig("Alternative.conf");
		
		assertEquals(DateUtil.toDate("2017-01-01", "yyyy-MM-dd"), config.date);
		assertEquals("alternative", config.alias);
		
		assertEquals(3, config.messages.size());
		
	}
	
	@Test
	public void readAlternativeAutoConfigTest() {
		
		AlternativeAutoConfig config = new AlternativeAutoConfig();
		
		assertEquals(DateUtil.toDate("2017-01-01", "yyyy-MM-dd"), config.date);
		assertEquals("alternative", config.alias);
		
		assertEquals(3, config.messages.size());
		
	}

	@Test
	public void readTestConfig() {
		
		AutoConfig config = ConfigUtil.read(AutoConfig.class);
		
		assertEquals(DateUtil.toDate("2018-01-01", "yyyy-MM-dd"), config.date);
		assertEquals("thisIsTheAlias", config.alias);
		
		assertEquals(4, config.messages.size());
		
	}
	
	@Test
	public void readTest2Config() {
		
		TestConfig config = ConfigUtil.read(TestConfig.class);
		
		assertEquals(DateUtil.toDate("2018-01-01", "yyyy-MM-dd"), config.date);
		assertEquals("testConfig", config.alias);
		
		assertEquals(4, config.messages.size());
		
	}
	
	@Test
	public void readAlternativeTestConfig() {
		
		TestConfig config = ConfigUtil.read(TestConfig.class, "Alternative.conf");
		
		assertEquals(DateUtil.toDate("2017-01-01", "yyyy-MM-dd"), config.date);
		assertEquals("alternative", config.alias);
		
		assertEquals(3, config.messages.size());
		
	}
	
	@Test
	public void readAbsoluteAlternativeTestConfig() {
		
		TestConfig config = ConfigUtil.read(TestConfig.class, ConfigLocation.OS_FILE, "/Users/simon/Personal/K2_Workshop/ConfigClass/target/test-classes/com/k2/ConfigClass/Alternative.conf");
		
		assertEquals(DateUtil.toDate("2017-01-01", "yyyy-MM-dd"), config.date);
		assertEquals("alternative", config.alias);
		
		assertEquals(3, config.messages.size());
		
	}
	
	

	@Test
	public void writeConfigTest() throws IOException {
		
		TestConfig config = new TestConfig();

		config.date = DateUtil.toDate("2018-01-01", "yyyy-MM-dd");
		config.alias = "thisIsTheAlias";
		config.messages = new ArrayList<TestConfig.SubConfig>();
		TestConfig.SubConfig sub0 = config.new SubConfig();
		sub0.sequence = 0L;
		sub0.message = "Message 0";
		config.messages.add(sub0);

		TestConfig.SubConfig sub1 = config.new SubConfig();
		sub1.sequence = 1L;
		sub1.message = "Message 1";
		config.messages.add(sub1);

		TestConfig.SubConfig sub2 = config.new SubConfig();
		sub2.sequence = 2L;
		sub2.message = "Message 2";
		config.messages.add(sub2);

		TestConfig.SubConfig sub3 = config.new SubConfig();
		sub3.sequence = 3L;
		sub3.message = "Message 3";
		config.messages.add(sub3);
		
		String exprected = "{\n" + 
				"  \"date\": \"2018-01-01\",\n" + 
				"  \"alias\": \"thisIsTheAlias\",\n" + 
				"  \"messages\": [\n" + 
				"    {\n" + 
				"      \"sequence\": 0,\n" + 
				"      \"message\": \"Message 0\"\n" + 
				"    },\n" + 
				"    {\n" + 
				"      \"sequence\": 1,\n" + 
				"      \"message\": \"Message 1\"\n" + 
				"    },\n" + 
				"    {\n" + 
				"      \"sequence\": 2,\n" + 
				"      \"message\": \"Message 2\"\n" + 
				"    },\n" + 
				"    {\n" + 
				"      \"sequence\": 3,\n" + 
				"      \"message\": \"Message 3\"\n" + 
				"    }\n" + 
				"  ]\n" + 
				"}";
		
		StringWriter sw = new StringWriter();

		ConfigUtil.write(sw, config).flush();
		
		assertEquals(exprected, sw.toString());
	}


}
