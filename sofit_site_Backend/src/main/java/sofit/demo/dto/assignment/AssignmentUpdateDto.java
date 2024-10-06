package sofit.demo.dto.assignment;

import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

public record AssignmentUpdateDto(Optional<String> title) {
    
}
