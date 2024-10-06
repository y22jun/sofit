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

@Table(name = "weekly")
@Getter
@NoArgsConstructor
@Entity
public class WeeklyPoint extends BaseTimeEntity{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "checkpoint_id")
    private CheckPoint checkPoint;

    // @ManyToOne
    // @JoinColumn(name = "assignment_id")
    // private Assignment assignment;

    @OneToMany(mappedBy = "weeklyPoint", orphanRemoval = true)
    public List<Assignment> assignmentList = new ArrayList<>();



    public void confirmWriter(User user) {
        this.user = user;
        user.addWeeklyPoint(this);
    }

    public void confirmCheckPoint(CheckPoint checkPoint) {
        this.checkPoint = checkPoint;
        checkPoint.addWeeklyPoint(this);
    }

    public void addWeekly(Assignment assignment) {
        assignmentList.add(assignment);
    }
}
