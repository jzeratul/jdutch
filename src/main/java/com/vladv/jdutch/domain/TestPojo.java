package com.vladv.jdutch.domain;

import java.io.Serializable;

public class TestPojo implements Serializable {

	private String id;

	private String testname;
	private String testcontents;

//	@Override
//	public String toString() {
//		return "[" + testname + ":" + testcontents + "]";
//	}

	public String getTestname() {
		return testname;
	}

	public void setTestname(String testname) {
		this.testname = testname;
	}

	public String getTestcontents() {
		return testcontents;
	}

	public void setTestcontents(String testcontents) {
		this.testcontents = testcontents;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}