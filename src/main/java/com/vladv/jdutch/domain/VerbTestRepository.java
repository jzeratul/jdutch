package com.vladv.jdutch.domain;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface VerbTestRepository extends MongoRepository<VerbTest, Long> {

	void deleteVerbTestByTestname(String testname);
}
