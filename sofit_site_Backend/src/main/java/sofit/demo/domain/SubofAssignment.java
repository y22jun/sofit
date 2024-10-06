package sofit.demo.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "subofassignment")
@Entity
@Getter
@NoArgsConstructor
public class SubofAssignment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Setter
    @Column(nullable = true)
    private String filepath;

    @Setter
    private int isOk = 0;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    @Setter
    @ManyToOne
    @JoinColumn(name = "assignment_id")
    private Assignment assignment;

    // @ManyToOne
    // private WeeklyPoint weeklyPoint;

    // public WeeklyPoint getWeeklyPoint() {
    //     return weeklyPoint;
    // }

    // public void setWeeklyPoint(WeeklyPoint weeklyPoint) {
    //     this.weeklyPoint = weeklyPoint;
    // }

    @Builder
    public SubofAssignment(int isOk) {
        this.isOk = 0;
    }

    public void updateFilePath(String filePath) {
        this.filepath = filePath;
    }

    public void save() {
        this.isOk = 1;
    }

    public void confirmWriter(User user) {
        this.user = user;
        user.addSubofAssignment(this);
    }

    public void confirmAssignment(Assignment assignment) {
        this.assignment = assignment;
        assignment.addAssignment(this);
    }
}
