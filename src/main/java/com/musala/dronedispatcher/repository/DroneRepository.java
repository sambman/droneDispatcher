package com.musala.dronedispatcher.repository;

import com.musala.dronedispatcher.domain.Drone;
import com.musala.dronedispatcher.domain.enumeration.StateType;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Drone entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DroneRepository extends JpaRepository<Drone, Long> {
    List<Drone> findAll();

    List<Drone> findByState(StateType stateType);
}
