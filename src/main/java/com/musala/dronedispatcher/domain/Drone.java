package com.musala.dronedispatcher.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.musala.dronedispatcher.domain.enumeration.ModelType;

import com.musala.dronedispatcher.domain.enumeration.StateType;

/**
 * A Drone.
 */
@Entity
@Table(name = "drone")
public class Drone implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "serial_number", length = 100, nullable = false, unique = true)
    private String serialNumber;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "model", nullable = false)
    private ModelType model;

    @DecimalMin(value = "0")
    @DecimalMax(value = "500")
    @Column(name = "weight_limit")
    private Float weightLimit;

    @DecimalMin(value = "0")
    @DecimalMax(value = "100")
    @Column(name = "battery_capacity")
    private Float batteryCapacity;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    private StateType state;

    @ManyToMany(mappedBy = "drones")
    @JsonIgnore
    private Set<DroneToMedications> droneToMedications = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public Drone serialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
        return this;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public ModelType getModel() {
        return model;
    }

    public Drone model(ModelType model) {
        this.model = model;
        return this;
    }

    public void setModel(ModelType model) {
        this.model = model;
    }

    public Float getWeightLimit() {
        return weightLimit;
    }

    public Drone weightLimit(Float weightLimit) {
        this.weightLimit = weightLimit;
        return this;
    }

    public void setWeightLimit(Float weightLimit) {
        this.weightLimit = weightLimit;
    }

    public Float getBatteryCapacity() {
        return batteryCapacity;
    }

    public Drone batteryCapacity(Float batteryCapacity) {
        this.batteryCapacity = batteryCapacity;
        return this;
    }

    public void setBatteryCapacity(Float batteryCapacity) {
        this.batteryCapacity = batteryCapacity;
    }

    public StateType getState() {
        return state;
    }

    public Drone state(StateType state) {
        this.state = state;
        return this;
    }

    public void setState(StateType state) {
        this.state = state;
    }

    public Set<DroneToMedications> getDroneToMedications() {
        return droneToMedications;
    }

    public Drone droneToMedications(Set<DroneToMedications> droneToMedications) {
        this.droneToMedications = droneToMedications;
        return this;
    }

    public Drone addDroneToMedications(DroneToMedications droneToMedications) {
        this.droneToMedications.add(droneToMedications);
        droneToMedications.getDrones().add(this);
        return this;
    }

    public Drone removeDroneToMedications(DroneToMedications droneToMedications) {
        this.droneToMedications.remove(droneToMedications);
        droneToMedications.getDrones().remove(this);
        return this;
    }

    public void setDroneToMedications(Set<DroneToMedications> droneToMedications) {
        this.droneToMedications = droneToMedications;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Drone)) {
            return false;
        }
        return id != null && id.equals(((Drone) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Drone{" +
            "id=" + getId() +
            ", serialNumber='" + getSerialNumber() + "'" +
            ", model='" + getModel() + "'" +
            ", weightLimit=" + getWeightLimit() +
            ", batteryCapacity=" + getBatteryCapacity() +
            ", state='" + getState() + "'" +
            "}";
    }
}
