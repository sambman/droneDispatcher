package com.musala.dronedispatcher.web.rest;

import com.musala.dronedispatcher.DroneDispatcherApp;
import com.musala.dronedispatcher.domain.Medication;
import com.musala.dronedispatcher.repository.MedicationRepository;
import com.musala.dronedispatcher.service.MedicationService;
import com.musala.dronedispatcher.service.dto.MedicationDTO;
import com.musala.dronedispatcher.service.mapper.MedicationMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link MedicationResource} REST controller.
 */
@SpringBootTest(classes = DroneDispatcherApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class MedicationResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Float DEFAULT_WEIGHT = 1F;
    private static final Float UPDATED_WEIGHT = 2F;

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    @Autowired
    private MedicationRepository medicationRepository;

    @Autowired
    private MedicationMapper medicationMapper;

    @Autowired
    private MedicationService medicationService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMedicationMockMvc;

    private Medication medication;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Medication createEntity(EntityManager em) {
        Medication medication = new Medication()
            .name(DEFAULT_NAME)
            .weight(DEFAULT_WEIGHT)
            .code(DEFAULT_CODE)
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE);
        return medication;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Medication createUpdatedEntity(EntityManager em) {
        Medication medication = new Medication()
            .name(UPDATED_NAME)
            .weight(UPDATED_WEIGHT)
            .code(UPDATED_CODE)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE);
        return medication;
    }

    @BeforeEach
    public void initTest() {
        medication = createEntity(em);
    }

    @Test
    @Transactional
    public void createMedication() throws Exception {
        int databaseSizeBeforeCreate = medicationRepository.findAll().size();
        // Create the Medication
        MedicationDTO medicationDTO = medicationMapper.toDto(medication);
        restMedicationMockMvc.perform(post("/api/medications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(medicationDTO)))
            .andExpect(status().isCreated());

        // Validate the Medication in the database
        List<Medication> medicationList = medicationRepository.findAll();
        assertThat(medicationList).hasSize(databaseSizeBeforeCreate + 1);
        Medication testMedication = medicationList.get(medicationList.size() - 1);
        assertThat(testMedication.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMedication.getWeight()).isEqualTo(DEFAULT_WEIGHT);
        assertThat(testMedication.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testMedication.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testMedication.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createMedicationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = medicationRepository.findAll().size();

        // Create the Medication with an existing ID
        medication.setId(1L);
        MedicationDTO medicationDTO = medicationMapper.toDto(medication);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMedicationMockMvc.perform(post("/api/medications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(medicationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Medication in the database
        List<Medication> medicationList = medicationRepository.findAll();
        assertThat(medicationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = medicationRepository.findAll().size();
        // set the field null
        medication.setName(null);

        // Create the Medication, which fails.
        MedicationDTO medicationDTO = medicationMapper.toDto(medication);


        restMedicationMockMvc.perform(post("/api/medications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(medicationDTO)))
            .andExpect(status().isBadRequest());

        List<Medication> medicationList = medicationRepository.findAll();
        assertThat(medicationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkWeightIsRequired() throws Exception {
        int databaseSizeBeforeTest = medicationRepository.findAll().size();
        // set the field null
        medication.setWeight(null);

        // Create the Medication, which fails.
        MedicationDTO medicationDTO = medicationMapper.toDto(medication);


        restMedicationMockMvc.perform(post("/api/medications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(medicationDTO)))
            .andExpect(status().isBadRequest());

        List<Medication> medicationList = medicationRepository.findAll();
        assertThat(medicationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = medicationRepository.findAll().size();
        // set the field null
        medication.setCode(null);

        // Create the Medication, which fails.
        MedicationDTO medicationDTO = medicationMapper.toDto(medication);


        restMedicationMockMvc.perform(post("/api/medications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(medicationDTO)))
            .andExpect(status().isBadRequest());

        List<Medication> medicationList = medicationRepository.findAll();
        assertThat(medicationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMedications() throws Exception {
        // Initialize the database
        medicationRepository.saveAndFlush(medication);

        // Get all the medicationList
        restMedicationMockMvc.perform(get("/api/medications?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(medication.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))));
    }
    
    @Test
    @Transactional
    public void getMedication() throws Exception {
        // Initialize the database
        medicationRepository.saveAndFlush(medication);

        // Get the medication
        restMedicationMockMvc.perform(get("/api/medications/{id}", medication.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(medication.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.weight").value(DEFAULT_WEIGHT.doubleValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)));
    }
    @Test
    @Transactional
    public void getNonExistingMedication() throws Exception {
        // Get the medication
        restMedicationMockMvc.perform(get("/api/medications/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMedication() throws Exception {
        // Initialize the database
        medicationRepository.saveAndFlush(medication);

        int databaseSizeBeforeUpdate = medicationRepository.findAll().size();

        // Update the medication
        Medication updatedMedication = medicationRepository.findById(medication.getId()).get();
        // Disconnect from session so that the updates on updatedMedication are not directly saved in db
        em.detach(updatedMedication);
        updatedMedication
            .name(UPDATED_NAME)
            .weight(UPDATED_WEIGHT)
            .code(UPDATED_CODE)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE);
        MedicationDTO medicationDTO = medicationMapper.toDto(updatedMedication);

        restMedicationMockMvc.perform(put("/api/medications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(medicationDTO)))
            .andExpect(status().isOk());

        // Validate the Medication in the database
        List<Medication> medicationList = medicationRepository.findAll();
        assertThat(medicationList).hasSize(databaseSizeBeforeUpdate);
        Medication testMedication = medicationList.get(medicationList.size() - 1);
        assertThat(testMedication.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMedication.getWeight()).isEqualTo(UPDATED_WEIGHT);
        assertThat(testMedication.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testMedication.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testMedication.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingMedication() throws Exception {
        int databaseSizeBeforeUpdate = medicationRepository.findAll().size();

        // Create the Medication
        MedicationDTO medicationDTO = medicationMapper.toDto(medication);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMedicationMockMvc.perform(put("/api/medications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(medicationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Medication in the database
        List<Medication> medicationList = medicationRepository.findAll();
        assertThat(medicationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMedication() throws Exception {
        // Initialize the database
        medicationRepository.saveAndFlush(medication);

        int databaseSizeBeforeDelete = medicationRepository.findAll().size();

        // Delete the medication
        restMedicationMockMvc.perform(delete("/api/medications/{id}", medication.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Medication> medicationList = medicationRepository.findAll();
        assertThat(medicationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
