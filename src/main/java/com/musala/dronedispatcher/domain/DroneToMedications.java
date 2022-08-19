package com.musala.dronedispatcher.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A DroneToMedications.
 */
@Entity
@Table(name = "drone_to_medications")
public class DroneToMedications implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private Drone drone;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Drone getDrone() {
        return drone;
    }

    public DroneToMedications drone(Drone drone) {
        this.drone = drone;
        return this;
    }

    public void setDrone(Drone drone) {
        this.drone = drone;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DroneToMedications)) {
            return false;
        }
        return id != null && id.equals(((DroneToMedications) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DroneToMedications{" +
            "id=" + getId() +
            "}";
    }
}
