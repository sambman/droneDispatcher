package com.musala.dronedispatcher.repository;

import com.musala.dronedispatcher.domain.DroneToMedications;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the DroneToMedications entity.
 */
@Repository
public interface DroneToMedicationsRepository extends JpaRepository<DroneToMedications, Long> {

    @Query(value = "select distinct droneToMedications from DroneToMedications droneToMedications left join fetch droneToMedications.drones",
        countQuery = "select count(distinct droneToMedications) from DroneToMedications droneToMedications")
    Page<DroneToMedications> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct droneToMedications from DroneToMedications droneToMedications left join fetch droneToMedications.drones")
    List<DroneToMedications> findAllWithEagerRelationships();

    @Query("select droneToMedications from DroneToMedications droneToMedications left join fetch droneToMedications.drones where droneToMedications.id =:id")
    Optional<DroneToMedications> findOneWithEagerRelationships(@Param("id") Long id);
}
