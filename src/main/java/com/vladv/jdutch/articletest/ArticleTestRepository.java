package com.vladv.jdutch.articletest;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface ArticleTestRepository extends MongoRepository<ArticleTest, Long> {

	void deleteArticleTestByTestname(String testname);
	
  @Query("{ 'category' : ?0 }")
  List<ArticleTest> findTestByCategory(String category);
}
