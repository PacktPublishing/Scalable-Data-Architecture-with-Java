package com.scalabledataarch.graphql.dao.repositories;

import com.scalabledataarch.graphql.dao.Application;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ApplicationRepository extends MongoRepository<Application, String>, QuerydslPredicateExecutor<Application> {

    @Query(value = "{ 'applicationId' : ?0 }")
    Application findApplicationsById(String applicationId);

    @Query(value = "{ 'id' : ?0 }")
    List<Application> findApplicationsByCustomerId(String id);


}
