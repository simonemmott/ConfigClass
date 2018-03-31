package com.k2.ConfigClass;

import java.util.Date;
import java.util.List;

@ConfigClass(
		filename="Alternative.conf",
		dateFormat="yyyy-MM-dd"
		)
public class AlternativeFileConfig {
	
	public AlternativeFileConfig() {};
	
	public class SubConfig {
		public Long sequence;
		public String message;
	}

	public Date date;
	public String alias;
	public List<SubConfig> messages;
}
