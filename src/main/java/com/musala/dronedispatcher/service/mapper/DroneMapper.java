package com.musala.dronedispatcher.service.mapper;


import com.musala.dronedispatcher.domain.*;
import com.musala.dronedispatcher.service.dto.DroneDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Drone} and its DTO {@link DroneDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DroneMapper extends EntityMapper<DroneDTO, Drone> {


    @Mapping(target = "droneToMedications", ignore = true)
    Drone toEntity(DroneDTO droneDTO);

    default Drone fromId(Long id) {
        if (id == null) {
            return null;
        }
        Drone drone = new Drone();
        drone.setId(id);
        return drone;
    }
}
