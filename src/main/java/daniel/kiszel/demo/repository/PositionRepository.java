package daniel.kiszel.demo.repository;

import daniel.kiszel.demo.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PositionRepository extends JpaRepository<Position, Long> {

    Position findPositionById(long Id);

    List<Position> findAllByNameContainingAndLocationContaining(String nameParam, String locationParam);
}
