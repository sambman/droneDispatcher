package com.musala.dronedispatcher.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.musala.dronedispatcher.web.rest.TestUtil;

public class DroneToMedicationsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DroneToMedicationsDTO.class);
        DroneToMedicationsDTO droneToMedicationsDTO1 = new DroneToMedicationsDTO();
        droneToMedicationsDTO1.setId(1L);
        DroneToMedicationsDTO droneToMedicationsDTO2 = new DroneToMedicationsDTO();
        assertThat(droneToMedicationsDTO1).isNotEqualTo(droneToMedicationsDTO2);
        droneToMedicationsDTO2.setId(droneToMedicationsDTO1.getId());
        assertThat(droneToMedicationsDTO1).isEqualTo(droneToMedicationsDTO2);
        droneToMedicationsDTO2.setId(2L);
        assertThat(droneToMedicationsDTO1).isNotEqualTo(droneToMedicationsDTO2);
        droneToMedicationsDTO1.setId(null);
        assertThat(droneToMedicationsDTO1).isNotEqualTo(droneToMedicationsDTO2);
    }
}
