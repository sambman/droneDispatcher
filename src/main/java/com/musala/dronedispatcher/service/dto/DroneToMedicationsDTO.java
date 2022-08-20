package com.musala.dronedispatcher.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A DTO for the {@link com.musala.dronedispatcher.domain.DroneToMedications} entity.
 */
public class DroneToMedicationsDTO implements Serializable {
    
    private Long id;


    private Long droneId;

    private DroneDTO drone;

    private Set<MedicationDTO> medications = new HashSet<>();
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDroneId() {
        return droneId;
    }

    public void setDroneId(Long droneId) {
        this.droneId = droneId;
    }

    public DroneDTO getDrone() {
        return drone;
    }

    public void setDrone(DroneDTO drone) {
        this.drone = drone;
    }

    public Set<MedicationDTO> getMedications() {
        return medications;
    }

    public void setMedications(Set<MedicationDTO> medications) {
        this.medications = medications;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DroneToMedicationsDTO)) {
            return false;
        }

        return id != null && id.equals(((DroneToMedicationsDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DroneToMedicationsDTO{" +
            "id=" + getId() +
            ", droneId=" + getDroneId() +
            "}";
    }
}
