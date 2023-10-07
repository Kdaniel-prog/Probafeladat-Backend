package daniel.kiszel.demo.service;

import daniel.kiszel.demo.DTO.JobDTO;
import daniel.kiszel.demo.DTO.PositionRequestDTO;
import daniel.kiszel.demo.model.Position;
import daniel.kiszel.demo.repository.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PositionService {

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private ExternalJobService externalJobService;

    public Map<String, String> createPosition(PositionRequestDTO request){
        // Creat new position
        Position position = new Position();
        position.setName(request.getKeyword());
        position.setLocation(request.getLocation());

        // Save position in database
        positionRepository.save(position);
        // Generate URL
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(position.getId()).toUri();
        position.setUrl(location.toString());
        //Save the record again with generated url
        positionRepository.save(position);
        Map<String, String> urls = new HashMap<>();
        urls.put("url", position.getUrl());
        return urls;
    }
    public Position getPosition(long id) {
        return positionRepository.findPositionById(id);
    }
    public ArrayList<JobDTO> getJobPositions(PositionRequestDTO positionRequestDTO){
        List<Position> positions = positionRepository
                .findAllByNameContainingAndLocationContaining(positionRequestDTO.getKeyword(),
                                                            positionRequestDTO.getLocation());
        List<JobDTO> internalPosition = new ArrayList<>();
        positions.forEach((position -> {
            internalPosition.add(new JobDTO(position.getName(),position.getLocation(),position.getUrl()));
        }) );
        ArrayList<JobDTO> externalJobs = externalJobService.getExternalJobPosition(positionRequestDTO);
        externalJobs.addAll(internalPosition);
        return externalJobs;
    }

}
