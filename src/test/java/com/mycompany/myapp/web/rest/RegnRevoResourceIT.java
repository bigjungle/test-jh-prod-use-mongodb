package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.TestProdDatabaseApp;
import com.mycompany.myapp.domain.RegnRevo;
import com.mycompany.myapp.repository.RegnRevoRepository;
import com.mycompany.myapp.service.RegnRevoService;
import com.mycompany.myapp.service.dto.RegnRevoDTO;
import com.mycompany.myapp.service.mapper.RegnRevoMapper;
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
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.mycompany.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link RegnRevoResource} REST controller.
 */
@SpringBootTest(classes = TestProdDatabaseApp.class)
public class RegnRevoResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESC_STRING = "AAAAAAAAAA";
    private static final String UPDATED_DESC_STRING = "BBBBBBBBBB";

    private static final String DEFAULT_ORG_INFO = "AAAAAAAAAA";
    private static final String UPDATED_ORG_INFO = "BBBBBBBBBB";

    private static final Integer DEFAULT_REVOKE_TIME_SPAN = 1;
    private static final Integer UPDATED_REVOKE_TIME_SPAN = 2;

    private static final LocalDate DEFAULT_REVOKE_START = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_REVOKE_START = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_REVOKE_OVER = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_REVOKE_OVER = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_PUNISH_ORG = "AAAAAAAAAA";
    private static final String UPDATED_PUNISH_ORG = "BBBBBBBBBB";

    private static final Instant DEFAULT_PUNISH_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_PUNISH_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_FACTS = "AAAAAAAAAA";
    private static final String UPDATED_FACTS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_AUTO_PROCESS = false;
    private static final Boolean UPDATED_AUTO_PROCESS = true;

    private static final String DEFAULT_REVOKE_PROOF = "AAAAAAAAAA";
    private static final String UPDATED_REVOKE_PROOF = "BBBBBBBBBB";

    private static final String DEFAULT_REMARKS = "AAAAAAAAAA";
    private static final String UPDATED_REMARKS = "BBBBBBBBBB";

    @Autowired
    private RegnRevoRepository regnRevoRepository;

    @Autowired
    private RegnRevoMapper regnRevoMapper;

    @Autowired
    private RegnRevoService regnRevoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restRegnRevoMockMvc;

    private RegnRevo regnRevo;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RegnRevoResource regnRevoResource = new RegnRevoResource(regnRevoService);
        this.restRegnRevoMockMvc = MockMvcBuilders.standaloneSetup(regnRevoResource)
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
    public static RegnRevo createEntity() {
        RegnRevo regnRevo = new RegnRevo()
            .name(DEFAULT_NAME)
            .descString(DEFAULT_DESC_STRING)
            .orgInfo(DEFAULT_ORG_INFO)
            .revokeTimeSpan(DEFAULT_REVOKE_TIME_SPAN)
            .revokeStart(DEFAULT_REVOKE_START)
            .revokeOver(DEFAULT_REVOKE_OVER)
            .punishOrg(DEFAULT_PUNISH_ORG)
            .punishTime(DEFAULT_PUNISH_TIME)
            .facts(DEFAULT_FACTS)
            .autoProcess(DEFAULT_AUTO_PROCESS)
            .revokeProof(DEFAULT_REVOKE_PROOF)
            .remarks(DEFAULT_REMARKS);
        return regnRevo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RegnRevo createUpdatedEntity() {
        RegnRevo regnRevo = new RegnRevo()
            .name(UPDATED_NAME)
            .descString(UPDATED_DESC_STRING)
            .orgInfo(UPDATED_ORG_INFO)
            .revokeTimeSpan(UPDATED_REVOKE_TIME_SPAN)
            .revokeStart(UPDATED_REVOKE_START)
            .revokeOver(UPDATED_REVOKE_OVER)
            .punishOrg(UPDATED_PUNISH_ORG)
            .punishTime(UPDATED_PUNISH_TIME)
            .facts(UPDATED_FACTS)
            .autoProcess(UPDATED_AUTO_PROCESS)
            .revokeProof(UPDATED_REVOKE_PROOF)
            .remarks(UPDATED_REMARKS);
        return regnRevo;
    }

    @BeforeEach
    public void initTest() {
        regnRevoRepository.deleteAll();
        regnRevo = createEntity();
    }

    @Test
    public void createRegnRevo() throws Exception {
        int databaseSizeBeforeCreate = regnRevoRepository.findAll().size();

        // Create the RegnRevo
        RegnRevoDTO regnRevoDTO = regnRevoMapper.toDto(regnRevo);
        restRegnRevoMockMvc.perform(post("/api/regn-revos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(regnRevoDTO)))
            .andExpect(status().isCreated());

        // Validate the RegnRevo in the database
        List<RegnRevo> regnRevoList = regnRevoRepository.findAll();
        assertThat(regnRevoList).hasSize(databaseSizeBeforeCreate + 1);
        RegnRevo testRegnRevo = regnRevoList.get(regnRevoList.size() - 1);
        assertThat(testRegnRevo.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRegnRevo.getDescString()).isEqualTo(DEFAULT_DESC_STRING);
        assertThat(testRegnRevo.getOrgInfo()).isEqualTo(DEFAULT_ORG_INFO);
        assertThat(testRegnRevo.getRevokeTimeSpan()).isEqualTo(DEFAULT_REVOKE_TIME_SPAN);
        assertThat(testRegnRevo.getRevokeStart()).isEqualTo(DEFAULT_REVOKE_START);
        assertThat(testRegnRevo.getRevokeOver()).isEqualTo(DEFAULT_REVOKE_OVER);
        assertThat(testRegnRevo.getPunishOrg()).isEqualTo(DEFAULT_PUNISH_ORG);
        assertThat(testRegnRevo.getPunishTime()).isEqualTo(DEFAULT_PUNISH_TIME);
        assertThat(testRegnRevo.getFacts()).isEqualTo(DEFAULT_FACTS);
        assertThat(testRegnRevo.isAutoProcess()).isEqualTo(DEFAULT_AUTO_PROCESS);
        assertThat(testRegnRevo.getRevokeProof()).isEqualTo(DEFAULT_REVOKE_PROOF);
        assertThat(testRegnRevo.getRemarks()).isEqualTo(DEFAULT_REMARKS);
    }

    @Test
    public void createRegnRevoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = regnRevoRepository.findAll().size();

        // Create the RegnRevo with an existing ID
        regnRevo.setId("existing_id");
        RegnRevoDTO regnRevoDTO = regnRevoMapper.toDto(regnRevo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRegnRevoMockMvc.perform(post("/api/regn-revos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(regnRevoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RegnRevo in the database
        List<RegnRevo> regnRevoList = regnRevoRepository.findAll();
        assertThat(regnRevoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = regnRevoRepository.findAll().size();
        // set the field null
        regnRevo.setName(null);

        // Create the RegnRevo, which fails.
        RegnRevoDTO regnRevoDTO = regnRevoMapper.toDto(regnRevo);

        restRegnRevoMockMvc.perform(post("/api/regn-revos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(regnRevoDTO)))
            .andExpect(status().isBadRequest());

        List<RegnRevo> regnRevoList = regnRevoRepository.findAll();
        assertThat(regnRevoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkOrgInfoIsRequired() throws Exception {
        int databaseSizeBeforeTest = regnRevoRepository.findAll().size();
        // set the field null
        regnRevo.setOrgInfo(null);

        // Create the RegnRevo, which fails.
        RegnRevoDTO regnRevoDTO = regnRevoMapper.toDto(regnRevo);

        restRegnRevoMockMvc.perform(post("/api/regn-revos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(regnRevoDTO)))
            .andExpect(status().isBadRequest());

        List<RegnRevo> regnRevoList = regnRevoRepository.findAll();
        assertThat(regnRevoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkRevokeTimeSpanIsRequired() throws Exception {
        int databaseSizeBeforeTest = regnRevoRepository.findAll().size();
        // set the field null
        regnRevo.setRevokeTimeSpan(null);

        // Create the RegnRevo, which fails.
        RegnRevoDTO regnRevoDTO = regnRevoMapper.toDto(regnRevo);

        restRegnRevoMockMvc.perform(post("/api/regn-revos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(regnRevoDTO)))
            .andExpect(status().isBadRequest());

        List<RegnRevo> regnRevoList = regnRevoRepository.findAll();
        assertThat(regnRevoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkRevokeStartIsRequired() throws Exception {
        int databaseSizeBeforeTest = regnRevoRepository.findAll().size();
        // set the field null
        regnRevo.setRevokeStart(null);

        // Create the RegnRevo, which fails.
        RegnRevoDTO regnRevoDTO = regnRevoMapper.toDto(regnRevo);

        restRegnRevoMockMvc.perform(post("/api/regn-revos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(regnRevoDTO)))
            .andExpect(status().isBadRequest());

        List<RegnRevo> regnRevoList = regnRevoRepository.findAll();
        assertThat(regnRevoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkRevokeOverIsRequired() throws Exception {
        int databaseSizeBeforeTest = regnRevoRepository.findAll().size();
        // set the field null
        regnRevo.setRevokeOver(null);

        // Create the RegnRevo, which fails.
        RegnRevoDTO regnRevoDTO = regnRevoMapper.toDto(regnRevo);

        restRegnRevoMockMvc.perform(post("/api/regn-revos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(regnRevoDTO)))
            .andExpect(status().isBadRequest());

        List<RegnRevo> regnRevoList = regnRevoRepository.findAll();
        assertThat(regnRevoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllRegnRevos() throws Exception {
        // Initialize the database
        regnRevoRepository.save(regnRevo);

        // Get all the regnRevoList
        restRegnRevoMockMvc.perform(get("/api/regn-revos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(regnRevo.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].descString").value(hasItem(DEFAULT_DESC_STRING)))
            .andExpect(jsonPath("$.[*].orgInfo").value(hasItem(DEFAULT_ORG_INFO)))
            .andExpect(jsonPath("$.[*].revokeTimeSpan").value(hasItem(DEFAULT_REVOKE_TIME_SPAN)))
            .andExpect(jsonPath("$.[*].revokeStart").value(hasItem(DEFAULT_REVOKE_START.toString())))
            .andExpect(jsonPath("$.[*].revokeOver").value(hasItem(DEFAULT_REVOKE_OVER.toString())))
            .andExpect(jsonPath("$.[*].punishOrg").value(hasItem(DEFAULT_PUNISH_ORG)))
            .andExpect(jsonPath("$.[*].punishTime").value(hasItem(DEFAULT_PUNISH_TIME.toString())))
            .andExpect(jsonPath("$.[*].facts").value(hasItem(DEFAULT_FACTS)))
            .andExpect(jsonPath("$.[*].autoProcess").value(hasItem(DEFAULT_AUTO_PROCESS.booleanValue())))
            .andExpect(jsonPath("$.[*].revokeProof").value(hasItem(DEFAULT_REVOKE_PROOF)))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS)));
    }
    
    @Test
    public void getRegnRevo() throws Exception {
        // Initialize the database
        regnRevoRepository.save(regnRevo);

        // Get the regnRevo
        restRegnRevoMockMvc.perform(get("/api/regn-revos/{id}", regnRevo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(regnRevo.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.descString").value(DEFAULT_DESC_STRING))
            .andExpect(jsonPath("$.orgInfo").value(DEFAULT_ORG_INFO))
            .andExpect(jsonPath("$.revokeTimeSpan").value(DEFAULT_REVOKE_TIME_SPAN))
            .andExpect(jsonPath("$.revokeStart").value(DEFAULT_REVOKE_START.toString()))
            .andExpect(jsonPath("$.revokeOver").value(DEFAULT_REVOKE_OVER.toString()))
            .andExpect(jsonPath("$.punishOrg").value(DEFAULT_PUNISH_ORG))
            .andExpect(jsonPath("$.punishTime").value(DEFAULT_PUNISH_TIME.toString()))
            .andExpect(jsonPath("$.facts").value(DEFAULT_FACTS))
            .andExpect(jsonPath("$.autoProcess").value(DEFAULT_AUTO_PROCESS.booleanValue()))
            .andExpect(jsonPath("$.revokeProof").value(DEFAULT_REVOKE_PROOF))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS));
    }

    @Test
    public void getNonExistingRegnRevo() throws Exception {
        // Get the regnRevo
        restRegnRevoMockMvc.perform(get("/api/regn-revos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateRegnRevo() throws Exception {
        // Initialize the database
        regnRevoRepository.save(regnRevo);

        int databaseSizeBeforeUpdate = regnRevoRepository.findAll().size();

        // Update the regnRevo
        RegnRevo updatedRegnRevo = regnRevoRepository.findById(regnRevo.getId()).get();
        updatedRegnRevo
            .name(UPDATED_NAME)
            .descString(UPDATED_DESC_STRING)
            .orgInfo(UPDATED_ORG_INFO)
            .revokeTimeSpan(UPDATED_REVOKE_TIME_SPAN)
            .revokeStart(UPDATED_REVOKE_START)
            .revokeOver(UPDATED_REVOKE_OVER)
            .punishOrg(UPDATED_PUNISH_ORG)
            .punishTime(UPDATED_PUNISH_TIME)
            .facts(UPDATED_FACTS)
            .autoProcess(UPDATED_AUTO_PROCESS)
            .revokeProof(UPDATED_REVOKE_PROOF)
            .remarks(UPDATED_REMARKS);
        RegnRevoDTO regnRevoDTO = regnRevoMapper.toDto(updatedRegnRevo);

        restRegnRevoMockMvc.perform(put("/api/regn-revos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(regnRevoDTO)))
            .andExpect(status().isOk());

        // Validate the RegnRevo in the database
        List<RegnRevo> regnRevoList = regnRevoRepository.findAll();
        assertThat(regnRevoList).hasSize(databaseSizeBeforeUpdate);
        RegnRevo testRegnRevo = regnRevoList.get(regnRevoList.size() - 1);
        assertThat(testRegnRevo.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRegnRevo.getDescString()).isEqualTo(UPDATED_DESC_STRING);
        assertThat(testRegnRevo.getOrgInfo()).isEqualTo(UPDATED_ORG_INFO);
        assertThat(testRegnRevo.getRevokeTimeSpan()).isEqualTo(UPDATED_REVOKE_TIME_SPAN);
        assertThat(testRegnRevo.getRevokeStart()).isEqualTo(UPDATED_REVOKE_START);
        assertThat(testRegnRevo.getRevokeOver()).isEqualTo(UPDATED_REVOKE_OVER);
        assertThat(testRegnRevo.getPunishOrg()).isEqualTo(UPDATED_PUNISH_ORG);
        assertThat(testRegnRevo.getPunishTime()).isEqualTo(UPDATED_PUNISH_TIME);
        assertThat(testRegnRevo.getFacts()).isEqualTo(UPDATED_FACTS);
        assertThat(testRegnRevo.isAutoProcess()).isEqualTo(UPDATED_AUTO_PROCESS);
        assertThat(testRegnRevo.getRevokeProof()).isEqualTo(UPDATED_REVOKE_PROOF);
        assertThat(testRegnRevo.getRemarks()).isEqualTo(UPDATED_REMARKS);
    }

    @Test
    public void updateNonExistingRegnRevo() throws Exception {
        int databaseSizeBeforeUpdate = regnRevoRepository.findAll().size();

        // Create the RegnRevo
        RegnRevoDTO regnRevoDTO = regnRevoMapper.toDto(regnRevo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRegnRevoMockMvc.perform(put("/api/regn-revos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(regnRevoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RegnRevo in the database
        List<RegnRevo> regnRevoList = regnRevoRepository.findAll();
        assertThat(regnRevoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteRegnRevo() throws Exception {
        // Initialize the database
        regnRevoRepository.save(regnRevo);

        int databaseSizeBeforeDelete = regnRevoRepository.findAll().size();

        // Delete the regnRevo
        restRegnRevoMockMvc.perform(delete("/api/regn-revos/{id}", regnRevo.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RegnRevo> regnRevoList = regnRevoRepository.findAll();
        assertThat(regnRevoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
