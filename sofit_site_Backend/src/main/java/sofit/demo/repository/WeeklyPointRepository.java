package sofit.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sofit.demo.domain.CheckPoint;
import sofit.demo.domain.User;
import sofit.demo.domain.WeeklyPoint;
import sofit.demo.dto.weekly.WeeklyInfoDto;

import java.util.List;

public interface WeeklyPointRepository extends JpaRepository<WeeklyPoint, Long> {

    // 전체 과제 개수
    @Query("SELECT COUNT(a) FROM Assignment a WHERE a.user = :groupLeader and a.weeklyPoint.checkPoint = :checkPoint")
    Double countAllAssignment(@Param("groupLeader") User groupLeader, @Param("checkPoint") CheckPoint checkPoint);

    // 제출한 전체 개수
    @Query("select count(s) from SubofAssignment s Join Assignment a ON a.id = s.assignment.id where s.user = :user and s.isOk = 1 and a.weeklyPoint.checkPoint =:checkPoint")
    Double countUserAssignment(@Param("user") User user, @Param("checkPoint") CheckPoint checkPoint);

    // 주차 과제 개수
    @Query("select count(*) from Assignment a where a.user = :groupLeader and a.weeklyPoint = :weeklyPoint")
    Double countAllWeekAssignment(@Param("groupLeader") User groupLeader, @Param("weeklyPoint") WeeklyPoint weeklyPoint);

    // 주차별 개인 제출 개수
    @Query("SELECT COUNT(*) " +
           "FROM Assignment a " +
           "JOIN SubofAssignment s ON a.id = s.assignment.id " +
           "WHERE s.user = :user AND a.weeklyPoint = :weeklyPoint")
    Double countUserWeeklyAssignment(@Param("user") User user, @Param("weeklyPoint") WeeklyPoint weeklyPoint);

    List<WeeklyPoint> findByCheckPointId(Long checkpointId);
}
