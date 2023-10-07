package daniel.kiszel.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import daniel.kiszel.demo.DTO.ClientRequestDTO;
import daniel.kiszel.demo.service.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(controllers = ClientController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class ClientControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientService clientService;

    @Autowired
    private ObjectMapper objectMapper;

    private ClientRequestDTO clientRequestDTO;

    private UUID expectedUUID;

    @BeforeEach
    public void init(){
        clientRequestDTO = ClientRequestDTO.builder().name("Test").email("test@test.com").build();
    }

    @Test
    public void ClientController_CreateClient_ReturnApiKey() throws Exception{
        expectedUUID = UUID.randomUUID(); // Create a UUID to return from the service
        given(clientService.createClient(ArgumentMatchers.any())).willReturn(expectedUUID);

        ResultActions response = mockMvc.perform(post("/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clientRequestDTO)));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json("{\"uuid\":\"" + expectedUUID+ "\"}"));

    }

}
