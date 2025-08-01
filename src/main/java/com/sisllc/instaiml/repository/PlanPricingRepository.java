/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sisllc.instaiml.repository;

import com.azure.spring.data.cosmos.repository.ReactiveCosmosRepository;
import com.sisllc.instaiml.dto.InsurancePricingDto;
import com.sisllc.instaiml.model.PlanPricing;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface PlanPricingRepository extends ReactiveCosmosRepository<PlanPricing, String>, PlanPricingCustomRepository  {
    //@Query("#{@CosmosQueryConfig.premiumVsAgeAnalysis}")
    //@Query("${cosmos.queries.premiumVsAgeAnalysis}")
    Flux<InsurancePricingDto> premiumVsAgeAnalysis();    
}
