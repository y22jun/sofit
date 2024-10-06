package sofit.demo.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "group_table")
@Getter
public class Group extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    private Long id;
    @Column(name="group_name")
    private String groupName;
    @Column(name="group_password")
    private String groupPassword;
    @Column(name="group_private")
    private int groupPrivate=0;
    @Column(name="group_max_people")
    private Long groupMaxPeople;
    @Column(name="group_num_people")
    private Long groupNumPeople=1L;
    @Column(name = "group_user_name")
    private String groupLeader;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupPassword() {
        return groupPassword;
    }

    public void setGroupPassword(String groupPassword) {
        this.groupPassword = groupPassword;
    }

    public int getGroupPrivate() {
        return groupPrivate;
    }

    public void setGroupPrivate(int groupPrivate) {
        this.groupPrivate = groupPrivate;
    }

    public Long getGroupMaxPeople() {
        return groupMaxPeople;
    }

    public void setGroupMaxPeople(Long groupMaxPeople) {
        this.groupMaxPeople = groupMaxPeople;
    }

    public Long getGroupNumPeople() {
        return groupNumPeople;
    }

    public void setGroupNumPeople(Long groupNumPeople) {
        this.groupNumPeople = groupNumPeople;
    }

    public String getGroupLeader() {
        return groupLeader;
    }

    public void setGroupLeader(String groupLeader) {
        this.groupLeader = groupLeader;
    }

    public List<GroupMember> getGroupMembers() {
        return groupMembers;
    }

    public void setGroupMembers(List<GroupMember> groupMembers) {
        this.groupMembers = groupMembers;
    }

    public List<CheckPoint> getCheckPointList() {
        return checkPointList;
    }

    public void setCheckPointList(List<CheckPoint> checkPointList) {
        this.checkPointList = checkPointList;
    }

    @OneToMany(mappedBy = "group")
    private List<GroupMember> groupMembers;


    @OneToMany(mappedBy = "group", orphanRemoval = true)
    private List<CheckPoint> checkPointList = new ArrayList<>();

    public void addCheckPoint(CheckPoint checkPoint) {
        checkPointList.add(checkPoint);
    }
}
