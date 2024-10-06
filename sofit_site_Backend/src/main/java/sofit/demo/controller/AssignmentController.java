package sofit.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import sofit.demo.dto.assignment.AssignmentInfoDto;
import sofit.demo.dto.assignment.AssignmentSaveDto;
import sofit.demo.dto.assignment.AssignmentUpdateDto;
import sofit.demo.dto.sub.SubInfoDto;
import sofit.demo.global.template.ResponseTemplate;
import sofit.demo.service.board.assignment.AssignmentService;

@RestController
@RequiredArgsConstructor
public class AssignmentController {
    
    private final AssignmentService assignmentService;

    @PostMapping("/assignment/{weeklyId}")
    public ResponseTemplate<?> save(@PathVariable("weeklyId") Long weeklyId, @RequestBody AssignmentSaveDto assignmentSaveDto) {
        assignmentService.save(weeklyId, assignmentSaveDto);
        return new ResponseTemplate<>(HttpStatus.OK, "과제 타이틀 생성 성공",assignmentSaveDto);
    }

    @PutMapping("/assignment/{assignmentId}")
    public  ResponseTemplate<?> update(@PathVariable("assignmentId") Long assignmentid, @RequestBody AssignmentUpdateDto assignmentUpdateDto) {
        assignmentService.update(assignmentid, assignmentUpdateDto);
        return new ResponseTemplate<>(HttpStatus.OK, "과제 타이틀 수정 성공",assignmentUpdateDto); 
    }

    @DeleteMapping("/assignment/{assignmentId}")
    public  ResponseTemplate<?> delete(@PathVariable("assignmentId") Long assignmentId) {
        assignmentService.delete(assignmentId);
        return new ResponseTemplate<>(HttpStatus.OK,"과제 삭제 성공");
    }

    @GetMapping("/assignment/{assignmentId}")
    public ResponseEntity<AssignmentInfoDto> getInfo(@PathVariable("assignmentId") Long assignmentId) {
        return ResponseEntity.ok(assignmentService.getSubInfo(assignmentId));
    }

    @GetMapping("/{weeklyId}/assignment")
    public ResponseTemplate<?> getAssignmentAll(@PathVariable("weeklyId")Long weeklyId){
        return new ResponseTemplate<>(HttpStatus.OK,"해당 주차 과제 조회 성공",assignmentService.getAssignment(weeklyId));
    }


}
