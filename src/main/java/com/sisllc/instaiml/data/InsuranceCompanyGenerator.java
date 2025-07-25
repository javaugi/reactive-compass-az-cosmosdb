/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sisllc.instaiml.data;

import com.sisllc.instaiml.model.InsuranceCompany;
import java.math.BigDecimal;
import java.util.UUID;

public class InsuranceCompanyGenerator extends DataGeneratorBase {

    public static InsuranceCompany generate() {
        return InsuranceCompany.builder()
            .id(UUID.randomUUID().toString())
            .companyCode("CC-" + JAVA_FAKER.number().digits(6))
            .companyName(JAVA_FAKER.company().name())
            .stateLicenses("LIC-" + JAVA_FAKER.number().digits(6))
            .financialRating("RATE-" + NET_FAKER.financialTerms().noun())
            .contactInfo(NET_FAKER.address().fullAddress())
            .marketShare(new BigDecimal(JAVA_FAKER.number().randomDouble(2, 0, 1)))
            .build();
    }    
}
