package com.musala.dronedispatcher.service;

import com.musala.dronedispatcher.service.dto.DroneToMedicationsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.musala.dronedispatcher.domain.DroneToMedications}.
 */
public interface DroneToMedicationsService {

    /**
     * Save a droneToMedications.
     *
     * @param droneToMedicationsDTO the entity to save.
     * @return the persisted entity.
     */
    DroneToMedicationsDTO save(DroneToMedicationsDTO droneToMedicationsDTO);

    /**
     * Get all the droneToMedications.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DroneToMedicationsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" droneToMedications.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DroneToMedicationsDTO> findOne(Long id);

    /**
     * Delete the "id" droneToMedications.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
