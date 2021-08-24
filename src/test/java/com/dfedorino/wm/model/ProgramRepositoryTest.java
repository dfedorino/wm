package com.dfedorino.wm.model;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ProgramRepositoryTest {
    @Autowired
    TestEntityManager testEntityManager;

    @Autowired
    ProgramRepository programRepository;

    @Test
    void testFindAll_whenRequested_thenReturnListOfPrograms() {
        // given
        Program storedProgram = new Program( null, 100, 60, 2400);
        testEntityManager.persist(storedProgram);
        testEntityManager.flush();

        // when
        List<Program> retrievedProgram = programRepository.findAll();

        // then
        assertThat(retrievedProgram)
                .hasSize(1)
                .contains(storedProgram);
    }

    @Test
    void testFindById_whenExistingIdIsPassed_thenReturnProgram() {
        // given
        Program storedProgram = new Program( null, 100, 60, 2400);
        testEntityManager.persist(storedProgram);
        testEntityManager.flush();

        // when
        Program retrievedProgram = programRepository.findById(1);

        // then
        assertThat(retrievedProgram).isEqualTo(storedProgram);
    }
}