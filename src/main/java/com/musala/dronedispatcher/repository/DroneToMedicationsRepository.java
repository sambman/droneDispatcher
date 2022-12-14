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
    @Query(value = "select dtm.* from drone_to_medications dtm " + 
    "inner join drone_to_medications_drone dtmd on dtm.id = dtmd.drone_to_medications_id "+
    "inner join drone d on dtmd.drone_id = d.id " +
    "order by dtm.id desc limit 1", nativeQuery = true)
    DroneToMedications findLastByDroneId(Long droneId);

    @Query(value = "select distinct droneToMedications from DroneToMedications droneToMedications left join fetch droneToMedications.drones",
        countQuery = "select count(distinct droneToMedications) from DroneToMedications droneToMedications")
    Page<DroneToMedications> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct droneToMedications from DroneToMedications droneToMedications left join fetch droneToMedications.drones")
    List<DroneToMedications> findAllWithEagerRelationships();

    @Query("select droneToMedications from DroneToMedications droneToMedications left join fetch droneToMedications.drones where droneToMedications.id =:id")
    Optional<DroneToMedications> findOneWithEagerRelationships(@Param("id") Long id);
}
