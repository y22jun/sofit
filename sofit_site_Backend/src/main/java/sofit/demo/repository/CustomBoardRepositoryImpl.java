// package sofit.demo.repository;

// import java.util.List;

// import org.springframework.data.domain.Page;
// import org.springframework.data.domain.Pageable;
// import org.springframework.data.support.PageableExecutionUtils;
// import org.springframework.stereotype.Repository;
// import org.springframework.util.StringUtils;
// import com.querydsl.core.types.dsl.BooleanExpression;
// import jakarta.persistence.EntityManager;
// import sofit.demo.domain.Board;
// import sofit.demo.dto.board.BoardSearchDto;

// import com.querydsl.jpa.impl.JPAQuery;
// import com.querydsl.jpa.impl.JPAQueryFactory;
// import jakarta.persistence.EntityManager;

// @Repository
// public class CustomBoardRepositoryImpl implements CustomBoardRepository {
//     private final JPAQueryFactory query;

//     public CustomBoardRepositoryImpl(EntityManager em) {
//         query = new JPAQueryFactory(em);
//     }


//     @Override
//     public Page<Board> search(BoardSearchDto boardSearchDto, Pageable pageable) {



//         List<Board> content = query.selectFrom(board)

//                                     .where(
//                                             contentHasStr(boardSearchDto.getContent()),
//                                             titleHasStr(boardSearchDto.getTitle())
//                                         )
//                 .leftJoin(board.)

//                 .fetchJoin()
//                                     .offset(pageable.getOffset())
//                                     .limit(pageable.getPageSize())
//                                     .fetch(); //Count 쿼리 발생 X




//         JPAQuery<Board> countQuery = query.selectFrom(board)
//                                             .where(
//                                                     contentHasStr(boardSearchDto.getContent()),
//                                                     titleHasStr(boardSearchDto.getTitle())
//                                             );



//         return  PageableExecutionUtils.getPage(content, pageable, () -> countQuery.fetch().size());
//     }

//     private BooleanExpression contentHasStr(String content) {
//         return StringUtils.hasLength(content) ? board.content.contains(content) : null;
//     }


//     private BooleanExpression titleHasStr(String title) {
//         return StringUtils.hasLength(title) ? board.title.contains(title) : null;
//     }
// }
