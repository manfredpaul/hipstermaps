package io.github.manfredpaul.hipstermaps.web.rest;

import io.github.manfredpaul.hipstermaps.HipstermapsApp;

import io.github.manfredpaul.hipstermaps.domain.Spatialevent;
import io.github.manfredpaul.hipstermaps.repository.SpatialeventRepository;
import io.github.manfredpaul.hipstermaps.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static io.github.manfredpaul.hipstermaps.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the SpatialeventResource REST controller.
 *
 * @see SpatialeventResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HipstermapsApp.class)
public class SpatialeventResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final byte[] DEFAULT_LOCATION = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_LOCATION = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_LOCATION_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_LOCATION_CONTENT_TYPE = "image/png";

    @Autowired
    private SpatialeventRepository spatialeventRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSpatialeventMockMvc;

    private Spatialevent spatialevent;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SpatialeventResource spatialeventResource = new SpatialeventResource(spatialeventRepository);
        this.restSpatialeventMockMvc = MockMvcBuilders.standaloneSetup(spatialeventResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Spatialevent createEntity(EntityManager em) {
        Spatialevent spatialevent = new Spatialevent()
            .title(DEFAULT_TITLE)
            .date(DEFAULT_DATE)
            .location(DEFAULT_LOCATION)
            .locationContentType(DEFAULT_LOCATION_CONTENT_TYPE);
        return spatialevent;
    }

    @Before
    public void initTest() {
        spatialevent = createEntity(em);
    }

    @Test
    @Transactional
    public void createSpatialevent() throws Exception {
        int databaseSizeBeforeCreate = spatialeventRepository.findAll().size();

        // Create the Spatialevent
        restSpatialeventMockMvc.perform(post("/api/spatialevents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(spatialevent)))
            .andExpect(status().isCreated());

        // Validate the Spatialevent in the database
        List<Spatialevent> spatialeventList = spatialeventRepository.findAll();
        assertThat(spatialeventList).hasSize(databaseSizeBeforeCreate + 1);
        Spatialevent testSpatialevent = spatialeventList.get(spatialeventList.size() - 1);
        assertThat(testSpatialevent.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testSpatialevent.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testSpatialevent.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testSpatialevent.getLocationContentType()).isEqualTo(DEFAULT_LOCATION_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createSpatialeventWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = spatialeventRepository.findAll().size();

        // Create the Spatialevent with an existing ID
        spatialevent.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSpatialeventMockMvc.perform(post("/api/spatialevents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(spatialevent)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Spatialevent> spatialeventList = spatialeventRepository.findAll();
        assertThat(spatialeventList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSpatialevents() throws Exception {
        // Initialize the database
        spatialeventRepository.saveAndFlush(spatialevent);

        // Get all the spatialeventList
        restSpatialeventMockMvc.perform(get("/api/spatialevents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(spatialevent.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(sameInstant(DEFAULT_DATE))))
            .andExpect(jsonPath("$.[*].locationContentType").value(hasItem(DEFAULT_LOCATION_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].location").value(hasItem(Base64Utils.encodeToString(DEFAULT_LOCATION))));
    }

    @Test
    @Transactional
    public void getSpatialevent() throws Exception {
        // Initialize the database
        spatialeventRepository.saveAndFlush(spatialevent);

        // Get the spatialevent
        restSpatialeventMockMvc.perform(get("/api/spatialevents/{id}", spatialevent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(spatialevent.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.date").value(sameInstant(DEFAULT_DATE)))
            .andExpect(jsonPath("$.locationContentType").value(DEFAULT_LOCATION_CONTENT_TYPE))
            .andExpect(jsonPath("$.location").value(Base64Utils.encodeToString(DEFAULT_LOCATION)));
    }

    @Test
    @Transactional
    public void getNonExistingSpatialevent() throws Exception {
        // Get the spatialevent
        restSpatialeventMockMvc.perform(get("/api/spatialevents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSpatialevent() throws Exception {
        // Initialize the database
        spatialeventRepository.saveAndFlush(spatialevent);
        int databaseSizeBeforeUpdate = spatialeventRepository.findAll().size();

        // Update the spatialevent
        Spatialevent updatedSpatialevent = spatialeventRepository.findOne(spatialevent.getId());
        updatedSpatialevent
            .title(UPDATED_TITLE)
            .date(UPDATED_DATE)
            .location(UPDATED_LOCATION)
            .locationContentType(UPDATED_LOCATION_CONTENT_TYPE);

        restSpatialeventMockMvc.perform(put("/api/spatialevents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSpatialevent)))
            .andExpect(status().isOk());

        // Validate the Spatialevent in the database
        List<Spatialevent> spatialeventList = spatialeventRepository.findAll();
        assertThat(spatialeventList).hasSize(databaseSizeBeforeUpdate);
        Spatialevent testSpatialevent = spatialeventList.get(spatialeventList.size() - 1);
        assertThat(testSpatialevent.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testSpatialevent.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testSpatialevent.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testSpatialevent.getLocationContentType()).isEqualTo(UPDATED_LOCATION_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingSpatialevent() throws Exception {
        int databaseSizeBeforeUpdate = spatialeventRepository.findAll().size();

        // Create the Spatialevent

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSpatialeventMockMvc.perform(put("/api/spatialevents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(spatialevent)))
            .andExpect(status().isCreated());

        // Validate the Spatialevent in the database
        List<Spatialevent> spatialeventList = spatialeventRepository.findAll();
        assertThat(spatialeventList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSpatialevent() throws Exception {
        // Initialize the database
        spatialeventRepository.saveAndFlush(spatialevent);
        int databaseSizeBeforeDelete = spatialeventRepository.findAll().size();

        // Get the spatialevent
        restSpatialeventMockMvc.perform(delete("/api/spatialevents/{id}", spatialevent.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Spatialevent> spatialeventList = spatialeventRepository.findAll();
        assertThat(spatialeventList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Spatialevent.class);
        Spatialevent spatialevent1 = new Spatialevent();
        spatialevent1.setId(1L);
        Spatialevent spatialevent2 = new Spatialevent();
        spatialevent2.setId(spatialevent1.getId());
        assertThat(spatialevent1).isEqualTo(spatialevent2);
        spatialevent2.setId(2L);
        assertThat(spatialevent1).isNotEqualTo(spatialevent2);
        spatialevent1.setId(null);
        assertThat(spatialevent1).isNotEqualTo(spatialevent2);
    }
}
