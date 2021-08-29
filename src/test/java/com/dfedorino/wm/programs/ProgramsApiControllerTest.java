package com.dfedorino.wm.programs;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProgramsApiController.class)
class ProgramsApiControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProgramsService programsService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetProgram_whenExistingProgramName_then200OkAndProgramObject() throws Exception {
        String existingProgramName = "daily";
        Program mockProgram = new Program();
        ReflectionTestUtils.setField(mockProgram, "name", existingProgramName);
        when(programsService.findByName(existingProgramName)).thenReturn(mockProgram);

        mockMvc.perform(get("/api/programs/" + existingProgramName))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(mockProgram)));
    }

    @Test
    void testGetPrograms_whenGet_then200OkAndProgramObjectList() throws Exception {
        List<Program> mockPrograms = Arrays.asList(new Program(), new Program());
        when(programsService.findAll()).thenReturn(mockPrograms);

        mockMvc.perform(get("/api/programs"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(mockPrograms)));
    }
}