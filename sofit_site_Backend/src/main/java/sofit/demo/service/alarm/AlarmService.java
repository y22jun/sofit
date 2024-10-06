package sofit.demo.service.alarm;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sofit.demo.domain.Alarm;
import sofit.demo.domain.User;
import sofit.demo.dto.alarm.AlarmInfoDto;
import sofit.demo.repository.AlarmRepository;
import sofit.demo.service.user.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AlarmService {
    private final UserService userService;
    private final AlarmRepository alarmRepository;

    public List<AlarmInfoDto> getMyAlarm() {
        User user = userService.findByEmail();
        List<Alarm> alarms = alarmRepository.findByReceiver(user.getNickname());
        alarms.forEach(alarm -> {
            if (!alarm.isRead()) {
                alarm.updateRead();
                alarmRepository.save(alarm); // 업데이트된 알람을 저장
            }
        });
        return alarms.stream().map(AlarmInfoDto::from)
                .collect(Collectors.toList());
    }
    public Long getCountAlarm(){
        User user = userService.findByEmail();
        return alarmRepository.countAlarms(user.getNickname());
    }
}
