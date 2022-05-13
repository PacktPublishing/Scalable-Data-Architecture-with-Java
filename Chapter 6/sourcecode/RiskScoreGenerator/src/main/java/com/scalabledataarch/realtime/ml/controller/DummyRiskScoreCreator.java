package com.scalabledataarch.realtime.ml.controller;

import com.scalabledataarch.realtime.ml.model.MLRequest;
import com.scalabledataarch.realtime.ml.model.RiskScoreResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/riskgenerate")
public class DummyRiskScoreCreator {

    @PostMapping(path= "/score", consumes = "application/json", produces = "application/json")
    public ResponseEntity<RiskScoreResponse> calculateRiskScore(@RequestBody MLRequest mlRequest){
        RiskScoreResponse riskScoreResponse = new RiskScoreResponse();
        riskScoreResponse.setScore((Math.random() * (99)) + 1);
        return  ResponseEntity.ok(riskScoreResponse);
    }
}
