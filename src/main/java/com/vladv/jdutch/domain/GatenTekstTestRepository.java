package com.vladv.jdutch.domain;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface GaatenTestRepository extends MongoRepository<GaatenTest, Long> {

	void deleteGaatenTestByTestname(String testname);
}
