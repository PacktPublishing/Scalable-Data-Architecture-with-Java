package com.scalabledataarch.rest.controller;

import com.scalabledataarch.rest.model.Application;
import com.scalabledataarch.rest.repository.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/rdaas")
public class DaasController {

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    ApplicationRepository applicationRepository;

    @GetMapping(path= "/application/{applicationId}",  produces = "application/json")
    public ResponseEntity<Application> getApplicationById(@PathVariable String applicationId){
      Application application = applicationRepository.findById(applicationId).orElseGet(null);
      return ResponseEntity.ok(application);
    }

    @GetMapping(path= "/customer/{id}/application",  produces = "application/json")
    public ResponseEntity<List<Application>> getApplicationByCustomerId(@PathVariable String id){
        return ResponseEntity.ok(applicationRepository.findApplicationsByCustomerId(id));
    }


    /*@PutMapping(value = "status/{applicationId}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable( "applicationId" ) String applicationId, @RequestBody String resource) {
        *//*Preconditions.checkNotNull(resource);
        RestPreconditions.checkNotNull(service.getById(resource.getId()));
        service.update(resource);*//*
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is("Markus"));
        Update update = new Update();
        update.set("name", "Nick");
        mongoTemplate.upsert(query, update, Application.class);
    }*/

    //application
    //approvalstatus update
    //get applicationIds by customerid
}
