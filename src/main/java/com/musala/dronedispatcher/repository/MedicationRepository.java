package com.musala.dronedispatcher.repository;

import com.musala.dronedispatcher.domain.Medication;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Medication entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MedicationRepository extends JpaRepository<Medication, Long> {
}
