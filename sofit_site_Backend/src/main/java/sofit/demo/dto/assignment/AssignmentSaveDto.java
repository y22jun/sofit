package sofit.demo.dto.assignment;

import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import sofit.demo.domain.Assignment;

public record AssignmentSaveDto(String title) {

    public Assignment toEntity() {
        return Assignment.builder()
                         .title(title)
                         .build();
    }
    
}
