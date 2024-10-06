package sofit.demo.dto.weekly;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class WeeklyDto {
    private Long id;
    private LocalDateTime createdAt;
}
