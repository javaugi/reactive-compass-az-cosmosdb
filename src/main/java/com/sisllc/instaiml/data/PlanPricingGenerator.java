/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sisllc.instaiml.data;

import com.sisllc.instaiml.model.PlanPricing;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.ZoneOffset;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class PlanPricingGenerator extends DataGeneratorBase {

    public static PlanPricing generate(String insurancePlanId) {
        return PlanPricing.builder()
            .id(UUID.randomUUID().toString())
            .insurancePlanId(insurancePlanId)
            .pricingType(NET_FAKER.options().option("premium", "deductible", "copay", "coinsurance", "adjustment"))
            .basePremium(new BigDecimal(NET_FAKER.number().numberBetween(1000, 9000)).setScale(2, RoundingMode.UP))
            .tobaccoSurcharge(new BigDecimal(NET_FAKER.number().numberBetween(10, 30)).setScale(2, RoundingMode.UP))
            .familyCoverageAdjustment(new BigDecimal(NET_FAKER.number().numberBetween(10, 30)).setScale(2, RoundingMode.UP).negate())
            .miscAdjustment(new BigDecimal(NET_FAKER.number().numberBetween(0, 20)).setScale(2, RoundingMode.UP).negate())
            .ageBracket(generateAgeGroupBracket())
            .coverageLevel(NET_FAKER.options().option("Bronze", "Silver", "Gold", "Platinum", "Catastrophic"))
            .effectiveDate(JAVA_FAKER.date().past(JAVA_FAKER.number().numberBetween(30, 90), TimeUnit.DAYS).toInstant().atOffset(ZoneOffset.UTC))
            .expirationDate(JAVA_FAKER.date().future(JAVA_FAKER.number().numberBetween(100, 300), TimeUnit.DAYS).toInstant().atOffset(ZoneOffset.UTC))
            .build();
    }    
}
