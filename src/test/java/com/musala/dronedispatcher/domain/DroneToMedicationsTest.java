package com.musala.dronedispatcher.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.musala.dronedispatcher.web.rest.TestUtil;

public class DroneToMedicationsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DroneToMedications.class);
        DroneToMedications droneToMedications1 = new DroneToMedications();
        droneToMedications1.setId(1L);
        DroneToMedications droneToMedications2 = new DroneToMedications();
        droneToMedications2.setId(droneToMedications1.getId());
        assertThat(droneToMedications1).isEqualTo(droneToMedications2);
        droneToMedications2.setId(2L);
        assertThat(droneToMedications1).isNotEqualTo(droneToMedications2);
        droneToMedications1.setId(null);
        assertThat(droneToMedications1).isNotEqualTo(droneToMedications2);
    }
}
