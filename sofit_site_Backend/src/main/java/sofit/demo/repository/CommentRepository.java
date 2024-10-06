package sofit.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import sofit.demo.domain.Comment;
import java.util.*;
import org.springframework.data.repository.query.Param;
public interface CommentRepository extends JpaRepository<Comment, Long>  {
    
    List<Comment> findByBoardId(Long boardId);

    Optional<Comment> findWithUserById(Long id);
    List<Comment> findByParentId(@Param("parentId") Long parentId);
}
