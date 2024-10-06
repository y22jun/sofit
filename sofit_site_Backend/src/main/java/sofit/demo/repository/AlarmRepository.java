package sofit.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sofit.demo.domain.Alarm;

import java.util.List;

@Repository
public interface AlarmRepository extends JpaRepository<Alarm, Long> {
    List<Alarm> findByReceiver(String nickName);
    @Query("SELECT COUNT(*) FROM Alarm a WHERE a.receiver =:nickName and a.isRead=false ")
    Long countAlarms(String nickName);

}
