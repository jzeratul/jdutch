package com.vladv.jdutch.domain;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface WordTestRepository extends MongoRepository<WordTest, Long> {

	void deleteWordTestByTestname(String testname);
}
