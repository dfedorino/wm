package com.dfedorino.wm.programs;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProgramsApiController.class)
class ProgramsApiControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProgramsService programsService;
    @Autowired
    private ObjectMapper objectMapper;
    private final String apiPath = "http://localhost/api/programs";

    @Test
    void testGetProgram_whenExistingProgramName_then200OkAndProgramObject() throws Exception {
        Program mockDaily = new Program("daily", 1000, 60, 2400);
        when(programsService.findByName("daily")).thenReturn(mockDaily);

        mockMvc.perform(get("/api/programs/daily"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(mockDaily)))
                .andExpect(jsonPath("_links.self.href").value(apiPath + "/daily"))
                .andExpect(jsonPath("_links.programs.href").value(apiPath));
    }

    @Test
    void testGetPrograms_whenGet_then200OkAndProgramObjectList() throws Exception {
        Program mockDaily = new Program("daily", 1000, 60, 2400);
        Program mockQuick = new Program("quick", 1000, 60, 1800);
        List<Program> programList = Arrays.asList(mockDaily, mockQuick);
        when(programsService.findAll()).thenReturn(programList);

        mockMvc.perform(get("/api/programs"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("_embedded.programList").isArray())
                .andExpect(jsonPath("_embedded.programList[0].id").value(mockDaily.getId()))
                .andExpect(jsonPath("_embedded.programList[0].name").value(mockDaily.getName()))
                .andExpect(jsonPath("_embedded.programList[0].waterVolume").value(mockDaily.getWaterVolume()))
                .andExpect(jsonPath("_embedded.programList[0].waterTemperature").value(mockDaily.getWaterTemperature()))
                .andExpect(jsonPath("_embedded.programList[0].washingTime").value(mockDaily.getWashingTime()))
                .andExpect(jsonPath("_embedded.programList[0]._links.self.href").value(apiPath + "/" + mockDaily.getName()))
                .andExpect(jsonPath("_embedded.programList[0]._links.programs.href").value(apiPath))
                .andExpect(jsonPath("_embedded.programList[1].id").value(mockQuick.getId()))
                .andExpect(jsonPath("_embedded.programList[1].name").value(mockQuick.getName()))
                .andExpect(jsonPath("_embedded.programList[1].waterVolume").value(mockQuick.getWaterVolume()))
                .andExpect(jsonPath("_embedded.programList[1].waterTemperature").value(mockQuick.getWaterTemperature()))
                .andExpect(jsonPath("_embedded.programList[1].washingTime").value(mockQuick.getWashingTime()))
                .andExpect(jsonPath("_embedded.programList[1]._links.self.href").value(apiPath + "/" + mockQuick.getName()))
                .andExpect(jsonPath("_embedded.programList[1]._links.programs.href").value(apiPath))
                .andExpect(jsonPath("_embedded.programList[2].id").doesNotExist());
    }
}