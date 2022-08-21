package com.musala.dronedispatcher.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

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

    @OneToMany(mappedBy = "droneToMedications")
    private Set<Medication> medications = new HashSet<>();

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "drone_to_medications_drone",
               joinColumns = @JoinColumn(name = "drone_to_medications_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "drone_id", referencedColumnName = "id"))
    private Set<Drone> drones = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Medication> getMedications() {
        return medications;
    }

    public DroneToMedications medications(Set<Medication> medications) {
        this.medications = medications;
        return this;
    }

    public DroneToMedications addMedication(Medication medication) {
        this.medications.add(medication);
        medication.setDroneToMedications(this);
        return this;
    }

    public DroneToMedications removeMedication(Medication medication) {
        this.medications.remove(medication);
        medication.setDroneToMedications(null);
        return this;
    }

    public void setMedications(Set<Medication> medications) {
        this.medications = medications;
    }

    public Set<Drone> getDrones() {
        return drones;
    }

    public DroneToMedications drones(Set<Drone> drones) {
        this.drones = drones;
        return this;
    }

    public DroneToMedications addDrone(Drone drone) {
        this.drones.add(drone);
        drone.getDroneToMedications().add(this);
        return this;
    }

    public DroneToMedications removeDrone(Drone drone) {
        this.drones.remove(drone);
        drone.getDroneToMedications().remove(this);
        return this;
    }

    public void setDrones(Set<Drone> drones) {
        this.drones = drones;
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
