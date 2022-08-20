package com.musala.dronedispatcher.service.mapper;


import com.musala.dronedispatcher.domain.*;
import com.musala.dronedispatcher.service.dto.MedicationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Medication} and its DTO {@link MedicationDTO}.
 */
@Mapper(componentModel = "spring", uses = {DroneToMedicationsMapper.class})
public interface MedicationMapper extends EntityMapper<MedicationDTO, Medication> {

    @Mapping(source = "droneToMedications.id", target = "droneToMedicationsId")
    MedicationDTO toDto(Medication medication);

    @Mapping(source = "droneToMedicationsId", target = "droneToMedications")
    Medication toEntity(MedicationDTO medicationDTO);

    default Medication fromId(Long id) {
        if (id == null) {
            return null;
        }
        Medication medication = new Medication();
        medication.setId(id);
        return medication;
    }
}
