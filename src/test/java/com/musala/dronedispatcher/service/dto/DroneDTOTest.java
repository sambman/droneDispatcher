package com.musala.dronedispatcher.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.musala.dronedispatcher.web.rest.TestUtil;

public class DroneDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DroneDTO.class);
        DroneDTO droneDTO1 = new DroneDTO();
        droneDTO1.setId(1L);
        DroneDTO droneDTO2 = new DroneDTO();
        assertThat(droneDTO1).isNotEqualTo(droneDTO2);
        droneDTO2.setId(droneDTO1.getId());
        assertThat(droneDTO1).isEqualTo(droneDTO2);
        droneDTO2.setId(2L);
        assertThat(droneDTO1).isNotEqualTo(droneDTO2);
        droneDTO1.setId(null);
        assertThat(droneDTO1).isNotEqualTo(droneDTO2);
    }
}
