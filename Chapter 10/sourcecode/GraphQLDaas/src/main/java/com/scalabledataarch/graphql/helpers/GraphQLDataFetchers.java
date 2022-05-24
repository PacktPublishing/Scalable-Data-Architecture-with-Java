package com.scalabledataarch.graphql.helpers;

import com.scalabledataarch.graphql.dao.repositories.ApplicationRepository;
import graphql.schema.DataFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GraphQLDataFetchers {

    @Autowired
    ApplicationRepository applicationRepository;

    public DataFetcher getApplicationbyApplicationIdDataFetcher() {
        return dataFetchingEnvironment -> {
            String applicationId = dataFetchingEnvironment.getArgument("applicationId");
            return applicationRepository.findApplicationsById(applicationId);
        };
    }

    public DataFetcher getApplicationsbyCustomerIdDataFetcher() {
        return  dataFetchingEnvironment -> {
            String customerId = dataFetchingEnvironment.getArgument("custId");
            return applicationRepository.findApplicationsByCustomerId(customerId);
        };
    }
}
