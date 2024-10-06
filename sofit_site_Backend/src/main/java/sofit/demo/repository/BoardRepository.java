package sofit.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import sofit.demo.domain.Board;

import java.util.*;

public interface BoardRepository extends JpaRepository<Board, Long> {
    
    Optional<Board> findWithUserById(Long id);

    Page<Board> findByTitleContaining(String keyword1, Pageable pageable); 
}
