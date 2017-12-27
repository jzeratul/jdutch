package com.vladv.jdutch.domain;

import java.io.Serializable;

public class Test implements Serializable {

	private String id;

	private String testname;
	private String testcontents;

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