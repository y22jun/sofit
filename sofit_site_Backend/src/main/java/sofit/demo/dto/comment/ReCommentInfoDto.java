package sofit.demo.dto.comment;

import java.time.LocalDateTime;

import lombok.Data;
import sofit.demo.domain.Comment;
import sofit.demo.dto.user.UserInfoDto;

@Data
public class ReCommentInfoDto {

    private final static String DEFAULT_DELETE = "삭제된 댓글입니다.";

    private Long boardId;
    private Long parenId;

    private Long reCommentId;
    private String content;
    private boolean isRemoved;

    private UserInfoDto userInfoDto;
    private LocalDateTime createdAt;

    public ReCommentInfoDto(Comment comment) {

        this.boardId = comment.getBoard().getId();
        this.parenId = comment.getParent().getId();
        this.reCommentId = comment.getId();
        this.content = comment.getContent();

        if(comment.isRemoved()) {
            this.content = DEFAULT_DELETE;
        }

        this.isRemoved = comment.isRemoved();
        this.userInfoDto = new UserInfoDto(comment.getUser());
        this.createdAt = comment.getCreatedDate();
    }
}
