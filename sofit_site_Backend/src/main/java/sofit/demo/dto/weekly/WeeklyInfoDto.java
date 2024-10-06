 package sofit.demo.dto.weekly;

 import lombok.AllArgsConstructor;
 import lombok.Builder;
 import lombok.Getter;
 import sofit.demo.domain.SubofAssignment;
 import sofit.demo.domain.WeeklyPoint;

 import java.time.LocalDateTime;

 @Getter
 @Builder
 @AllArgsConstructor
 public class WeeklyInfoDto {
     private Long id;
     private Long checkPointId;
    public WeeklyInfoDto(WeeklyPoint weeklyPoint) {
     this.id = weeklyPoint.getId();
     this.checkPointId = weeklyPoint.getCheckPoint().getId();

    }

 }

