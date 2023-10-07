package daniel.kiszel.demo.controller;

import daniel.kiszel.demo.DTO.ClientRequestDTO;
import daniel.kiszel.demo.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/clients")
@Validated
public class ClientController {
    @Autowired
    ClientService clientService;

    @PostMapping
    public ResponseEntity<Map<String, UUID>> registerClient(@Valid @RequestBody  ClientRequestDTO request) {
        //Check email is unique
        if(clientService.isEmailPresent(request.getEmail())){
            Map<String, String> errors = new HashMap<>();
            errors.put("email","The email is already taken");
            return new ResponseEntity(errors, HttpStatus.BAD_REQUEST);
        }

        Map<String, UUID> apiKeys = new HashMap<>();
        apiKeys.put("uuid", clientService.createClient(request));
        return new ResponseEntity<>(apiKeys, HttpStatus.CREATED);
    }
}
