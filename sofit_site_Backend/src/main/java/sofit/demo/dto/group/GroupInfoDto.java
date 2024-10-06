
package sofit.demo.dto.group;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sofit.demo.domain.Group;
import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
public class GroupInfoDto {
    private String groupLeader;
    private String groupName;
    private int groupPrivate;
    private Long groupMaxPeople;
    private Long groupId;
    private Long groupNumPeople;
    private int groupIsLeader;
    private LocalDateTime createdAt;
    
    public GroupInfoDto(Group group,int groupIsLeader){
        this.groupLeader = group.getGroupLeader();
        this.groupMaxPeople= group.getGroupMaxPeople();
        this.groupName = group.getGroupName();
        this.groupPrivate = group.getGroupPrivate();
        this.groupNumPeople = group.getGroupNumPeople();
        this.groupId=group.getId();
        this.groupIsLeader=groupIsLeader;
        this.createdAt = getCreatedAt();
    }
    @Builder
    public GroupInfoDto(Group group){
        this.groupLeader = group.getGroupLeader();
        this.groupMaxPeople= group.getGroupMaxPeople();
        this.groupName = group.getGroupName();
        this.groupPrivate = group.getGroupPrivate();
        this.groupNumPeople = group.getGroupNumPeople();
        this.groupId=group.getId();
        this.createdAt = group.getCreatedDate();

    }
}
