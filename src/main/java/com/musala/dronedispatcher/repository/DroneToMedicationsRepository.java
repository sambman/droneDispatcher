package com.musala.dronedispatcher.repository;

import com.musala.dronedispatcher.domain.DroneToMedications;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the DroneToMedications entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DroneToMedicationsRepository extends JpaRepository<DroneToMedications, Long> {
}
