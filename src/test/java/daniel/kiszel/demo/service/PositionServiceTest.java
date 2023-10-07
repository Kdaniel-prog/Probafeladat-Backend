package daniel.kiszel.demo.service;

import daniel.kiszel.demo.DTO.JobDTO;
import daniel.kiszel.demo.DTO.PositionRequestDTO;
import daniel.kiszel.demo.model.Position;
import daniel.kiszel.demo.repository.PositionRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;


import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
public class PositionServiceTest {
    @Mock
    private PositionRepository positionRepository;

    @Mock
    private ExternalJobService externalJobService;

    @InjectMocks
    private PositionService positionService;

    @Test
    public void PositionService_CreatePosition_ReturnURL() {
        Position position = Position.builder()
                .name("Account")
                .location("London")
                .url("https://test.com/1")
                .build();

        PositionRequestDTO positionDTO = PositionRequestDTO.builder().keyword("test").location("London").build();

        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        when(positionRepository.save(Mockito.any(Position.class))).thenReturn(position);

        Map<String, String> url = positionService.createPosition(positionDTO);

        Assertions.assertThat(url).isNotEmpty();
    }

    @Test
    public void PositionService_getPosition_ReturnPosition() {
        Position position = Position.builder()
                .id(1)
                .name("Account")
                .location("London")
                .url("https://test.com/1")
                .build();

        when(positionRepository.findPositionById(Mockito.any(Long.class))).thenReturn(position);

        Position foundPosition = positionService.getPosition(position.getId());

        Assertions.assertThat(foundPosition).isEqualTo(position);
    }

    @Test
    public void PositionService_getJobPositions_ReturnPositions() {
        Position position = Position.builder()
                .id(1)
                .name("Account")
                .location("London")
                .url("https://test.com/1")
                .build();

        JobDTO positionExternal = JobDTO.builder()
                .name("Account external")
                .location("London")
                .url("https://test.com/2")
                .build();

        ArrayList<JobDTO> externals = new ArrayList<>();
        externals.add(positionExternal);

        PositionRequestDTO positionDTO = PositionRequestDTO.builder().keyword("Account").location("London").build();

        when(externalJobService.getExternalJobPosition(
                Mockito.any(PositionRequestDTO.class))).thenReturn(externals);

        when(positionRepository.findAllByNameContainingAndLocationContaining(
                Mockito.any(),Mockito.any())).thenReturn(Arrays.asList(position));

        ArrayList<JobDTO> positions = positionService.getJobPositions(positionDTO);

        Assertions.assertThat(positions.size()).isEqualTo(2);
    }

}
