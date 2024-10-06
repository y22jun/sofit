package sofit.demo.dto.SubofAssignment;

import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import sofit.demo.domain.SubofAssignment;

public record SubofAssignmentSaveDto(Optional<MultipartFile> uploadFile) {
    
    public SubofAssignment toEntity() {
        return SubofAssignment.builder()
                              .build();
    }
}
