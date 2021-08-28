package com.dfedorino.wm.programs;

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
    void testFindByName_whenExistingName_thenProgramObject() {
        Program expectedProgram = new Program("test", 0, 0, 0);
        Program found = testEntityManager.persistFlushFind(expectedProgram);
        assertThat(programRepository.findByName(expectedProgram.getName()).get()).isEqualTo(found);
    }

    @Test
    void testFindByName_whenNonExistingName_thenNull() {
        assertThat(programRepository.findByName("nonExistingName").isPresent()).isFalse();
    }

    @Test
    void testFindAll_whenCalled_thenListOfPrograms() {
        assertThat(programRepository.findAll()).isNotNull();
    }
}