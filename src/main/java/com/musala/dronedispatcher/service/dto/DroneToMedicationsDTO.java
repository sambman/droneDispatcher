package com.musala.dronedispatcher.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.musala.dronedispatcher.domain.DroneToMedications} entity.
 */
public class DroneToMedicationsDTO implements Serializable {
    
    private Long id;


    private Long droneId;
    
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
