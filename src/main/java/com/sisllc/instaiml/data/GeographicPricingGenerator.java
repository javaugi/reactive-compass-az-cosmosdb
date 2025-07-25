/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sisllc.instaiml.data;

import com.sisllc.instaiml.model.GeographicPricing;
import java.time.ZoneOffset;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GeographicPricingGenerator extends DataGeneratorBase {

    public static GeographicPricing generate(String insurancePlanId) {
        String state = getStateAbbr();
        String zipCode = getZipCodeByStateAbbr(state);
        
        return GeographicPricing.builder()
            .id(UUID.randomUUID().toString())
            .insurancePlanId(insurancePlanId)
            .zipCode(zipCode).stateAbbr(state)
            .ratingArea(JAVA_FAKER.number().numberBetween(1, 9))
            .effectiveDate(JAVA_FAKER.date().past(JAVA_FAKER.number().numberBetween(30, 90), TimeUnit.DAYS).toInstant().atOffset(ZoneOffset.UTC))
            .expirationDate(JAVA_FAKER.date().future(JAVA_FAKER.number().numberBetween(100, 300), TimeUnit.DAYS).toInstant().atOffset(ZoneOffset.UTC))
            .build();
    }    
}
