package com.musala.dronedispatcher.service;

import com.musala.dronedispatcher.service.dto.MedicationDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.musala.dronedispatcher.domain.Medication}.
 */
public interface MedicationService {

    /**
     * Save a medication.
     *
     * @param medicationDTO the entity to save.
     * @return the persisted entity.
     */
    MedicationDTO save(MedicationDTO medicationDTO);

    /**
     * Get all the medications.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MedicationDTO> findAll(Pageable pageable);


    /**
     * Get the "id" medication.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MedicationDTO> findOne(Long id);

    /**
     * Delete the "id" medication.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
