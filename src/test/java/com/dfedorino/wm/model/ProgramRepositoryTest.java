package com.dfedorino.wm.model;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ProgramRepositoryTest {
    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private ProgramRepository programRepository;

    @Test
    void findById() {
        Program persisted = programRepository.findById(1);
        System.out.println(persisted);
        assertThat(persisted).isNotNull();
    }
}