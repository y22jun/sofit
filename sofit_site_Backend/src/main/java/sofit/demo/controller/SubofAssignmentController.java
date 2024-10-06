package sofit.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import sofit.demo.dto.sub.SubInfoDto;
import sofit.demo.global.template.ResponseTemplate;
import sofit.demo.service.sub.SubofAssignmentService;

@RestController
@RequiredArgsConstructor
public class SubofAssignmentController {
    
    private final SubofAssignmentService service;

    @PostMapping(value = "/subofassignment/{assignmentId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseTemplate<?> save(@PathVariable("assignmentId") Long assignmentId, @RequestPart(value = "file", required = true) MultipartFile multipartFile) {
        service.save(assignmentId, multipartFile);
        return new ResponseTemplate<>(HttpStatus.OK,"subofassignment 생성 성공");
    }

    @PutMapping("/subofassignment/{subofassignmentId}")
    public ResponseTemplate<?> update(@PathVariable("subofassignmentId") Long subofassignmentId, @RequestPart(required = false) MultipartFile multipartFile) {
        service.update(subofassignmentId, multipartFile);
        return new ResponseTemplate<>(HttpStatus.OK,"과제 제출란 수정 성공");
    }

    @DeleteMapping("/subofassignment/{subofassignmentId}")
    public ResponseTemplate<?> delete(@PathVariable("subofassignmentId") Long subofassignmenttId) {
        service.delete(subofassignmenttId);
        return new ResponseTemplate<>(HttpStatus.OK, "과제 제출란 삭제 성공");
    }

    @GetMapping("/subofassignment/{subofassignmentId}")
    public ResponseEntity<SubInfoDto> getInfo(@PathVariable("subofassignmentId") Long subofassignmentId) {
        return ResponseEntity.ok(service.getSubInfo(subofassignmentId));
    }

    @GetMapping("/{assignmentId}/subofassignment")
    public ResponseTemplate<?> getInfoAll(@PathVariable("assignmentId") Long assignmentId) {
        return new ResponseTemplate<>(HttpStatus.OK, "해당 주자 과제에 대한 과제 제출여부 조회 성공", service.getSubOfAssignment(assignmentId));
    }
}
