package com.musala.dronedispatcher.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class DroneToMedicationsMapperTest {

    private DroneToMedicationsMapper droneToMedicationsMapper;

    @BeforeEach
    public void setUp() {
        droneToMedicationsMapper = new DroneToMedicationsMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(droneToMedicationsMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(droneToMedicationsMapper.fromId(null)).isNull();
    }
}
