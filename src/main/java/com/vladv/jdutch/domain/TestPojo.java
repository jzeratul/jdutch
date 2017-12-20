package com.vladv.jdutch.domain;

import java.io.Serializable;

import io.jsondb.annotation.Document;
import io.jsondb.annotation.Id;

@Document(collection = "instances", schemaVersion = "1.0")
public class TestPojo implements Serializable {

	@Id
	private Long id;

	private String testname;
	private String testcontents;

	@Override
	public String toString() {
		return testname + " " + testcontents;
	}

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
}