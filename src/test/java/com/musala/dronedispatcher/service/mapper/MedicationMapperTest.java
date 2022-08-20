package com.musala.dronedispatcher.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MedicationMapperTest {

    private MedicationMapper medicationMapper;

    @BeforeEach
    public void setUp() {
        medicationMapper = new MedicationMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(medicationMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(medicationMapper.fromId(null)).isNull();
    }
}
