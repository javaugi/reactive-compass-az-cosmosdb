/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sisllc.instaiml.repository;

import com.azure.cosmos.models.SqlQuerySpec;
import com.azure.spring.data.cosmos.core.ReactiveCosmosTemplate;
import com.sisllc.instaiml.dto.InsurancePricingDto;
import com.sisllc.instaiml.model.PlanPricing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class PlanPricingCustomRepositoryImpl implements PlanPricingCustomRepository {
    @Value("${cosmos.queries.premiumVsAgeAnalysis}")
    private String premiumVsAgeQuery;
    @Autowired
    private ReactiveCosmosTemplate reactiveCosmosTemplate;

    @Override
    public Flux<InsurancePricingDto> premiumVsAgeAnalysis() {
        SqlQuerySpec querySpec = new SqlQuerySpec(premiumVsAgeQuery);
        return reactiveCosmosTemplate.runQuery(querySpec, PlanPricing.class, InsurancePricingDto.class);
    }
    
}
