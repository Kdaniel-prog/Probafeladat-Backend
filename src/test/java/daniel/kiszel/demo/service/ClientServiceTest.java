package daniel.kiszel.demo.service;

import daniel.kiszel.demo.DTO.ClientRequestDTO;
import daniel.kiszel.demo.model.Client;
import daniel.kiszel.demo.repository.ClientRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientService clientService;

    @Test
    public void ClientService_CreateClient_ReturnUUID() {
        Client client = Client.builder()
                .name("Test")
                .email("test@test.com").build();

        ClientRequestDTO clientDTO = ClientRequestDTO.builder().name("test").email("test@test.com").build();

        when(clientRepository.save(Mockito.any(Client.class))).thenReturn(client);

        UUID apiKey = clientService.createClient(clientDTO);

        Assertions.assertThat(apiKey).isNotNull();
    }

}
