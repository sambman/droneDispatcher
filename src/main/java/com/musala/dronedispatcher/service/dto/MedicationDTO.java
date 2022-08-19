package com.musala.dronedispatcher.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.musala.dronedispatcher.domain.Medication} entity.
 */
public class MedicationDTO implements Serializable {
    
    private Long id;

    @NotNull
    @Pattern(regexp = "([A-Z]?|_).([A-Z,\\-,_])+")
    private String name;

    @NotNull
    private Float weight;

    @NotNull
    @Pattern(regexp = "([A-Z,0-9,\\-,_])+")
    private String code;

    @Lob
    private byte[] image;

    private String imageContentType;

    private Long droneToMedicationsId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public Long getDroneToMedicationsId() {
        return droneToMedicationsId;
    }

    public void setDroneToMedicationsId(Long droneToMedicationsId) {
        this.droneToMedicationsId = droneToMedicationsId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MedicationDTO)) {
            return false;
        }

        return id != null && id.equals(((MedicationDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MedicationDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", weight=" + getWeight() +
            ", code='" + getCode() + "'" +
            ", image='" + getImage() + "'" +
            ", droneToMedicationsId=" + getDroneToMedicationsId() +
            "}";
    }
}
