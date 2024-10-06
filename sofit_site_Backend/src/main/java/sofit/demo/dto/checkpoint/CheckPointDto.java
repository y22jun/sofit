package sofit.demo.dto.checkpoint;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@AllArgsConstructor
public class CheckPointDto{
    private Long id;
    private String goal;
    private LocalDateTime createdAt;
}
