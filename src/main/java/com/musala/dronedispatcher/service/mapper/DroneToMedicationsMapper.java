package com.musala.dronedispatcher.service.mapper;


import com.musala.dronedispatcher.domain.*;
import com.musala.dronedispatcher.service.dto.DroneToMedicationsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link DroneToMedications} and its DTO {@link DroneToMedicationsDTO}.
 */
@Mapper(componentModel = "spring", uses = {DroneMapper.class})
public interface DroneToMedicationsMapper extends EntityMapper<DroneToMedicationsDTO, DroneToMedications> {

    @Mapping(source = "drone.id", target = "droneId")
    DroneToMedicationsDTO toDto(DroneToMedications droneToMedications);

    @Mapping(source = "droneId", target = "drone")
    DroneToMedications toEntity(DroneToMedicationsDTO droneToMedicationsDTO);

    default DroneToMedications fromId(Long id) {
        if (id == null) {
            return null;
        }
        DroneToMedications droneToMedications = new DroneToMedications();
        droneToMedications.setId(id);
        return droneToMedications;
    }
}
