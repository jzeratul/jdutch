package com.vladv.jdutch.verbtest;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface VerbTestRepository extends MongoRepository<VerbTest, Long> {

	void deleteVerbTestByTestname(String testname);

  @Query("{ 'category' : ?0 }")
  List<VerbTest> findTestByCategory(String ofCategory);
}
