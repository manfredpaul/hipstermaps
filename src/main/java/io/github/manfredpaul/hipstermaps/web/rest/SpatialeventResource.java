package io.github.manfredpaul.hipstermaps.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.manfredpaul.hipstermaps.domain.Spatialevent;

import io.github.manfredpaul.hipstermaps.repository.SpatialeventRepository;
import io.github.manfredpaul.hipstermaps.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Spatialevent.
 */
@RestController
@RequestMapping("/api")
public class SpatialeventResource {

    private final Logger log = LoggerFactory.getLogger(SpatialeventResource.class);

    private static final String ENTITY_NAME = "spatialevent";

    private final SpatialeventRepository spatialeventRepository;

    public SpatialeventResource(SpatialeventRepository spatialeventRepository) {
        this.spatialeventRepository = spatialeventRepository;
    }

    /**
     * POST  /spatialevents : Create a new spatialevent.
     *
     * @param spatialevent the spatialevent to create
     * @return the ResponseEntity with status 201 (Created) and with body the new spatialevent, or with status 400 (Bad Request) if the spatialevent has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/spatialevents")
    @Timed
    public ResponseEntity<Spatialevent> createSpatialevent(@RequestBody Spatialevent spatialevent) throws URISyntaxException {
        log.debug("REST request to save Spatialevent : {}", spatialevent);
        if (spatialevent.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new spatialevent cannot already have an ID")).body(null);
        }
        Spatialevent result = spatialeventRepository.save(spatialevent);
        return ResponseEntity.created(new URI("/api/spatialevents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /spatialevents : Updates an existing spatialevent.
     *
     * @param spatialevent the spatialevent to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated spatialevent,
     * or with status 400 (Bad Request) if the spatialevent is not valid,
     * or with status 500 (Internal Server Error) if the spatialevent couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/spatialevents")
    @Timed
    public ResponseEntity<Spatialevent> updateSpatialevent(@RequestBody Spatialevent spatialevent) throws URISyntaxException {
        log.debug("REST request to update Spatialevent : {}", spatialevent);
        if (spatialevent.getId() == null) {
            return createSpatialevent(spatialevent);
        }
        Spatialevent result = spatialeventRepository.save(spatialevent);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, spatialevent.getId().toString()))
            .body(result);
    }

    /**
     * GET  /spatialevents : get all the spatialevents.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of spatialevents in body
     */
    @GetMapping("/spatialevents")
    @Timed
    public List<Spatialevent> getAllSpatialevents() {
        log.debug("REST request to get all Spatialevents");
        return spatialeventRepository.findAll();
    }

    /**
     * GET  /spatialevents/:id : get the "id" spatialevent.
     *
     * @param id the id of the spatialevent to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the spatialevent, or with status 404 (Not Found)
     */
    @GetMapping("/spatialevents/{id}")
    @Timed
    public ResponseEntity<Spatialevent> getSpatialevent(@PathVariable Long id) {
        log.debug("REST request to get Spatialevent : {}", id);
        Spatialevent spatialevent = spatialeventRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(spatialevent));
    }

    /**
     * DELETE  /spatialevents/:id : delete the "id" spatialevent.
     *
     * @param id the id of the spatialevent to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/spatialevents/{id}")
    @Timed
    public ResponseEntity<Void> deleteSpatialevent(@PathVariable Long id) {
        log.debug("REST request to delete Spatialevent : {}", id);
        spatialeventRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
