package com.vladv.jdutch.domain;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface TestRepository extends MongoRepository<TestPojo, Long> {

}
