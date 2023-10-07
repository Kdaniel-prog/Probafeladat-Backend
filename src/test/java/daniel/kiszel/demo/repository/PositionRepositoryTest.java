package daniel.kiszel.demo.repository;


import daniel.kiszel.demo.model.Position;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class PositionRepositoryTest {
    @Autowired
    private PositionRepository positionRepository;
    private static final String KEYWORD = "Account";
    private static final String LOCATION = "London";
    @Test
    public void PositionRepository_findPositionById() {

        Position position = Position.builder()
                .name("Test")
                .location("London")
                .url("https://test/1").build();

        Position savedPosition = positionRepository.save(position);

        Position findedPosition = positionRepository.findPositionById(savedPosition.getId());

        //Assert
        Assertions.assertThat(findedPosition).isNotNull();
        Assertions.assertThat(findedPosition.getId()).isGreaterThan(0);

        //Check if the Client is the one we looked for
        Assertions.assertThat(findedPosition.getId()).isEqualTo(savedPosition.getId());
    }

    @Test
    public void PositionRepository_findAllByNameContainingAndLocationContaining() {

        Position position = Position.builder()
                .name("Account 1")
                .location("London")
                .url("https://test/1").build();

        Position position2 = Position.builder()
                .name("Account 2")
                .location("London")
                .url("https://test/2").build();

        positionRepository.save(position);
        positionRepository.save(position2);

        List<Position> findedPosition = positionRepository
                .findAllByNameContainingAndLocationContaining(KEYWORD,LOCATION);

        //Assert
        Assertions.assertThat(findedPosition).isNotNull();
        Assertions.assertThat(findedPosition.size()).isEqualTo(2);
    }

}
