package com.dfedorino.wm.actions;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ActionRepositoryTest {
    @Autowired
    private ActionRepository actionRepository;

    @Test
    public void testFindAll_whenCalled_thenListOfActions() {
        assertThat(actionRepository.findAll()).isNotNull();
    }
}