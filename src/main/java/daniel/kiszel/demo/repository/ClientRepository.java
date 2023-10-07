package daniel.kiszel.demo.repository;

import daniel.kiszel.demo.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;
public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByEmail(String email);
    Optional<Client> findClientByApiKey(UUID apiKey);
}
