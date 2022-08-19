package com.musala.dronedispatcher.web.rest;

import com.musala.dronedispatcher.DroneDispatcherApp;
import com.musala.dronedispatcher.domain.Drone;
import com.musala.dronedispatcher.repository.DroneRepository;
import com.musala.dronedispatcher.service.DroneService;
import com.musala.dronedispatcher.service.dto.DroneDTO;
import com.musala.dronedispatcher.service.mapper.DroneMapper;

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

import com.musala.dronedispatcher.domain.enumeration.ModelType;
import com.musala.dronedispatcher.domain.enumeration.StateType;
/**
 * Integration tests for the {@link DroneResource} REST controller.
 */
@SpringBootTest(classes = DroneDispatcherApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class DroneResourceIT {

    private static final String DEFAULT_SERIAL_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_SERIAL_NUMBER = "BBBBBBBBBB";

    private static final ModelType DEFAULT_MODEL = ModelType.LIGHTWEIGHT;
    private static final ModelType UPDATED_MODEL = ModelType.MIDDLEWEIGHT;

    private static final Float DEFAULT_WEIGHT_LIMIT = 0F;
    private static final Float UPDATED_WEIGHT_LIMIT = 1F;

    private static final Float DEFAULT_BATTERY_CAPACITY = 0F;
    private static final Float UPDATED_BATTERY_CAPACITY = 1F;

    private static final StateType DEFAULT_STATE = StateType.IDLE;
    private static final StateType UPDATED_STATE = StateType.LOADING;

    @Autowired
    private DroneRepository droneRepository;

    @Autowired
    private DroneMapper droneMapper;

    @Autowired
    private DroneService droneService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDroneMockMvc;

    private Drone drone;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Drone createEntity(EntityManager em) {
        Drone drone = new Drone()
            .serialNumber(DEFAULT_SERIAL_NUMBER)
            .model(DEFAULT_MODEL)
            .weightLimit(DEFAULT_WEIGHT_LIMIT)
            .batteryCapacity(DEFAULT_BATTERY_CAPACITY)
            .state(DEFAULT_STATE);
        return drone;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Drone createUpdatedEntity(EntityManager em) {
        Drone drone = new Drone()
            .serialNumber(UPDATED_SERIAL_NUMBER)
            .model(UPDATED_MODEL)
            .weightLimit(UPDATED_WEIGHT_LIMIT)
            .batteryCapacity(UPDATED_BATTERY_CAPACITY)
            .state(UPDATED_STATE);
        return drone;
    }

    @BeforeEach
    public void initTest() {
        drone = createEntity(em);
    }

    @Test
    @Transactional
    public void createDrone() throws Exception {
        int databaseSizeBeforeCreate = droneRepository.findAll().size();
        // Create the Drone
        DroneDTO droneDTO = droneMapper.toDto(drone);
        restDroneMockMvc.perform(post("/api/drones")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(droneDTO)))
            .andExpect(status().isCreated());

        // Validate the Drone in the database
        List<Drone> droneList = droneRepository.findAll();
        assertThat(droneList).hasSize(databaseSizeBeforeCreate + 1);
        Drone testDrone = droneList.get(droneList.size() - 1);
        assertThat(testDrone.getSerialNumber()).isEqualTo(DEFAULT_SERIAL_NUMBER);
        assertThat(testDrone.getModel()).isEqualTo(DEFAULT_MODEL);
        assertThat(testDrone.getWeightLimit()).isEqualTo(DEFAULT_WEIGHT_LIMIT);
        assertThat(testDrone.getBatteryCapacity()).isEqualTo(DEFAULT_BATTERY_CAPACITY);
        assertThat(testDrone.getState()).isEqualTo(DEFAULT_STATE);
    }

    @Test
    @Transactional
    public void createDroneWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = droneRepository.findAll().size();

        // Create the Drone with an existing ID
        drone.setId(1L);
        DroneDTO droneDTO = droneMapper.toDto(drone);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDroneMockMvc.perform(post("/api/drones")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(droneDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Drone in the database
        List<Drone> droneList = droneRepository.findAll();
        assertThat(droneList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkSerialNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = droneRepository.findAll().size();
        // set the field null
        drone.setSerialNumber(null);

        // Create the Drone, which fails.
        DroneDTO droneDTO = droneMapper.toDto(drone);


        restDroneMockMvc.perform(post("/api/drones")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(droneDTO)))
            .andExpect(status().isBadRequest());

        List<Drone> droneList = droneRepository.findAll();
        assertThat(droneList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModelIsRequired() throws Exception {
        int databaseSizeBeforeTest = droneRepository.findAll().size();
        // set the field null
        drone.setModel(null);

        // Create the Drone, which fails.
        DroneDTO droneDTO = droneMapper.toDto(drone);


        restDroneMockMvc.perform(post("/api/drones")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(droneDTO)))
            .andExpect(status().isBadRequest());

        List<Drone> droneList = droneRepository.findAll();
        assertThat(droneList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStateIsRequired() throws Exception {
        int databaseSizeBeforeTest = droneRepository.findAll().size();
        // set the field null
        drone.setState(null);

        // Create the Drone, which fails.
        DroneDTO droneDTO = droneMapper.toDto(drone);


        restDroneMockMvc.perform(post("/api/drones")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(droneDTO)))
            .andExpect(status().isBadRequest());

        List<Drone> droneList = droneRepository.findAll();
        assertThat(droneList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDrones() throws Exception {
        // Initialize the database
        droneRepository.saveAndFlush(drone);

        // Get all the droneList
        restDroneMockMvc.perform(get("/api/drones?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(drone.getId().intValue())))
            .andExpect(jsonPath("$.[*].serialNumber").value(hasItem(DEFAULT_SERIAL_NUMBER)))
            .andExpect(jsonPath("$.[*].model").value(hasItem(DEFAULT_MODEL.toString())))
            .andExpect(jsonPath("$.[*].weightLimit").value(hasItem(DEFAULT_WEIGHT_LIMIT.doubleValue())))
            .andExpect(jsonPath("$.[*].batteryCapacity").value(hasItem(DEFAULT_BATTERY_CAPACITY.doubleValue())))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())));
    }
    
    @Test
    @Transactional
    public void getDrone() throws Exception {
        // Initialize the database
        droneRepository.saveAndFlush(drone);

        // Get the drone
        restDroneMockMvc.perform(get("/api/drones/{id}", drone.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(drone.getId().intValue()))
            .andExpect(jsonPath("$.serialNumber").value(DEFAULT_SERIAL_NUMBER))
            .andExpect(jsonPath("$.model").value(DEFAULT_MODEL.toString()))
            .andExpect(jsonPath("$.weightLimit").value(DEFAULT_WEIGHT_LIMIT.doubleValue()))
            .andExpect(jsonPath("$.batteryCapacity").value(DEFAULT_BATTERY_CAPACITY.doubleValue()))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingDrone() throws Exception {
        // Get the drone
        restDroneMockMvc.perform(get("/api/drones/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDrone() throws Exception {
        // Initialize the database
        droneRepository.saveAndFlush(drone);

        int databaseSizeBeforeUpdate = droneRepository.findAll().size();

        // Update the drone
        Drone updatedDrone = droneRepository.findById(drone.getId()).get();
        // Disconnect from session so that the updates on updatedDrone are not directly saved in db
        em.detach(updatedDrone);
        updatedDrone
            .serialNumber(UPDATED_SERIAL_NUMBER)
            .model(UPDATED_MODEL)
            .weightLimit(UPDATED_WEIGHT_LIMIT)
            .batteryCapacity(UPDATED_BATTERY_CAPACITY)
            .state(UPDATED_STATE);
        DroneDTO droneDTO = droneMapper.toDto(updatedDrone);

        restDroneMockMvc.perform(put("/api/drones")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(droneDTO)))
            .andExpect(status().isOk());

        // Validate the Drone in the database
        List<Drone> droneList = droneRepository.findAll();
        assertThat(droneList).hasSize(databaseSizeBeforeUpdate);
        Drone testDrone = droneList.get(droneList.size() - 1);
        assertThat(testDrone.getSerialNumber()).isEqualTo(UPDATED_SERIAL_NUMBER);
        assertThat(testDrone.getModel()).isEqualTo(UPDATED_MODEL);
        assertThat(testDrone.getWeightLimit()).isEqualTo(UPDATED_WEIGHT_LIMIT);
        assertThat(testDrone.getBatteryCapacity()).isEqualTo(UPDATED_BATTERY_CAPACITY);
        assertThat(testDrone.getState()).isEqualTo(UPDATED_STATE);
    }

    @Test
    @Transactional
    public void updateNonExistingDrone() throws Exception {
        int databaseSizeBeforeUpdate = droneRepository.findAll().size();

        // Create the Drone
        DroneDTO droneDTO = droneMapper.toDto(drone);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDroneMockMvc.perform(put("/api/drones")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(droneDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Drone in the database
        List<Drone> droneList = droneRepository.findAll();
        assertThat(droneList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDrone() throws Exception {
        // Initialize the database
        droneRepository.saveAndFlush(drone);

        int databaseSizeBeforeDelete = droneRepository.findAll().size();

        // Delete the drone
        restDroneMockMvc.perform(delete("/api/drones/{id}", drone.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Drone> droneList = droneRepository.findAll();
        assertThat(droneList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
