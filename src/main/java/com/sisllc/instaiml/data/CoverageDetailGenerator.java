/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sisllc.instaiml.data;

import com.sisllc.instaiml.model.CoverageDetail;
import java.math.BigDecimal;
import java.time.ZoneOffset;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class CoverageDetailGenerator extends DataGeneratorBase {

    public static CoverageDetail generate(String insurancePlanId) {
        return CoverageDetail.builder()
            .id(UUID.randomUUID().toString())
            .insurancePlanId(insurancePlanId)
            .deductibleIndividual(new BigDecimal(FAKER.number().randomDouble(2, 500, 1200)))
            .deductibleFamily(new BigDecimal(FAKER.number().randomDouble(2, 1500, 2500)))
            .oopMaxIndividual(new BigDecimal(FAKER.number().randomDouble(2, 2000, 2500)))
            .oopMaxFamily(new BigDecimal(FAKER.number().randomDouble(2, 4000, 6000)))
            .primaryCareCopay(new BigDecimal(FAKER.number().randomDouble(2, 10, 30)))
            .specialistCopay(new BigDecimal(FAKER.number().randomDouble(2, 80, 200)))
            .erCopay(new BigDecimal(FAKER.number().randomDouble(2, 200, 500)))
            .prescriptionTier1(new BigDecimal(FAKER.number().randomDouble(2, 10, 20)))
            .prescriptionTier2(new BigDecimal(FAKER.number().randomDouble(2, 20, 30)))
            .prescriptionTier3(new BigDecimal(FAKER.number().randomDouble(2, 30, 50)))
            .effectiveDate(FAKER.date().past(FAKER.number().numberBetween(30, 360), TimeUnit.DAYS).toInstant().atOffset(ZoneOffset.UTC))
            .expirationDate(FAKER.date().past(FAKER.number().numberBetween(30, 360), TimeUnit.DAYS).toInstant().atOffset(ZoneOffset.UTC))
            .build();
    }    
}
