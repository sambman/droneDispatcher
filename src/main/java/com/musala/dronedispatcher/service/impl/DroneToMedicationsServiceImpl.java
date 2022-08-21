package com.musala.dronedispatcher.service.impl;

import com.musala.dronedispatcher.service.DroneService;
import com.musala.dronedispatcher.service.DroneToMedicationsService;
import com.musala.dronedispatcher.domain.DroneToMedications;
import com.musala.dronedispatcher.domain.enumeration.StateType;
import com.musala.dronedispatcher.repository.DroneToMedicationsRepository;
import com.musala.dronedispatcher.service.dto.DroneDTO;
import com.musala.dronedispatcher.service.dto.DroneToMedicationsDTO;
import com.musala.dronedispatcher.service.dto.MedicationDTO;
import com.musala.dronedispatcher.service.mapper.DroneMapper;
import com.musala.dronedispatcher.service.mapper.DroneToMedicationsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link DroneToMedications}.
 */
@Service
@Transactional
public class DroneToMedicationsServiceImpl implements DroneToMedicationsService {

    private final Logger log = LoggerFactory.getLogger(DroneToMedicationsServiceImpl.class);

    private final DroneToMedicationsRepository droneToMedicationsRepository;

    private final DroneToMedicationsMapper droneToMedicationsMapper;

    private final DroneService droneService;

    public DroneToMedicationsServiceImpl(DroneToMedicationsRepository droneToMedicationsRepository, DroneToMedicationsMapper droneToMedicationsMapper, DroneService droneService) {
        this.droneToMedicationsRepository = droneToMedicationsRepository;
        this.droneToMedicationsMapper = droneToMedicationsMapper;
        this.droneService = droneService;
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


    public Page<DroneToMedicationsDTO> findAllWithEagerRelationships(Pageable pageable) {
        return droneToMedicationsRepository.findAllWithEagerRelationships(pageable).map(droneToMedicationsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DroneToMedicationsDTO> findOne(Long id) {
        log.debug("Request to get DroneToMedications : {}", id);
        return droneToMedicationsRepository.findOneWithEagerRelationships(id)
            .map(droneToMedicationsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DroneToMedications : {}", id);
        droneToMedicationsRepository.deleteById(id);
    }

    @Override
    public List<MedicationDTO> getLoadedMedications(Long droneId) {
        Optional<DroneDTO> optionalDrone = droneService.findOne(droneId);
        if (!optionalDrone.isPresent()) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Cannot find drone with ID: [" + droneId + "]");
        }
        DroneDTO droneDTO = optionalDrone.get();
        if (!droneDTO.getState().equals(StateType.LOADED)) {
            throw new HttpClientErrorException(HttpStatus.FORBIDDEN, "Drone is not on state: " + StateType.LOADED);
        }
        DroneToMedications droneToMedications = droneToMedicationsRepository.findLastByDroneId(droneId);

        return droneToMedicationsMapper.toDto(droneToMedications).getMedications().stream().collect(Collectors.toList());
    }
}
