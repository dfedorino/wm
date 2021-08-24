package com.dfedorino.wm.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(WashingMachineController.class)
class WashingMachineControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void index() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(content().string("Washing Machine API"));
    }

    @Test
    void getPrograms() throws Exception {
        mockMvc.perform(get("/programs")).andDo(print());
    }

    @Test
    void executeProgram() throws Exception {
        mockMvc.perform(get("/programs/daily")).andDo(print());
    }
}