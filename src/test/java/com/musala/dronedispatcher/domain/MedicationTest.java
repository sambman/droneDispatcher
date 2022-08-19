package com.musala.dronedispatcher.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.musala.dronedispatcher.web.rest.TestUtil;

public class MedicationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Medication.class);
        Medication medication1 = new Medication();
        medication1.setId(1L);
        Medication medication2 = new Medication();
        medication2.setId(medication1.getId());
        assertThat(medication1).isEqualTo(medication2);
        medication2.setId(2L);
        assertThat(medication1).isNotEqualTo(medication2);
        medication1.setId(null);
        assertThat(medication1).isNotEqualTo(medication2);
    }
}
