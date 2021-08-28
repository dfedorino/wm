package com.dfedorino.wm.actions;

import com.dfedorino.wm.programs.Program;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ActionsApiController.class)
class ActionsApiControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private ActionsService mockService;

    @Test
    void testGetActions_whenClientCanAcceptJson_then200OkAndListOfActions() throws Exception {
        List<Action> mockActions = Arrays.asList(new Action(), new Action());

        when(mockService.findAll()).thenReturn(mockActions);

        mockMvc.perform(get("/api/actions").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(mockActions)));
    }

    @Test
    void testPostDrain_whenMachineIsWaiting_then200OkAndActionResultTrue() throws Exception {
        ActionResult actionResult = new ActionResult();
        actionResult.setApplied(true);
        when(mockService.applyDrain()).thenReturn(actionResult);

        mockMvc.perform(post("/api/actions/drain").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(actionResult)));
    }

    @Test
    void testPostDrain_whenMachineIsWashing_then409ConflictAndActionResultFalse() throws Exception {
        ActionResult actionResult = new ActionResult();
        when(mockService.applyDrain()).thenReturn(actionResult);

        mockMvc.perform(post("/api/actions/drain").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(409))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(actionResult)));
    }

    @Test
    void testPostUnlock_whenMachineIsWaiting_then200OkAndActionResultTrue() throws Exception {
        ActionResult actionResult = new ActionResult();
        actionResult.setApplied(true);
        when(mockService.applyUnlock()).thenReturn(actionResult);

        mockMvc.perform(post("/api/actions/unlock").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(actionResult)));
    }

    @Test
    void testPostUnlock_whenMachineIsWashing_then409ConflictAndActionResultFalse() throws Exception {
        ActionResult actionResult = new ActionResult();
        when(mockService.applyUnlock()).thenReturn(actionResult);

        mockMvc.perform(post("/api/actions/unlock").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(409))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(actionResult)));
    }

    @Test
    void testPostRun_whenMachineIsWaiting_then200OkAndActionResultTrue() throws Exception {
        Program program = new Program();
        ActionResult actionResult = new ActionResult();
        actionResult.setApplied(true);
        when(mockService.applyRun(program)).thenReturn(actionResult);

        RequestBuilder post = post("/api/actions/run")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(program));

        mockMvc.perform(post)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(actionResult)));
    }

    @Test
    void testPostRun_whenMachineIsWashing_then409ConflictAndActionResultFalse() throws Exception {
        Program program = new Program();
        ActionResult actionResult = new ActionResult();
        when(mockService.applyRun(program)).thenReturn(actionResult);

        RequestBuilder post = post("/api/actions/run")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(program));

        mockMvc.perform(post)
                .andExpect(status().is(409))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(actionResult)));
    }
}