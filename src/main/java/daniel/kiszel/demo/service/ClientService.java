package daniel.kiszel.demo.service;

import daniel.kiszel.demo.DTO.ClientRequestDTO;
import daniel.kiszel.demo.model.Client;
import daniel.kiszel.demo.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;
    public boolean isApiKeyValid(UUID apiKey){
        if (clientRepository.findClientByApiKey(apiKey).isPresent()) {
            return true;
        }else{
            return false;
        }
    }
    public boolean isEmailPresent(String email){
        if (clientRepository.findByEmail(email).isPresent()) {
            return true;
        }else{
            return false;
        }
    }
    public UUID createClient(ClientRequestDTO request){
        // Creat new Client
        Client client = new Client();
        client.setName(request.getName());
        client.setEmail(request.getEmail());
        client.setApiKey(UUID.randomUUID());

        // Save Client in database
        clientRepository.save(client);
        return client.getApiKey();
    }
}
