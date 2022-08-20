package com.musala.dronedispatcher.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import com.musala.dronedispatcher.domain.enumeration.ModelType;
import com.musala.dronedispatcher.domain.enumeration.StateType;

/**
 * A DTO for the {@link com.musala.dronedispatcher.domain.Drone} entity.
 */
public class DroneDTO implements Serializable {
    
    private Long id;

    @NotNull
    @Size(max = 100)
    private String serialNumber;

    @NotNull
    private ModelType model;

    @DecimalMin(value = "0")
    @DecimalMax(value = "500")
    private Float weightLimit;

    @DecimalMin(value = "0")
    @DecimalMax(value = "100")
    private Float batteryCapacity;

    private StateType state;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public ModelType getModel() {
        return model;
    }

    public void setModel(ModelType model) {
        this.model = model;
    }

    public Float getWeightLimit() {
        return weightLimit;
    }

    public void setWeightLimit(Float weightLimit) {
        this.weightLimit = weightLimit;
    }

    public Float getBatteryCapacity() {
        return batteryCapacity;
    }

    public void setBatteryCapacity(Float batteryCapacity) {
        this.batteryCapacity = batteryCapacity;
    }

    public StateType getState() {
        return state;
    }

    public void setState(StateType state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DroneDTO)) {
            return false;
        }

        return id != null && id.equals(((DroneDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DroneDTO{" +
            "id=" + getId() +
            ", serialNumber='" + getSerialNumber() + "'" +
            ", model='" + getModel() + "'" +
            ", weightLimit=" + getWeightLimit() +
            ", batteryCapacity=" + getBatteryCapacity() +
            ", state='" + getState() + "'" +
            "}";
    }
}
