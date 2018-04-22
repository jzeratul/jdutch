package com.vladv.jdutch.gatentekst;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface GatenTekstTestRepository extends MongoRepository<GatenTekstTest, Long> {

	void deleteGaatenTestByTestname(String testname);
  
  @Query("{ 'category' : ?0 }")
  List<GatenTekstTest> findTestByCategory(String ofCategory);
}
