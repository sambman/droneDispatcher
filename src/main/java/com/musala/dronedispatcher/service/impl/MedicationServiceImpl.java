package com.musala.dronedispatcher.service.impl;

import com.musala.dronedispatcher.service.MedicationService;
import com.musala.dronedispatcher.domain.Medication;
import com.musala.dronedispatcher.repository.MedicationRepository;
import com.musala.dronedispatcher.service.dto.MedicationDTO;
import com.musala.dronedispatcher.service.mapper.MedicationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Medication}.
 */
@Service
@Transactional
public class MedicationServiceImpl implements MedicationService {

    private final Logger log = LoggerFactory.getLogger(MedicationServiceImpl.class);

    private final MedicationRepository medicationRepository;

    private final MedicationMapper medicationMapper;

    public MedicationServiceImpl(MedicationRepository medicationRepository, MedicationMapper medicationMapper) {
        this.medicationRepository = medicationRepository;
        this.medicationMapper = medicationMapper;
    }

    @Override
    public MedicationDTO save(MedicationDTO medicationDTO) {
        log.debug("Request to save Medication : {}", medicationDTO);
        Medication medication = medicationMapper.toEntity(medicationDTO);
        medication = medicationRepository.save(medication);
        return medicationMapper.toDto(medication);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MedicationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Medications");
        return medicationRepository.findAll(pageable)
            .map(medicationMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<MedicationDTO> findOne(Long id) {
        log.debug("Request to get Medication : {}", id);
        return medicationRepository.findById(id)
            .map(medicationMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Medication : {}", id);
        medicationRepository.deleteById(id);
    }
}
