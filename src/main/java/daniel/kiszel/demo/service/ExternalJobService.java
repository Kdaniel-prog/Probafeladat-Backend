package daniel.kiszel.demo.service;

import daniel.kiszel.demo.DTO.JobDTO;
import daniel.kiszel.demo.DTO.PositionRequestDTO;
import daniel.kiszel.demo.responseModel.ResponseModel;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;

@Service
public class ExternalJobService {
    private final String baseUrl = "https://www.themuse.com/api/public/";
    private final WebClient webClient;

    public ExternalJobService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
    }

    public ArrayList<JobDTO> getExternalJobPosition(PositionRequestDTO positionRequestDTO){
        // Using WebClient
        String url = baseUrl + "jobs?page=0";

        if(positionRequestDTO.getKeyword() != null){
            url = url + "&category=" + positionRequestDTO.getKeyword();
        }
        if(positionRequestDTO.getLocation() != null){
            url = url + "&location=" + positionRequestDTO.getLocation();
        }

        WebClient.Builder builder = WebClient.builder();
        ResponseModel responseModel = this.webClient
                .get()
                .uri(url)
                .retrieve()
                .bodyToMono(ResponseModel.class)
                .block();
        ArrayList<JobDTO> jobs = new ArrayList<>();
        responseModel.getResults().forEach((job) ->{
            jobs.add(new JobDTO(job.getName(),job.getLocations().get(0).getName(), job.getRefs().getLandingPage()));
        });
        return jobs;
    }
}
