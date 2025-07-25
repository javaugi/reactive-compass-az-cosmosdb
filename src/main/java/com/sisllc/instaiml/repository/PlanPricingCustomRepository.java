/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sisllc.instaiml.repository;

import com.sisllc.instaiml.dto.InsurancePricingDto;
import reactor.core.publisher.Flux;

public interface PlanPricingCustomRepository {
    Flux<InsurancePricingDto> premiumVsAgeAnalysis();
}

