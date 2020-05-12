package com.mycompany.myapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class RegnCancMapperTest {

    private RegnCancMapper regnCancMapper;

    @BeforeEach
    public void setUp() {
        regnCancMapper = new RegnCancMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        String id = "id1";
        assertThat(regnCancMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(regnCancMapper.fromId(null)).isNull();
    }
}
