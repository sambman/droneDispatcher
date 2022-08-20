package com.musala.dronedispatcher.service;

import com.musala.dronedispatcher.service.dto.DroneDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.musala.dronedispatcher.domain.Drone}.
 */
public interface DroneService {

    /**
     * Save a drone.
     *
     * @param droneDTO the entity to save.
     * @return the persisted entity.
     */
    DroneDTO save(DroneDTO droneDTO);

    /**
     * Get all the drones.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DroneDTO> findAll(Pageable pageable);
    /**
     * Get all the DroneDTO where DroneToMedications is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<DroneDTO> findAllWhereDroneToMedicationsIsNull();


    /**
     * Get the "id" drone.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DroneDTO> findOne(Long id);

    /**
     * Delete the "id" drone.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
