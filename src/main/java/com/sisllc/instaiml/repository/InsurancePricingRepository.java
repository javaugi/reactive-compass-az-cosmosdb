/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sisllc.instaiml.repository;

import com.azure.spring.data.cosmos.repository.Query;
import com.azure.spring.data.cosmos.repository.ReactiveCosmosRepository;
import com.sisllc.instaiml.model.InsurancePlan;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import com.sisllc.instaiml.dto.InsurancePricingDto;

@Repository
public interface InsurancePricingRepository extends ReactiveCosmosRepository<InsurancePlan, String> {
    
    // Or using a Projection Interface (Requires Spring Data Projections)
    // You would define an interface like:
    // public interface ClientNameEmail { String getName(); String getEmail(); }
    //@Query("SELECT p.planType, p.tierLevel FROM insurancePlans p")
    @Query("${cosmos.queries.premiumByPlanTypeTierAnalysis}")
    Flux<InsurancePricingDto> premiumByPlanTypeTierAnalysis();

    @Query("${cosmos.queries.costVsCoverageAnalysis}")
    Flux<InsurancePricingDto> costVsCoverageAnalysis();

    @Query("${cosmos.queries.tobaccoSurchargeImpactAnalysis}")
    Flux<InsurancePricingDto> tobaccoSurchargeImpactAnalysis();

    @Query("${cosmos.queries.marketBenchmarkingAnalysis}")
    Flux<InsurancePricingDto> marketBenchmarkingAnalysis();

    @Query("${cosmos.queries.riskPoolAnalysis}")
    Flux<InsurancePricingDto> riskPoolAnalysis();

    @Query("${cosmos.queries.networkAdequacyImpactAnalysis}")
    Flux<InsurancePricingDto> networkAdequacyImpactAnalysis();
}
