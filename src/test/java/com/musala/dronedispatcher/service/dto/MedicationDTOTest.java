package com.musala.dronedispatcher.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.musala.dronedispatcher.web.rest.TestUtil;

public class MedicationDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MedicationDTO.class);
        MedicationDTO medicationDTO1 = new MedicationDTO();
        medicationDTO1.setId(1L);
        MedicationDTO medicationDTO2 = new MedicationDTO();
        assertThat(medicationDTO1).isNotEqualTo(medicationDTO2);
        medicationDTO2.setId(medicationDTO1.getId());
        assertThat(medicationDTO1).isEqualTo(medicationDTO2);
        medicationDTO2.setId(2L);
        assertThat(medicationDTO1).isNotEqualTo(medicationDTO2);
        medicationDTO1.setId(null);
        assertThat(medicationDTO1).isNotEqualTo(medicationDTO2);
    }
}
