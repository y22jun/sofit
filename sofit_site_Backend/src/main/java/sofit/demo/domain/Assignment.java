package sofit.demo.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "assignment")
@Entity
@NoArgsConstructor
@Getter
public class Assignment extends BaseTimeEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @ManyToOne
    @JoinColumn(name = "weekly_id")
    private WeeklyPoint weeklyPoint;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Assignment(String title, boolean isOk) {
        this.title = title;
    }

    public void updateTitle(String title) {
        this.title = title;
    }

    @OneToMany(mappedBy = "assignment", orphanRemoval = true)
    private List<SubofAssignment> subofAssignmentList = new ArrayList<>();

    public void confirmWeekly(WeeklyPoint weeklyPoint) {
        this.weeklyPoint = weeklyPoint;
        weeklyPoint.addWeekly(this);
    }

    public void confirmWriter(User user) {
        this.user = user;
        user.addAssignment(this);
    }

    public void addAssignment(SubofAssignment subofAssignment) {
        subofAssignmentList.add(subofAssignment);
    }
}
