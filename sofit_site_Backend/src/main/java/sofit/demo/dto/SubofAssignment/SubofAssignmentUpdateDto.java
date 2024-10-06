package sofit.demo.dto.SubofAssignment;

import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

public record SubofAssignmentUpdateDto(Optional<MultipartFile> uploadFile) {
    
}
