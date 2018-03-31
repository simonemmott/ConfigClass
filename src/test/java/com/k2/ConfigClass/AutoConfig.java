package com.k2.ConfigClass;

import java.util.Date;
import java.util.List;

@ConfigClass(dateFormat="yyyy-MM-dd")
public class AutoConfig extends AutoConfigClass<AutoConfig>{
	
	public AutoConfig() {};
	
	public AutoConfig(String filename) {
		super(filename);
	}

	public class SubConfig {
		public Long sequence;
		public String message;
	}

	public Date date;
	public String alias;
	public List<SubConfig> messages;
}
