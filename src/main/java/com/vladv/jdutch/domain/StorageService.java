package com.vladv.jdutch.domain;

import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import io.jsondb.JsonDBTemplate;

@Component("storageService")
public class StorageService {

	private static final Logger LOGGER = LoggerFactory.getLogger(StorageService.class);

	private JsonDBTemplate jsonDBTemplate;

	public List<TestPojo> findBySkill(String testname) {

		String jxQuery = String.format("/.[testname='%s']", testname);
		List<TestPojo> result = jsonDBTemplate.find(jxQuery, TestPojo.class);

		return result;
	}

	public void save(TestPojo obj) {

		jsonDBTemplate.insert(obj);
	}

	@PostConstruct
	private void iniDataForTesting() {

		String dbFilesLocation = "D:/jsondb/";

		LOGGER.info("Using jsondb on " + dbFilesLocation);

		jsonDBTemplate = new JsonDBTemplate(dbFilesLocation, TestPojo.class.getPackage().getName());

		LOGGER.info("Initialized jsonDBTemplate");

		Set<String> collectionNames = jsonDBTemplate.getCollectionNames();

		if(collectionNames.isEmpty()) {
			jsonDBTemplate.createCollection("instances");
		}
	}

	public List<TestPojo> findAll() {

		List<TestPojo> result = jsonDBTemplate.findAll("instances");

		return result;
	}
}
