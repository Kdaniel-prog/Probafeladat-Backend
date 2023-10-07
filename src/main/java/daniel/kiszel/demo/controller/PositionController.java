package daniel.kiszel.demo.controller;

import daniel.kiszel.demo.DTO.JobDTO;
import daniel.kiszel.demo.DTO.PositionRequestDTO;
import daniel.kiszel.demo.model.Position;
import daniel.kiszel.demo.service.PositionService;
import daniel.kiszel.demo.util.ObjectFieldValidator;
import jakarta.validation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping("/positions")
@Validated
public class PositionController {
    @Autowired
    PositionService positionService;
    ObjectFieldValidator objectFieldValidator = new ObjectFieldValidator();

    @PostMapping
    public ResponseEntity<Map<String, String>> createJob(@Valid @RequestBody PositionRequestDTO request) {
        return new ResponseEntity<>(positionService.createPosition(request), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Position> getPosition(@PathVariable("id") Long id){
        return new ResponseEntity<>(positionService.getPosition(id), HttpStatus.CREATED);
    }

    @GetMapping("/{keyword}/{location}")
    public ResponseEntity<JobDTO> findAllPositions(@PathVariable("keyword") String keyword,
                                                   @PathVariable("location") String location){

        //create an instance and check fields
        PositionRequestDTO positionSearchDTO = new PositionRequestDTO(keyword,location);

        ResponseEntity responseEntity = objectFieldValidator.checkFields(positionSearchDTO);
        if(responseEntity != null){
            return responseEntity;
        }
        //return external and internal database result
        return new ResponseEntity(positionService.getJobPositions(positionSearchDTO), HttpStatus.OK);
    }

}
