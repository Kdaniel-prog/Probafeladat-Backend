package daniel.kiszel.demo.repository;

import daniel.kiszel.demo.model.Client;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ClientRepositoryTest {

    @Autowired
    private ClientRepository clientRepository;

    @Test
    public void ClientRepository_findByEmail() {
        Client client = Client.builder()
                .name("Test")
                .email("test@test.com").build();

        Client savedClient = clientRepository.save(client);

        Optional<Client> searchedClient = clientRepository.findByEmail(client.getEmail());

        //Assert
        Assertions.assertThat(searchedClient.get()).isNotNull();
        Assertions.assertThat(searchedClient.get().getId()).isGreaterThan(0);

        //Check if the Client is the one we looked for
        Assertions.assertThat(searchedClient.get().getId()).isEqualTo(savedClient.getId());
    }

}
