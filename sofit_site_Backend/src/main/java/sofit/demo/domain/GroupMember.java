package sofit.demo.domain;

import jakarta.persistence.*;

@Table(name = "group_member")
@Entity
public class GroupMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_member_id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;
    @Column(name = "group_role")
    private boolean groupRole;
    @Column(name="weekly_score")
    private Double weeklyScore=0.0;

    @Column(name = "total_score")
    private Double totalScore=0.0;

    public Double getWeeklyScore() {
        return weeklyScore;
    }

    public void setWeeklyScore(Double weeklyScore) {
        this.weeklyScore = weeklyScore;
    }

    public Double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Double totalScore) {
        this.totalScore = totalScore;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public boolean isGroupRole() {
        return groupRole;
    }

    public void setGroupRole(boolean groupRole) {
        this.groupRole = groupRole;
    }


}
