/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sisllc.instaiml.service;

import com.sisllc.instaiml.model.Prescription;
import com.sisllc.instaiml.repository.PrescriptionRepository;
import java.time.OffsetDateTime;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class PrescriptionService {
    
    private final PrescriptionRepository prescriptionRepository;
    
    public Mono<Prescription> createPrescription(Prescription prescription) {
        prescription.setCreatedDate(OffsetDateTime.now());
        prescription.setUpdatedDate(OffsetDateTime.now());
        return prescriptionRepository.save(prescription);
    }

    /*
    Best Practice Version:
    */
    public Mono<Prescription> updateStatus(String id, String status) {
        return prescriptionRepository.findById(id)
            .filter(Objects::nonNull)  // Explicit null check (though findById shouldn't return null)
            .flatMap(prescription -> {
                prescription.setStatus(status);
                prescription.setUpdatedDate(OffsetDateTime.now());
                return prescriptionRepository.save(prescription);
            })
            .switchIfEmpty(Mono.error(new RuntimeException("Prescription not found")));
    }    
    
    
    /*
    map vs flatMap:
        map transforms values synchronously
        flatMap transforms values asynchronously (needed for save() which returns Mono<T>)

    Reactive Streams Contract:
        prescriptionRepository.save() returns a Mono<Prescription>
        map would result in Mono<Mono<Prescription>> (nested Mono)
        flatMap flattens to Mono<Prescription>
    */
    public Mono<Prescription> updateStatusSave(String id, String status) {
        return prescriptionRepository.findById(id)
            .filter(Objects::nonNull) // Explicit null check (though findById shouldn't return null)
            .map(prescription -> {
                prescription.setStatus(status);
                prescription.setUpdatedDate(OffsetDateTime.now());
                return prescription;
            })
            .flatMap(prescriptionRepository::save) // Still need flatMap for the save operation
            .switchIfEmpty(Mono.error(new RuntimeException("Prescription not found")));
    }   
    
    /*
    public Mono<Prescription> updateStatusByHandle(String id, String status) {
        return prescriptionRepository.findById(id)
            .handle((prescription, sink) -> {
                if (prescription != null) {
                    prescription.setStatus(status);
                    prescription.setUpdatedDate(OffsetDateTime.now());
                    sink.next(prescription);
                }
                // Implicit else: no emission (like mapNotNull)
            })
            .flatMap(prescriptionRepository::save) // Still need flatMap for the save operation
            .switchIfEmpty(Mono.error(new RuntimeException("Prescription not found")))
            ;
    }    
    // */
 
}
