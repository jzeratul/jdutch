package com.vladv.jdutch.domain;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ArticleTestRepository extends MongoRepository<ArticleTest, Long> {

	void deleteWordTestByTestname(String testname);
}
