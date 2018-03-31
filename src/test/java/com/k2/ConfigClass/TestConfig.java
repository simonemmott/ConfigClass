package com.k2.ConfigClass;

import java.util.Date;
import java.util.List;

@ConfigClass(dateFormat="yyyy-MM-dd")
public class TestConfig {
	
	public TestConfig() {};
	
	public class SubConfig {
		public Long sequence;
		public String message;
	}

	public Date date;
	public String alias;
	public List<SubConfig> messages;
}
