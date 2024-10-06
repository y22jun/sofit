package sofit.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import sofit.demo.domain.Assignment;
import sofit.demo.domain.WeeklyPoint;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    Optional<Assignment> findWithUserById(Long id);

    List<Assignment> findByWeeklyPoint(WeeklyPoint weeklyPoint);
}
