package com.vladv.jdutch.wordtest;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface WordTestRepository extends MongoRepository<WordTest, Long> {

	void deleteWordTestByTestname(String testname);

  List<WordTest> findAllOrderByTestNameSortAsc();
}
