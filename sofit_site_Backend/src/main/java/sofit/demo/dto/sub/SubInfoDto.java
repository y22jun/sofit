package sofit.demo.dto.sub;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sofit.demo.domain.SubofAssignment;
import sofit.demo.dto.user.UserInfoDto;

@Data
@NoArgsConstructor
public class SubInfoDto {

    private Long subId;
    private Long assId;
    private int isOk;
    private String filePath;
    private UserInfoDto userInfoDto;


    @Builder
    public SubInfoDto(SubofAssignment subofAssignment) {
        this.subId = subofAssignment.getId();
        this.assId = subofAssignment.getAssignment().getId();
        this.isOk = subofAssignment.getIsOk();
        this.filePath = subofAssignment.getFilepath();
        this.userInfoDto = new UserInfoDto(subofAssignment.getUser());
    }
}
