package sofit.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import sofit.demo.dto.board.BoardInfoDto;
import sofit.demo.dto.checkpoint.CheckPointDto;
import sofit.demo.dto.checkpoint.CheckPointInfoDto;
import sofit.demo.dto.checkpoint.CheckPointSaveDto;
import sofit.demo.dto.checkpoint.CheckPointUpdateDto;
import sofit.demo.global.template.ResponseTemplate;
import sofit.demo.service.checkpoint.CheckPointService;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
public class CheckPointController {
    
    private final CheckPointService checkPointService;

    @GetMapping("/info/{groupId}")
    public ResponseTemplate<CheckPointDto> getGoal(@PathVariable("groupId")Long groupId){
        return new ResponseTemplate<>(HttpStatus.OK, "최종 목표 조회 성공", checkPointService.getGoal(groupId));
    }

    @PostMapping("/checkpoint/{groupId}")
    public ResponseTemplate<CheckPointDto> save(@PathVariable("groupId") Long groupId, @RequestBody CheckPointSaveDto checkPointSaveDto) {
        
        return new ResponseTemplate<>(HttpStatus.CREATED, "최종 목표 생성 성공", checkPointService.save(groupId, checkPointSaveDto));
    }

    @PutMapping("/checkpoint/{checkpointId}")
    public void update(@PathVariable("groupId") Long checkpointId, @RequestBody CheckPointUpdateDto checkPointUpdateDto) {
        checkPointService.update(checkpointId, checkPointUpdateDto);
    }

    @DeleteMapping("/checkpoint/{checkpointId}")
    public void delete(@PathVariable("checkpointId") Long checkpointId) {
        checkPointService.delete(checkpointId);
    }

    //그룹원 전체 진행률
    @GetMapping("/checkpoint/{checkpointId}")
    public ResponseEntity <List<CheckPointInfoDto>>countAllAssignment(@PathVariable("checkpointId")Long checkpointId){
        return ResponseEntity.ok(checkPointService.countAllAssignment(checkpointId));
    }

    @GetMapping("/checkpoint/getGroupUsers/{groupId}")
    public ResponseEntity<List<String>> getGroupUsers(@PathVariable("groupId") Long groupId) {
        try {
            List<String> userNicknames = checkPointService.getGroupUsers(groupId);
            return ResponseEntity.ok(userNicknames);
        } catch (NoSuchElementException e) {
            // 그룹이 존재하지 않는 경우의 처리
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            // 기타 예외 처리
            return ResponseEntity.status(500).build();
        }
    }
}
