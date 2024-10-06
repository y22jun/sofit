package sofit.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import sofit.demo.domain.CheckPoint;
import java.util.List;

import javax.swing.GroupLayout.Group;


public interface CheckPointRepository extends JpaRepository<CheckPoint, Long> {
        
    boolean existsByGroupId(Long groupId);
    CheckPoint findByGroupId(Long groupId);
}
