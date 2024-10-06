package sofit.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import sofit.demo.domain.Assignment;
import sofit.demo.domain.SubofAssignment;
import java.util.*;
public interface SubofAssignmentRepository extends JpaRepository<SubofAssignment, Long> {
    
    Optional<SubofAssignment> findWithUserById(Long id);

    List<SubofAssignment> findByAssignment(Assignment assignment);
}
