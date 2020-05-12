package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.TestProdDatabaseApp;
import com.mycompany.myapp.domain.RegnCanc;
import com.mycompany.myapp.repository.RegnCancRepository;
import com.mycompany.myapp.service.RegnCancService;
import com.mycompany.myapp.service.dto.RegnCancDTO;
import com.mycompany.myapp.service.mapper.RegnCancMapper;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.Validator;


import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.mycompany.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link RegnCancResource} REST controller.
 */
@SpringBootTest(classes = TestProdDatabaseApp.class)
public class RegnCancResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESC_STRING = "AAAAAAAAAA";
    private static final String UPDATED_DESC_STRING = "BBBBBBBBBB";

    private static final String DEFAULT_ORG_INFO = "AAAAAAAAAA";
    private static final String UPDATED_ORG_INFO = "BBBBBBBBBB";

    private static final String DEFAULT_CANCELLATION_WAY = "AAAAAAAAAA";
    private static final String UPDATED_CANCELLATION_WAY = "BBBBBBBBBB";

    private static final String DEFAULT_CANCELLATION_REASON = "AAAAAAAAAA";
    private static final String UPDATED_CANCELLATION_REASON = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CANCELLATION_TIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CANCELLATION_TIME = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CANCELLATION_PROOF = "AAAAAAAAAA";
    private static final String UPDATED_CANCELLATION_PROOF = "BBBBBBBBBB";

    private static final String DEFAULT_REMARKS = "AAAAAAAAAA";
    private static final String UPDATED_REMARKS = "BBBBBBBBBB";

    @Autowired
    private RegnCancRepository regnCancRepository;

    @Autowired
    private RegnCancMapper regnCancMapper;

    @Autowired
    private RegnCancService regnCancService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restRegnCancMockMvc;

    private RegnCanc regnCanc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RegnCancResource regnCancResource = new RegnCancResource(regnCancService);
        this.restRegnCancMockMvc = MockMvcBuilders.standaloneSetup(regnCancResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RegnCanc createEntity() {
        RegnCanc regnCanc = new RegnCanc()
            .name(DEFAULT_NAME)
            .descString(DEFAULT_DESC_STRING)
            .orgInfo(DEFAULT_ORG_INFO)
            .cancellationWay(DEFAULT_CANCELLATION_WAY)
            .cancellationReason(DEFAULT_CANCELLATION_REASON)
            .cancellationTime(DEFAULT_CANCELLATION_TIME)
            .cancellationProof(DEFAULT_CANCELLATION_PROOF)
            .remarks(DEFAULT_REMARKS);
        return regnCanc;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RegnCanc createUpdatedEntity() {
        RegnCanc regnCanc = new RegnCanc()
            .name(UPDATED_NAME)
            .descString(UPDATED_DESC_STRING)
            .orgInfo(UPDATED_ORG_INFO)
            .cancellationWay(UPDATED_CANCELLATION_WAY)
            .cancellationReason(UPDATED_CANCELLATION_REASON)
            .cancellationTime(UPDATED_CANCELLATION_TIME)
            .cancellationProof(UPDATED_CANCELLATION_PROOF)
            .remarks(UPDATED_REMARKS);
        return regnCanc;
    }

    @BeforeEach
    public void initTest() {
        regnCancRepository.deleteAll();
        regnCanc = createEntity();
    }

    @Test
    public void createRegnCanc() throws Exception {
        int databaseSizeBeforeCreate = regnCancRepository.findAll().size();

        // Create the RegnCanc
        RegnCancDTO regnCancDTO = regnCancMapper.toDto(regnCanc);
        restRegnCancMockMvc.perform(post("/api/regn-cancs")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(regnCancDTO)))
            .andExpect(status().isCreated());

        // Validate the RegnCanc in the database
        List<RegnCanc> regnCancList = regnCancRepository.findAll();
        assertThat(regnCancList).hasSize(databaseSizeBeforeCreate + 1);
        RegnCanc testRegnCanc = regnCancList.get(regnCancList.size() - 1);
        assertThat(testRegnCanc.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRegnCanc.getDescString()).isEqualTo(DEFAULT_DESC_STRING);
        assertThat(testRegnCanc.getOrgInfo()).isEqualTo(DEFAULT_ORG_INFO);
        assertThat(testRegnCanc.getCancellationWay()).isEqualTo(DEFAULT_CANCELLATION_WAY);
        assertThat(testRegnCanc.getCancellationReason()).isEqualTo(DEFAULT_CANCELLATION_REASON);
        assertThat(testRegnCanc.getCancellationTime()).isEqualTo(DEFAULT_CANCELLATION_TIME);
        assertThat(testRegnCanc.getCancellationProof()).isEqualTo(DEFAULT_CANCELLATION_PROOF);
        assertThat(testRegnCanc.getRemarks()).isEqualTo(DEFAULT_REMARKS);
    }

    @Test
    public void createRegnCancWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = regnCancRepository.findAll().size();

        // Create the RegnCanc with an existing ID
        regnCanc.setId("existing_id");
        RegnCancDTO regnCancDTO = regnCancMapper.toDto(regnCanc);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRegnCancMockMvc.perform(post("/api/regn-cancs")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(regnCancDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RegnCanc in the database
        List<RegnCanc> regnCancList = regnCancRepository.findAll();
        assertThat(regnCancList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = regnCancRepository.findAll().size();
        // set the field null
        regnCanc.setName(null);

        // Create the RegnCanc, which fails.
        RegnCancDTO regnCancDTO = regnCancMapper.toDto(regnCanc);

        restRegnCancMockMvc.perform(post("/api/regn-cancs")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(regnCancDTO)))
            .andExpect(status().isBadRequest());

        List<RegnCanc> regnCancList = regnCancRepository.findAll();
        assertThat(regnCancList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllRegnCancs() throws Exception {
        // Initialize the database
        regnCancRepository.save(regnCanc);

        // Get all the regnCancList
        restRegnCancMockMvc.perform(get("/api/regn-cancs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(regnCanc.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].descString").value(hasItem(DEFAULT_DESC_STRING)))
            .andExpect(jsonPath("$.[*].orgInfo").value(hasItem(DEFAULT_ORG_INFO)))
            .andExpect(jsonPath("$.[*].cancellationWay").value(hasItem(DEFAULT_CANCELLATION_WAY)))
            .andExpect(jsonPath("$.[*].cancellationReason").value(hasItem(DEFAULT_CANCELLATION_REASON)))
            .andExpect(jsonPath("$.[*].cancellationTime").value(hasItem(DEFAULT_CANCELLATION_TIME.toString())))
            .andExpect(jsonPath("$.[*].cancellationProof").value(hasItem(DEFAULT_CANCELLATION_PROOF)))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS)));
    }
    
    @Test
    public void getRegnCanc() throws Exception {
        // Initialize the database
        regnCancRepository.save(regnCanc);

        // Get the regnCanc
        restRegnCancMockMvc.perform(get("/api/regn-cancs/{id}", regnCanc.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(regnCanc.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.descString").value(DEFAULT_DESC_STRING))
            .andExpect(jsonPath("$.orgInfo").value(DEFAULT_ORG_INFO))
            .andExpect(jsonPath("$.cancellationWay").value(DEFAULT_CANCELLATION_WAY))
            .andExpect(jsonPath("$.cancellationReason").value(DEFAULT_CANCELLATION_REASON))
            .andExpect(jsonPath("$.cancellationTime").value(DEFAULT_CANCELLATION_TIME.toString()))
            .andExpect(jsonPath("$.cancellationProof").value(DEFAULT_CANCELLATION_PROOF))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS));
    }

    @Test
    public void getNonExistingRegnCanc() throws Exception {
        // Get the regnCanc
        restRegnCancMockMvc.perform(get("/api/regn-cancs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateRegnCanc() throws Exception {
        // Initialize the database
        regnCancRepository.save(regnCanc);

        int databaseSizeBeforeUpdate = regnCancRepository.findAll().size();

        // Update the regnCanc
        RegnCanc updatedRegnCanc = regnCancRepository.findById(regnCanc.getId()).get();
        updatedRegnCanc
            .name(UPDATED_NAME)
            .descString(UPDATED_DESC_STRING)
            .orgInfo(UPDATED_ORG_INFO)
            .cancellationWay(UPDATED_CANCELLATION_WAY)
            .cancellationReason(UPDATED_CANCELLATION_REASON)
            .cancellationTime(UPDATED_CANCELLATION_TIME)
            .cancellationProof(UPDATED_CANCELLATION_PROOF)
            .remarks(UPDATED_REMARKS);
        RegnCancDTO regnCancDTO = regnCancMapper.toDto(updatedRegnCanc);

        restRegnCancMockMvc.perform(put("/api/regn-cancs")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(regnCancDTO)))
            .andExpect(status().isOk());

        // Validate the RegnCanc in the database
        List<RegnCanc> regnCancList = regnCancRepository.findAll();
        assertThat(regnCancList).hasSize(databaseSizeBeforeUpdate);
        RegnCanc testRegnCanc = regnCancList.get(regnCancList.size() - 1);
        assertThat(testRegnCanc.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRegnCanc.getDescString()).isEqualTo(UPDATED_DESC_STRING);
        assertThat(testRegnCanc.getOrgInfo()).isEqualTo(UPDATED_ORG_INFO);
        assertThat(testRegnCanc.getCancellationWay()).isEqualTo(UPDATED_CANCELLATION_WAY);
        assertThat(testRegnCanc.getCancellationReason()).isEqualTo(UPDATED_CANCELLATION_REASON);
        assertThat(testRegnCanc.getCancellationTime()).isEqualTo(UPDATED_CANCELLATION_TIME);
        assertThat(testRegnCanc.getCancellationProof()).isEqualTo(UPDATED_CANCELLATION_PROOF);
        assertThat(testRegnCanc.getRemarks()).isEqualTo(UPDATED_REMARKS);
    }

    @Test
    public void updateNonExistingRegnCanc() throws Exception {
        int databaseSizeBeforeUpdate = regnCancRepository.findAll().size();

        // Create the RegnCanc
        RegnCancDTO regnCancDTO = regnCancMapper.toDto(regnCanc);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRegnCancMockMvc.perform(put("/api/regn-cancs")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(regnCancDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RegnCanc in the database
        List<RegnCanc> regnCancList = regnCancRepository.findAll();
        assertThat(regnCancList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteRegnCanc() throws Exception {
        // Initialize the database
        regnCancRepository.save(regnCanc);

        int databaseSizeBeforeDelete = regnCancRepository.findAll().size();

        // Delete the regnCanc
        restRegnCancMockMvc.perform(delete("/api/regn-cancs/{id}", regnCanc.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RegnCanc> regnCancList = regnCancRepository.findAll();
        assertThat(regnCancList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
