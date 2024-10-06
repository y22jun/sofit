package sofit.demo.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "checkpoint")
@Getter
@NoArgsConstructor
@Entity
public class CheckPoint extends BaseTimeEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "checkPoint", orphanRemoval = true) 
    private List<WeeklyPoint> weeklyPointList = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    @Builder
    public CheckPoint(String title) {
        this.title = title;
    }

    public void updateTitle(String title) {
        this.title = title;
    }

    public void confirmWriter(User user) {
        this.user = user;
        user.addCheckPoint(this);
    }

    public void addWeeklyPoint(WeeklyPoint weeklyPoint) {
        weeklyPointList.add(weeklyPoint);
    }

    public void confirmGroup(Group group) {
        this.group = group;
        group.addCheckPoint(this);
    }
}
