package com.vladv.jdutch.wordtest;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface WordTestRepository extends MongoRepository<WordTest, Long> {

	void deleteWordTestByTestname(String testname);
  
  @Query("{ 'category' : ?0 }")
  List<WordTest> findTestByCategory(String ofCategory);
}
