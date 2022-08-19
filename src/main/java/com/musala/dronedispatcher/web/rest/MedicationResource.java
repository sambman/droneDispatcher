package com.musala.dronedispatcher.web.rest;

import com.musala.dronedispatcher.service.MedicationService;
import com.musala.dronedispatcher.web.rest.errors.BadRequestAlertException;
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
 * REST controller for managing {@link com.musala.dronedispatcher.domain.Medication}.
 */
@RestController
@RequestMapping("/api")
public class MedicationResource {

    private final Logger log = LoggerFactory.getLogger(MedicationResource.class);

    private static final String ENTITY_NAME = "droneDispatcherMedication";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MedicationService medicationService;

    public MedicationResource(MedicationService medicationService) {
        this.medicationService = medicationService;
    }

    /**
     * {@code POST  /medications} : Create a new medication.
     *
     * @param medicationDTO the medicationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new medicationDTO, or with status {@code 400 (Bad Request)} if the medication has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/medications")
    public ResponseEntity<MedicationDTO> createMedication(@Valid @RequestBody MedicationDTO medicationDTO) throws URISyntaxException {
        log.debug("REST request to save Medication : {}", medicationDTO);
        if (medicationDTO.getId() != null) {
            throw new BadRequestAlertException("A new medication cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MedicationDTO result = medicationService.save(medicationDTO);
        return ResponseEntity.created(new URI("/api/medications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /medications} : Updates an existing medication.
     *
     * @param medicationDTO the medicationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated medicationDTO,
     * or with status {@code 400 (Bad Request)} if the medicationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the medicationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/medications")
    public ResponseEntity<MedicationDTO> updateMedication(@Valid @RequestBody MedicationDTO medicationDTO) throws URISyntaxException {
        log.debug("REST request to update Medication : {}", medicationDTO);
        if (medicationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MedicationDTO result = medicationService.save(medicationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, medicationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /medications} : get all the medications.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of medications in body.
     */
    @GetMapping("/medications")
    public ResponseEntity<List<MedicationDTO>> getAllMedications(Pageable pageable) {
        log.debug("REST request to get a page of Medications");
        Page<MedicationDTO> page = medicationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /medications/:id} : get the "id" medication.
     *
     * @param id the id of the medicationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the medicationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/medications/{id}")
    public ResponseEntity<MedicationDTO> getMedication(@PathVariable Long id) {
        log.debug("REST request to get Medication : {}", id);
        Optional<MedicationDTO> medicationDTO = medicationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(medicationDTO);
    }

    /**
     * {@code DELETE  /medications/:id} : delete the "id" medication.
     *
     * @param id the id of the medicationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/medications/{id}")
    public ResponseEntity<Void> deleteMedication(@PathVariable Long id) {
        log.debug("REST request to delete Medication : {}", id);
        medicationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
