package com.musala.dronedispatcher.web.rest;

import com.musala.dronedispatcher.domain.enumeration.StateType;
import com.musala.dronedispatcher.service.DroneService;
import com.musala.dronedispatcher.web.rest.errors.BadRequestAlertException;
import com.musala.dronedispatcher.service.dto.DroneDTO;

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
import java.util.stream.StreamSupport;

/**
 * REST controller for managing {@link com.musala.dronedispatcher.domain.Drone}.
 */
@RestController
@RequestMapping("/api")
public class DroneResource {

    private final Logger log = LoggerFactory.getLogger(DroneResource.class);

    private static final String ENTITY_NAME = "droneDispatcherDrone";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DroneService droneService;

    public DroneResource(DroneService droneService) {
        this.droneService = droneService;
    }

    /**
     * {@code POST  /drones} : Create a new drone: drone registration.
     *
     * @param droneDTO the droneDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new droneDTO, or with status {@code 400 (Bad Request)} if the drone has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/drones")
    public ResponseEntity<DroneDTO> createDrone(@Valid @RequestBody DroneDTO droneDTO) throws URISyntaxException {
        log.debug("REST request to save Drone : {}", droneDTO);
        if (droneDTO.getId() != null) {
            throw new BadRequestAlertException("A new drone cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if (droneService.findAllWithoutPagination().size() == 10) {
            throw new BadRequestAlertException("Cannot register more than 10 drones", ENTITY_NAME, "nb10Drones");
        }
        droneDTO.setState(StateType.IDLE); // new drone is on IDLE state
        DroneDTO result = droneService.save(droneDTO);
        return ResponseEntity.created(new URI("/api/drones/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /drones} : Updates an existing drone.
     *
     * @param droneDTO the droneDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated droneDTO,
     * or with status {@code 400 (Bad Request)} if the droneDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the droneDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/drones")
    public ResponseEntity<DroneDTO> updateDrone(@Valid @RequestBody DroneDTO droneDTO) throws URISyntaxException {
        log.debug("REST request to update Drone : {}", droneDTO);
        if (droneDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DroneDTO result = droneService.save(droneDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, droneDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /drones} : get all the drones.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of drones in body.
     */
    @GetMapping("/drones")
    public ResponseEntity<List<DroneDTO>> getAllDrones(Pageable pageable) {
        log.debug("REST request to get a page of Drones");
        Page<DroneDTO> page = droneService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /drones/:id} : get the "id" drone.
     *
     * @param id the id of the droneDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the droneDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/drones/{id}")
    public ResponseEntity<DroneDTO> getDrone(@PathVariable Long id) {
        log.debug("REST request to get Drone : {}", id);
        Optional<DroneDTO> droneDTO = droneService.findOne(id);
        return ResponseUtil.wrapOrNotFound(droneDTO);
    }

    /**
     * {@code DELETE  /drones/:id} : delete the "id" drone.
     *
     * @param id the id of the droneDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/drones/{id}")
    public ResponseEntity<Void> deleteDrone(@PathVariable Long id) {
        log.debug("REST request to delete Drone : {}", id);
        droneService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code GET  /drones/available} : get available drones.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of available drones in body.
     */
    @GetMapping("/drones/available")
    public ResponseEntity<List<DroneDTO>> getAvailableDrones() {
        log.debug("REST request to get a page of Drones");
        List<DroneDTO> drones = droneService.getAllAvailableDrones();
        return ResponseEntity.ok().body(drones);
    }
}
