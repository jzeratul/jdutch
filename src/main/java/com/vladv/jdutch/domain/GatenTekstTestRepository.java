package com.vladv.jdutch.domain;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface GatenTekstTestRepository extends MongoRepository<GatenTekstTest, Long> {

	void deleteGaatenTestByTestname(String testname);
}
