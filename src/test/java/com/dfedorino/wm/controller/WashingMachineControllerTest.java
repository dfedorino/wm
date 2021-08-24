package com.dfedorino.wm.controller;

import com.dfedorino.wm.model.Program;
import com.dfedorino.wm.model.ProgramRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class WashingMachineControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProgramRepository programRepository;

    @Test
    void index() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string("Washing Machine API"));
    }

    @Test
    void getPrograms() throws Exception {
        mockMvc.perform(get("/programs"))
                .andExpect(status().isOk());
    }

    @Test
    void executeProgram() throws Exception {
        Program program = new Program(1L, "test", 1, 1, 1);
        programRepository.save(program);
        when(programRepository.findById(1)).thenReturn(program);
        mockMvc.perform(get("/programs/1"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}