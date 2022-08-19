package com.musala.dronedispatcher.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class DroneMapperTest {

    private DroneMapper droneMapper;

    @BeforeEach
    public void setUp() {
        droneMapper = new DroneMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(droneMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(droneMapper.fromId(null)).isNull();
    }
}
