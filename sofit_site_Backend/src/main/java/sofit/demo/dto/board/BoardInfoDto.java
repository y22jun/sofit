package sofit.demo.dto.board;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sofit.demo.domain.Board;
import sofit.demo.domain.Comment;
import sofit.demo.dto.comment.CommentInfoDto;
import sofit.demo.dto.user.UserInfoDto;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.*;
@Data
@NoArgsConstructor
public class BoardInfoDto {
    
    private Long boardId;
    private String title;
    private String content;
    private List<String> hashtag;
    private String filePath;
    private UserInfoDto userInfoDto;
    private List<CommentInfoDto> commentInfoDto;
    private LocalDateTime createdAt;

    @Builder
    public BoardInfoDto(Board board) {
        this.boardId = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.hashtag = board.getHashtag();
        this.filePath = board.getFilePath();
        this.createdAt = board.getCreatedDate();
        this.userInfoDto = new UserInfoDto(board.getUser());

        Map<Comment, List<Comment>> commentListMap = board.getCommentList().stream()
                                                    .filter(comment -> comment.getParent() != null)
                                                    .collect(Collectors.groupingBy(Comment::getParent));

        commentInfoDto = commentListMap.keySet().stream()
                                        .map(comment -> new CommentInfoDto(comment, commentListMap.get(comment)))
                                        .toList();
    }
}
