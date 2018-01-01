package com.vladv.jdutch.gatentekst;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface GatenTekstTestRepository extends MongoRepository<GatenTekstTest, Long> {

	void deleteGaatenTestByTestname(String testname);
}
