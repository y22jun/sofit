
package sofit.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import sofit.demo.dto.alarm.AlarmInfoDto;
import sofit.demo.global.template.ResponseTemplate;
import sofit.demo.service.alarm.AlarmService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AlarmController {
    private final AlarmService alarmService;

    @GetMapping("/alarm/list")
    public ResponseTemplate<?> getAllAlarms() {
        return new ResponseTemplate<>(HttpStatus.OK, "알람 조회 성공",alarmService.getMyAlarm());

    }
    @GetMapping("/alarm/count")
    public ResponseTemplate<?> getCountAlarms(){
        return new ResponseTemplate<>(HttpStatus.OK, "알람 개수 조회 성공",alarmService.getCountAlarm());
    }
}
