package com.musala.dronedispatcher.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.musala.dronedispatcher.web.rest.TestUtil;

public class DroneTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Drone.class);
        Drone drone1 = new Drone();
        drone1.setId(1L);
        Drone drone2 = new Drone();
        drone2.setId(drone1.getId());
        assertThat(drone1).isEqualTo(drone2);
        drone2.setId(2L);
        assertThat(drone1).isNotEqualTo(drone2);
        drone1.setId(null);
        assertThat(drone1).isNotEqualTo(drone2);
    }
}
