package sofit.demo.dto.assignment;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sofit.demo.domain.Assignment;
import sofit.demo.dto.user.UserInfoDto;

@Data
@NoArgsConstructor
public class AssignmentInfoDto {
    
    private Long assId;
    private String title;
    private Long weeklyId;
    private UserInfoDto userInfoDto;


    @Builder
    public AssignmentInfoDto(Assignment assignment) {
        this.assId = assignment.getId();
        this.title = assignment.getTitle();
        this.weeklyId = assignment.getWeeklyPoint().getId();
        this.userInfoDto = new UserInfoDto(assignment.getUser());
    }

}
