package sofit.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import sofit.demo.domain.Group;


public interface GroupRepository extends JpaRepository<Group,Long> {
    Page<Group> findByGroupNameContaining(String keyword, Pageable pageable);
}
