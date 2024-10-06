package sofit.demo.dto.comment;

import sofit.demo.domain.Comment;

public record CommentSaveDto(String content) {
    
    public Comment toEntity() {
        return Comment.builder()
                      .content(content)
                      .build();
    }
}