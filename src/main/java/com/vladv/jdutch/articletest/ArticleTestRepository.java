package com.vladv.jdutch.articletest;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ArticleTestRepository extends MongoRepository<ArticleTest, Long> {

	void deleteArticleTestByTestname(String testname);
}
