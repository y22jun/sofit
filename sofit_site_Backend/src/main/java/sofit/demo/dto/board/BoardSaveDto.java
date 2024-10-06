package sofit.demo.dto.board;

import sofit.demo.domain.Board;

import java.util.*;

public record BoardSaveDto(String title, String content, List<String> hashtag) {

    public Board toEntity() {
        return Board.builder()
                    .title(title)
                    .content(content)
                    .hashtag(hashtag)
                    .build();
    }
}
