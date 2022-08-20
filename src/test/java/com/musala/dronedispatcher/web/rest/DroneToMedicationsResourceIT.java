package com.musala.dronedispatcher.web.rest;

import com.musala.dronedispatcher.DroneDispatcherApp;
import com.musala.dronedispatcher.domain.DroneToMedications;
import com.musala.dronedispatcher.domain.Drone;
import com.musala.dronedispatcher.repository.DroneToMedicationsRepository;
import com.musala.dronedispatcher.service.DroneToMedicationsService;
import com.musala.dronedispatcher.service.dto.DroneToMedicationsDTO;
import com.musala.dronedispatcher.service.mapper.DroneToMedicationsMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link DroneToMedicationsResource} REST controller.
 */
@SpringBootTest(classes = DroneDispatcherApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class DroneToMedicationsResourceIT {

    @Autowired
    private DroneToMedicationsRepository droneToMedicationsRepository;

    @Autowired
    private DroneToMedicationsMapper droneToMedicationsMapper;

    @Autowired
    private DroneToMedicationsService droneToMedicationsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDroneToMedicationsMockMvc;

    private DroneToMedications droneToMedications;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DroneToMedications createEntity(EntityManager em) {
        DroneToMedications droneToMedications = new DroneToMedications();
        // Add required entity
        Drone drone;
        if (TestUtil.findAll(em, Drone.class).isEmpty()) {
            drone = DroneResourceIT.createEntity(em);
            em.persist(drone);
            em.flush();
        } else {
            drone = TestUtil.findAll(em, Drone.class).get(0);
        }
        droneToMedications.setDrone(drone);
        return droneToMedications;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DroneToMedications createUpdatedEntity(EntityManager em) {
        DroneToMedications droneToMedications = new DroneToMedications();
        // Add required entity
        Drone drone;
        if (TestUtil.findAll(em, Drone.class).isEmpty()) {
            drone = DroneResourceIT.createUpdatedEntity(em);
            em.persist(drone);
            em.flush();
        } else {
            drone = TestUtil.findAll(em, Drone.class).get(0);
        }
        droneToMedications.setDrone(drone);
        return droneToMedications;
    }

    @BeforeEach
    public void initTest() {
        droneToMedications = createEntity(em);
    }

    @Test
    @Transactional
    public void createDroneToMedications() throws Exception {
        int databaseSizeBeforeCreate = droneToMedicationsRepository.findAll().size();
        // Create the DroneToMedications
        DroneToMedicationsDTO droneToMedicationsDTO = droneToMedicationsMapper.toDto(droneToMedications);
        restDroneToMedicationsMockMvc.perform(post("/api/drone-to-medications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(droneToMedicationsDTO)))
            .andExpect(status().isCreated());

        // Validate the DroneToMedications in the database
        List<DroneToMedications> droneToMedicationsList = droneToMedicationsRepository.findAll();
        assertThat(droneToMedicationsList).hasSize(databaseSizeBeforeCreate + 1);
        DroneToMedications testDroneToMedications = droneToMedicationsList.get(droneToMedicationsList.size() - 1);
    }

    @Test
    @Transactional
    public void createDroneToMedicationsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = droneToMedicationsRepository.findAll().size();

        // Create the DroneToMedications with an existing ID
        droneToMedications.setId(1L);
        DroneToMedicationsDTO droneToMedicationsDTO = droneToMedicationsMapper.toDto(droneToMedications);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDroneToMedicationsMockMvc.perform(post("/api/drone-to-medications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(droneToMedicationsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DroneToMedications in the database
        List<DroneToMedications> droneToMedicationsList = droneToMedicationsRepository.findAll();
        assertThat(droneToMedicationsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDroneToMedications() throws Exception {
        // Initialize the database
        droneToMedicationsRepository.saveAndFlush(droneToMedications);

        // Get all the droneToMedicationsList
        restDroneToMedicationsMockMvc.perform(get("/api/drone-to-medications?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(droneToMedications.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getDroneToMedications() throws Exception {
        // Initialize the database
        droneToMedicationsRepository.saveAndFlush(droneToMedications);

        // Get the droneToMedications
        restDroneToMedicationsMockMvc.perform(get("/api/drone-to-medications/{id}", droneToMedications.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(droneToMedications.getId().intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingDroneToMedications() throws Exception {
        // Get the droneToMedications
        restDroneToMedicationsMockMvc.perform(get("/api/drone-to-medications/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDroneToMedications() throws Exception {
        // Initialize the database
        droneToMedicationsRepository.saveAndFlush(droneToMedications);

        int databaseSizeBeforeUpdate = droneToMedicationsRepository.findAll().size();

        // Update the droneToMedications
        DroneToMedications updatedDroneToMedications = droneToMedicationsRepository.findById(droneToMedications.getId()).get();
        // Disconnect from session so that the updates on updatedDroneToMedications are not directly saved in db
        em.detach(updatedDroneToMedications);
        DroneToMedicationsDTO droneToMedicationsDTO = droneToMedicationsMapper.toDto(updatedDroneToMedications);

        restDroneToMedicationsMockMvc.perform(put("/api/drone-to-medications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(droneToMedicationsDTO)))
            .andExpect(status().isOk());

        // Validate the DroneToMedications in the database
        List<DroneToMedications> droneToMedicationsList = droneToMedicationsRepository.findAll();
        assertThat(droneToMedicationsList).hasSize(databaseSizeBeforeUpdate);
        DroneToMedications testDroneToMedications = droneToMedicationsList.get(droneToMedicationsList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingDroneToMedications() throws Exception {
        int databaseSizeBeforeUpdate = droneToMedicationsRepository.findAll().size();

        // Create the DroneToMedications
        DroneToMedicationsDTO droneToMedicationsDTO = droneToMedicationsMapper.toDto(droneToMedications);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDroneToMedicationsMockMvc.perform(put("/api/drone-to-medications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(droneToMedicationsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DroneToMedications in the database
        List<DroneToMedications> droneToMedicationsList = droneToMedicationsRepository.findAll();
        assertThat(droneToMedicationsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDroneToMedications() throws Exception {
        // Initialize the database
        droneToMedicationsRepository.saveAndFlush(droneToMedications);

        int databaseSizeBeforeDelete = droneToMedicationsRepository.findAll().size();

        // Delete the droneToMedications
        restDroneToMedicationsMockMvc.perform(delete("/api/drone-to-medications/{id}", droneToMedications.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DroneToMedications> droneToMedicationsList = droneToMedicationsRepository.findAll();
        assertThat(droneToMedicationsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
