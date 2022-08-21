package com.musala.dronedispatcher.web.rest;

import com.musala.dronedispatcher.domain.enumeration.StateType;
import com.musala.dronedispatcher.service.DroneService;
import com.musala.dronedispatcher.service.DroneToMedicationsService;
import com.musala.dronedispatcher.service.MedicationService;
import com.musala.dronedispatcher.web.rest.errors.BadRequestAlertException;
import com.musala.dronedispatcher.service.dto.DroneDTO;
import com.musala.dronedispatcher.service.dto.DroneToMedicationsDTO;
import com.musala.dronedispatcher.service.dto.MedicationDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.musala.dronedispatcher.domain.DroneToMedications}.
 */
@RestController
@RequestMapping("/api")
public class DroneToMedicationsResource {

    private final Logger log = LoggerFactory.getLogger(DroneToMedicationsResource.class);

    private static final String ENTITY_NAME = "droneDispatcherDroneToMedications";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DroneToMedicationsService droneToMedicationsService;
    private final DroneService droneService;
    private final MedicationService medicationService;

    public DroneToMedicationsResource(DroneToMedicationsService droneToMedicationsService, DroneService droneService, MedicationService medicationService) {
        this.droneToMedicationsService = droneToMedicationsService;
        this.droneService = droneService;
        this.medicationService = medicationService;
    }

    /**
     * {@code POST  /drone-to-medications} : Create a new droneToMedications: loading a drone with medication items.
     *
     * @param droneToMedicationsDTO the droneToMedicationsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new droneToMedicationsDTO, or with status {@code 400 (Bad Request)} if the droneToMedications has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/drone-to-medications")
    public ResponseEntity<DroneToMedicationsDTO> createDroneToMedications(@Valid @RequestBody DroneToMedicationsDTO droneToMedicationsDTO) throws URISyntaxException {
        log.debug("REST request to save DroneToMedications : {}", droneToMedicationsDTO);
        if (droneToMedicationsDTO.getId() != null) {
            throw new BadRequestAlertException("A new droneToMedications cannot already have an ID", ENTITY_NAME, "idexists");
        }
        // Check if drone exists
        Optional<DroneDTO> optionalDroneDTO = droneService.findOne(droneToMedicationsDTO.getDroneId());
        if (!optionalDroneDTO.isPresent()) {
            throw new BadRequestAlertException("Cannot find drone with ID: " + droneToMedicationsDTO.getDroneId(), ENTITY_NAME, "idexists");
        }
        DroneDTO droneDTO = optionalDroneDTO.get();
        // Check if drone is waiting for loads
        if (!droneDTO.getState().equals(StateType.IDLE)) {
            throw new BadRequestAlertException("Cannot load drone which is on state: " + droneDTO.getState(), ENTITY_NAME, "notloadableid");
        }
        // Check if drone have more than 5% battery
        if (droneDTO.getBatteryCapacity() < 25) {
            throw new BadRequestAlertException("Cannot load drone with less battery precentage: " + droneDTO.getBatteryCapacity(), ENTITY_NAME, "lessbatterycapacity");
        }

        // Check if medications total weight is not greater that drone's max capacity
        if (droneToMedicationsDTO.getMedications() == null || droneToMedicationsDTO.getMedications().size() == 0) {
            throw new BadRequestAlertException("Cannot load drone with no medication(s).", ENTITY_NAME, "nomedications");
        }
        // Chech if any medication is on system and no already loaded
        Float totalWeight = droneToMedicationsDTO.getMedications()
            .stream().map(medication -> medication.getWeight())
            .reduce(0F, (subTotal, element) -> subTotal + element);
        if (droneDTO.getWeightLimit() < totalWeight) {
            throw new BadRequestAlertException("Cannot load drone with more than weigth limit: ["
                + droneDTO.getWeightLimit() + "], medication(s) total weight: ["
                + totalWeight + "]" , ENTITY_NAME, "gtweightlimit");
        }
            
        DroneToMedicationsDTO result = droneToMedicationsService.save(droneToMedicationsDTO);
        for (MedicationDTO medication : droneToMedicationsDTO.getMedications()) {
            medication.setDroneToMedicationsId(result.getId());
            medicationService.save(medication);
        }
        droneDTO.setState(StateType.LOADED);
        droneService.save(droneDTO);
        return ResponseEntity.created(new URI("/api/drone-to-medications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /drone-to-medications} : Updates an existing droneToMedications.
     *
     * @param droneToMedicationsDTO the droneToMedicationsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated droneToMedicationsDTO,
     * or with status {@code 400 (Bad Request)} if the droneToMedicationsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the droneToMedicationsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/drone-to-medications")
    public ResponseEntity<DroneToMedicationsDTO> updateDroneToMedications(@Valid @RequestBody DroneToMedicationsDTO droneToMedicationsDTO) throws URISyntaxException {
        log.debug("REST request to update DroneToMedications : {}", droneToMedicationsDTO);
        if (droneToMedicationsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DroneToMedicationsDTO result = droneToMedicationsService.save(droneToMedicationsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, droneToMedicationsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /drone-to-medications} : get all the droneToMedications.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of droneToMedications in body.
     */
    @GetMapping("/drone-to-medications")
    public ResponseEntity<List<DroneToMedicationsDTO>> getAllDroneToMedications(Pageable pageable) {
        log.debug("REST request to get a page of DroneToMedications");
        Page<DroneToMedicationsDTO> page = droneToMedicationsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /drone-to-medications/:id} : get the "id" droneToMedications.
     *
     * @param id the id of the droneToMedicationsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the droneToMedicationsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/drone-to-medications/{id}")
    public ResponseEntity<DroneToMedicationsDTO> getDroneToMedications(@PathVariable Long id) {
        log.debug("REST request to get DroneToMedications : {}", id);
        Optional<DroneToMedicationsDTO> droneToMedicationsDTO = droneToMedicationsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(droneToMedicationsDTO);
    }

    /**
     * {@code DELETE  /drone-to-medications/:id} : delete the "id" droneToMedications.
     *
     * @param id the id of the droneToMedicationsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/drone-to-medications/{id}")
    public ResponseEntity<Void> deleteDroneToMedications(@PathVariable Long id) {
        log.debug("REST request to delete DroneToMedications : {}", id);
        droneToMedicationsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
