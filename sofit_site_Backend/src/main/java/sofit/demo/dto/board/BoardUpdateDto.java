package sofit.demo.dto.board;

import java.util.List;
import java.util.Optional;

public record BoardUpdateDto(Optional<String> title, Optional<String> content, Optional<List<String>> hashtag) {
    
}
