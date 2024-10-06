package sofit.demo.dto.checkpoint;

import sofit.demo.domain.CheckPoint;

public record CheckPointSaveDto(String title) {

    public CheckPoint toEntity() {
        return CheckPoint.builder()
                         .title(title)
                         .build();
    }
    
}
