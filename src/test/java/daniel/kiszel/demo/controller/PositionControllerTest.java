package daniel.kiszel.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import daniel.kiszel.demo.DTO.JobDTO;
import daniel.kiszel.demo.DTO.PositionRequestDTO;
import daniel.kiszel.demo.interceptor.ApiKeyInterceptor;
import daniel.kiszel.demo.model.Client;
import daniel.kiszel.demo.model.Position;
import daniel.kiszel.demo.service.ExternalJobService;
import daniel.kiszel.demo.service.PositionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase
@Transactional
@SpringBootTest
public class PositionControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PositionService positionService;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ApiKeyInterceptor apiKeyInterceptor;

    @MockBean
    private ExternalJobService externalJobService;
    private PositionRequestDTO positionRequestDTO;
    private Position position;

    private Client client;

    private UUID clientApiKey;
    @BeforeEach
    public void init() throws Exception{
        clientApiKey = UUID.randomUUID();
        client = Client.builder().apiKey(clientApiKey).email("test@test.com").name("test").build();

        positionRequestDTO = PositionRequestDTO.builder().location("london").keyword("Account").build();
        position = Position.builder().id(1).location("london")
                .name("Account").url("http://localhost:8080/positions/1").build();

        // Configure the mock interceptor behavior
        Mockito.when(apiKeyInterceptor.preHandle(any(HttpServletRequest.class),
                        any(HttpServletResponse.class), any(Object.class)))
                .thenReturn(true); // For example, always return true for preHandle
    }

    @Test
    public void PositionController_CreateJob_ReturnJobLink() throws Exception{
        Map<String, String> urls = new HashMap<>();
        urls.put("url", position.getUrl());
        given(positionService.createPosition(any())).willReturn(urls);

        ResultActions response = mockMvc.perform(
                post("/positions")
                .header("api-key",clientApiKey.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(positionRequestDTO)));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json("{\"url\":\"" + position.getUrl()+ "\"}"));

    }

    @Test
    public void PositionController_GetPosition_ReturnPosition() throws Exception{
        given(positionService.getPosition(position.getId())).willReturn(position);

        ResultActions response = mockMvc.perform(
                get("/positions/"+position.getId())
                        .header("api-key",clientApiKey.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(positionRequestDTO)));

        response.andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void PositionController_FindAllPositions_ReturnJobDTOList() throws Exception{
        //Mock externJobService Call out
        ArrayList<JobDTO> externalMockJobs = new ArrayList<>();
        externalMockJobs.add(new JobDTO("external job Accounting","London","https://test.com"));
        when(externalJobService.getExternalJobPosition(any(PositionRequestDTO.class))).thenReturn(externalMockJobs);

        given(positionService.getJobPositions(any())).willReturn(externalMockJobs);

        ResultActions response = mockMvc.perform(
                get("/positions/accounting/london")
                        .header("api-key",clientApiKey.toString())
                        .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }
}
