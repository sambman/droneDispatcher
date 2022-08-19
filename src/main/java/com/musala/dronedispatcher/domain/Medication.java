package com.musala.dronedispatcher.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Medication.
 */
@Entity
@Table(name = "medication")
public class Medication implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Pattern(regexp = "([A-Z]?|_).([A-Z,\\-,_])+")
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @NotNull
    @Column(name = "weight", nullable = false)
    private Float weight;

    @NotNull
    @Pattern(regexp = "([A-Z,0-9,\\-,_])+")
    @Column(name = "code", nullable = false)
    private String code;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @Column(name = "image_content_type")
    private String imageContentType;

    @ManyToOne
    @JsonIgnoreProperties(value = "medications", allowSetters = true)
    private DroneToMedications droneToMedications;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Medication name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getWeight() {
        return weight;
    }

    public Medication weight(Float weight) {
        this.weight = weight;
        return this;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public String getCode() {
        return code;
    }

    public Medication code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public byte[] getImage() {
        return image;
    }

    public Medication image(byte[] image) {
        this.image = image;
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public Medication imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public DroneToMedications getDroneToMedications() {
        return droneToMedications;
    }

    public Medication droneToMedications(DroneToMedications droneToMedications) {
        this.droneToMedications = droneToMedications;
        return this;
    }

    public void setDroneToMedications(DroneToMedications droneToMedications) {
        this.droneToMedications = droneToMedications;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Medication)) {
            return false;
        }
        return id != null && id.equals(((Medication) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Medication{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", weight=" + getWeight() +
            ", code='" + getCode() + "'" +
            ", image='" + getImage() + "'" +
            ", imageContentType='" + getImageContentType() + "'" +
            "}";
    }
}
