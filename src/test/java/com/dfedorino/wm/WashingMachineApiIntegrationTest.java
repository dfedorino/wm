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

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class WashingMachineApiIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ProgramsService programService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testActions_whenGetActions_then200Ok() throws Exception {
        mockMvc.perform(get("/api/actions"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testActions_whenPostDrain_then200Ok() throws Exception {
        mockMvc.perform(post("/api/actions/drain"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testActions_whenPostUnlock_then200Ok() throws Exception {
        mockMvc.perform(post("/api/actions/unlock"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testActions_whenPostRunWithProgram_then200Ok() throws Exception {
        Program daily = programService.findByName("daily");
        String programJson = objectMapper.writeValueAsString(daily);
        mockMvc.perform(post("/api/actions/unlock").content(programJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testPrograms_whenGetPrograms_then200Ok() throws Exception {
        mockMvc.perform(get("/api/programs"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testPrograms_whenGetProgram_then200Ok() throws Exception {
        List<Program> programs = programService.findAll();
        for (Program program : programs) {
            mockMvc.perform(get("/api/programs/" + program.getName()))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        }
    }
}
