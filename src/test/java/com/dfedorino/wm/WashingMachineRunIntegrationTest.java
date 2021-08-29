package com.dfedorino.wm;

import com.dfedorino.wm.programs.Program;
import com.dfedorino.wm.programs.ProgramsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class WashingMachineRunIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ProgramsService programService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testActions_whenPostRunWithProgram_then200Ok() throws Exception {
        Program daily = programService.findByName("daily");
        String programJson = objectMapper.writeValueAsString(daily);
        mockMvc.perform(post("/api/actions/run").contentType(MediaType.APPLICATION_JSON).content(programJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}
