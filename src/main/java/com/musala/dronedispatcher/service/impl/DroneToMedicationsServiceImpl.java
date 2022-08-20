package com.musala.dronedispatcher.service.impl;

import com.musala.dronedispatcher.service.DroneToMedicationsService;
import com.musala.dronedispatcher.domain.DroneToMedications;
import com.musala.dronedispatcher.repository.DroneToMedicationsRepository;
import com.musala.dronedispatcher.service.dto.DroneToMedicationsDTO;
import com.musala.dronedispatcher.service.mapper.DroneToMedicationsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link DroneToMedications}.
 */
@Service
@Transactional
public class DroneToMedicationsServiceImpl implements DroneToMedicationsService {

    private final Logger log = LoggerFactory.getLogger(DroneToMedicationsServiceImpl.class);

    private final DroneToMedicationsRepository droneToMedicationsRepository;

    private final DroneToMedicationsMapper droneToMedicationsMapper;

    public DroneToMedicationsServiceImpl(DroneToMedicationsRepository droneToMedicationsRepository, DroneToMedicationsMapper droneToMedicationsMapper) {
        this.droneToMedicationsRepository = droneToMedicationsRepository;
        this.droneToMedicationsMapper = droneToMedicationsMapper;
    }

    @Override
    public DroneToMedicationsDTO save(DroneToMedicationsDTO droneToMedicationsDTO) {
        log.debug("Request to save DroneToMedications : {}", droneToMedicationsDTO);
        DroneToMedications droneToMedications = droneToMedicationsMapper.toEntity(droneToMedicationsDTO);
        droneToMedications = droneToMedicationsRepository.save(droneToMedications);
        return droneToMedicationsMapper.toDto(droneToMedications);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DroneToMedicationsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DroneToMedications");
        return droneToMedicationsRepository.findAll(pageable)
            .map(droneToMedicationsMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<DroneToMedicationsDTO> findOne(Long id) {
        log.debug("Request to get DroneToMedications : {}", id);
        return droneToMedicationsRepository.findById(id)
            .map(droneToMedicationsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DroneToMedications : {}", id);
        droneToMedicationsRepository.deleteById(id);
    }
}
