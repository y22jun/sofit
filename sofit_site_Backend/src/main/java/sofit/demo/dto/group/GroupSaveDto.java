
package sofit.demo.dto.group;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import sofit.demo.domain.Comment;
import sofit.demo.domain.Group;

public record GroupSaveDto(String groupName, String groupPassword, int groupPrivate, Long groupMaxPeople) {

    public Group toEntity() {
        Group group = new Group();
        group.setGroupName(groupName);
        group.setGroupPassword(groupPassword);
        group.setGroupPrivate(groupPrivate);
        group.setGroupMaxPeople(groupMaxPeople);
        return group;
    }
}
