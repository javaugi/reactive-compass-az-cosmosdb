/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sisllc.instaiml.repository;

import com.azure.spring.data.cosmos.repository.ReactiveCosmosRepository;
import com.sisllc.instaiml.model.Pharmacy;
import org.springframework.stereotype.Repository;

@Repository
public interface PharmacyRepository extends ReactiveCosmosRepository<Pharmacy, String> {
    
}
