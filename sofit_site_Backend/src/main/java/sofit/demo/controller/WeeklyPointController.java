package sofit.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import sofit.demo.dto.checkpoint.CheckPointDto;
import sofit.demo.dto.checkpoint.CheckPointInfoDto;
import sofit.demo.dto.weekly.WeeklyDto;

import sofit.demo.dto.weekly.WeeklyUpdateDto;
import sofit.demo.global.template.ResponseTemplate;
import sofit.demo.service.weekly.WeeklyPointService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class WeeklyPointController {
    
    private final WeeklyPointService weeklyPointService;

    @PostMapping("/weekly/{checkpointId}")
    public ResponseTemplate<WeeklyDto> save(@PathVariable("checkpointId") Long checkpointId) {

        return new ResponseTemplate<>(HttpStatus.OK, "주간 목표 생성 성공",weeklyPointService.save(checkpointId));
    }

    @PutMapping("/weekly/{weeklypointId}")
    public ResponseTemplate<?> update(@PathVariable("weeklypointId") Long weeklypointid, @RequestBody WeeklyUpdateDto weeklyUpdateDto) {
        weeklyPointService.update(weeklypointid, weeklyUpdateDto);
        return new ResponseTemplate<>(HttpStatus.OK,"주간 목표 수정 성공", weeklyUpdateDto);
    }

    @DeleteMapping("/weekly/{weeklypointId}")
    public ResponseTemplate<?> delete(@PathVariable("weeklypointId") Long weeklypointId) {
        weeklyPointService.delete(weeklypointId);
        return new ResponseTemplate<>(HttpStatus.OK, "주간 목표 삭제 성공");
    }


    //주간 진행률
    // @GetMapping("/weekly/{weeklypointId}")
    // public ResponseEntity<CheckPointInfoDto> countUserWeeklyPoint(@PathVariable("weeklypointId")Long weeklypointId){
    //     return ResponseEntity.ok(weeklyPointService.countWeekAssignment(weeklypointId));
    // }

    @GetMapping("/weeoklyinfo/{checkPointId}")
    public ResponseTemplate<List<WeeklyDto>> getWeeklyAll(@PathVariable("checkPointId")Long checkPointId)
    {
        return new ResponseTemplate<>(HttpStatus.OK,"해당 체크포인트 주차 조회 성공",weeklyPointService.getWeekly(checkPointId));
    }


}
