package com.musala.dronedispatcher.service.impl;

import com.musala.dronedispatcher.service.DroneService;
import com.musala.dronedispatcher.domain.Drone;
import com.musala.dronedispatcher.repository.DroneRepository;
import com.musala.dronedispatcher.service.dto.DroneDTO;
import com.musala.dronedispatcher.service.mapper.DroneMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Drone}.
 */
@Service
@Transactional
public class DroneServiceImpl implements DroneService {

    private final Logger log = LoggerFactory.getLogger(DroneServiceImpl.class);

    private final DroneRepository droneRepository;

    private final DroneMapper droneMapper;

    public DroneServiceImpl(DroneRepository droneRepository, DroneMapper droneMapper) {
        this.droneRepository = droneRepository;
        this.droneMapper = droneMapper;
    }

    @Override
    public DroneDTO save(DroneDTO droneDTO) {
        log.debug("Request to save Drone : {}", droneDTO);
        Drone drone = droneMapper.toEntity(droneDTO);
        drone = droneRepository.save(drone);
        return droneMapper.toDto(drone);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DroneDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Drones");
        return droneRepository.findAll(pageable)
            .map(droneMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<DroneDTO> findOne(Long id) {
        log.debug("Request to get Drone : {}", id);
        return droneRepository.findById(id)
            .map(droneMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Drone : {}", id);
        droneRepository.deleteById(id);
    }

    @Override
    public List<DroneDTO> findAllWithoutPagination() {
        return droneMapper.toDto(droneRepository.findAll());
    }
}
