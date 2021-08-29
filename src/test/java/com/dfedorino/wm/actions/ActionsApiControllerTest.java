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

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ActionsApiController.class)
class ActionsApiControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private ActionsService mockService;
    private final String apiPath = "http://localhost/api/actions";

    @Test
    void testGetActions_whenClientCanAcceptJson_then200OkAndListOfActions() throws Exception {
        Action drain = new Action(1L, "drain", "Drain water");
        Action unlock = new Action(2L, "unlock", "Unlock the machine");
        List<Action> mockActions = Arrays.asList(drain, unlock);

        when(mockService.findAll()).thenReturn(mockActions);

        mockMvc.perform(get("/api/actions").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("_embedded.actionList").isArray())
                .andExpect(jsonPath("_embedded.actionList[0].id").value(drain.getId()))
                .andExpect(jsonPath("_embedded.actionList[0].name").value(drain.getName()))
                .andExpect(jsonPath("_embedded.actionList[0].description").value(drain.getDescription()))
                .andExpect(jsonPath("_embedded.actionList[0]._links.self.href").value(apiPath + "/" + drain.getName()))
                .andExpect(jsonPath("_embedded.actionList[0]._links.actions.href").value(apiPath))
                .andExpect(jsonPath("_embedded.actionList[1].id").value(unlock.getId()))
                .andExpect(jsonPath("_embedded.actionList[1].name").value(unlock.getName()))
                .andExpect(jsonPath("_embedded.actionList[1].description").value(unlock.getDescription()))
                .andExpect(jsonPath("_embedded.actionList[1]._links.self.href").value(apiPath + "/" + unlock.getName()))
                .andExpect(jsonPath("_embedded.actionList[1]._links.actions.href").value(apiPath))
                .andExpect(jsonPath("_embedded.actionList[2].id").doesNotExist());
    }

    @Test
    void testPostDrain_whenMachineIsWaiting_then200OkAndActionResultTrue() throws Exception {
        ActionResult actionResult = new ActionResult(true, "action", ZonedDateTime.now().toInstant());
        when(mockService.applyDrain()).thenReturn(actionResult);

        mockMvc.perform(post("/api/actions/drain").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(actionResult)))
                .andExpect(jsonPath("_links.self.href").value(apiPath + "/drain"))
                .andExpect(jsonPath("_links.actions.href").value(apiPath));
    }

    @Test
    void testPostDrain_whenMachineIsWashing_then409ConflictAndActionResultFalse() throws Exception {
        ActionResult actionResult = new ActionResult(false, "action", ZonedDateTime.now().toInstant());
        when(mockService.applyDrain()).thenReturn(actionResult);

        mockMvc.perform(post("/api/actions/drain").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(409))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(actionResult)))
                .andExpect(jsonPath("_links.self.href").value(apiPath + "/drain"))
                .andExpect(jsonPath("_links.actions.href").value(apiPath));
    }

    @Test
    void testPostUnlock_whenMachineIsWaiting_then200OkAndActionResultTrue() throws Exception {
        ActionResult actionResult = new ActionResult(true, "action", ZonedDateTime.now().toInstant());
        when(mockService.applyUnlock()).thenReturn(actionResult);

        mockMvc.perform(post("/api/actions/unlock").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(actionResult)))
                .andExpect(jsonPath("_links.self.href").value(apiPath + "/unlock"))
                .andExpect(jsonPath("_links.actions.href").value(apiPath));
    }

    @Test
    void testPostUnlock_whenMachineIsWashing_then409ConflictAndActionResultFalse() throws Exception {
        ActionResult actionResult = new ActionResult(false, "action", ZonedDateTime.now().toInstant());
        when(mockService.applyUnlock()).thenReturn(actionResult);

        mockMvc.perform(post("/api/actions/unlock").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(409))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(actionResult)))
                .andExpect(jsonPath("_links.self.href").value(apiPath + "/unlock"))
                .andExpect(jsonPath("_links.actions.href").value(apiPath));
    }

    @Test
    void testPostRun_whenMachineIsWaiting_then200OkAndActionResultTrue() throws Exception {
        Program program = new Program();
        ActionResult actionResult = new ActionResult(true, "action", ZonedDateTime.now().toInstant());
        when(mockService.applyRun(program)).thenReturn(actionResult);

        RequestBuilder post = post("/api/actions/run")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(program));

        mockMvc.perform(post)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(actionResult)))
                .andExpect(jsonPath("_links.self.href").value(apiPath + "/run"))
                .andExpect(jsonPath("_links.actions.href").value(apiPath));
    }

    @Test
    void testPostRun_whenMachineIsWashing_then409ConflictAndActionResultFalse() throws Exception {
        Program program = new Program();
        ActionResult actionResult = new ActionResult(false, "action", ZonedDateTime.now().toInstant());
        when(mockService.applyRun(program)).thenReturn(actionResult);

        RequestBuilder post = post("/api/actions/run")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(program));

        mockMvc.perform(post)
                .andExpect(status().is(409))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(actionResult)))
                .andExpect(jsonPath("_links.self.href").value(apiPath + "/run"))
                .andExpect(jsonPath("_links.actions.href").value(apiPath));
    }
}