package sofit.demo.service.board.assignment;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import sofit.demo.domain.Assignment;
import sofit.demo.domain.SubofAssignment;
import sofit.demo.domain.WeeklyPoint;
import sofit.demo.dto.assignment.AssignmentInfoDto;
import sofit.demo.dto.assignment.AssignmentSaveDto;
import sofit.demo.dto.assignment.AssignmentUpdateDto;
import sofit.demo.dto.sub.SubInfoDto;
import sofit.demo.dto.weekly.WeeklyInfoDto;
import sofit.demo.global.util.SecurityUtil;
import sofit.demo.repository.AssignmentRepository;
import sofit.demo.repository.SubofAssignmentRepository;
import sofit.demo.repository.UserRepository;
import sofit.demo.repository.WeeklyPointRepository;
import sofit.demo.service.file.FileService;
import sofit.demo.service.user.UserService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AssignmentService {
    
    private final AssignmentRepository assignmentRepository;
    private final WeeklyPointRepository weeklyPointRepository;
    private final UserRepository userRepository;
    private final SubofAssignmentRepository subofAssignmentRepository;
    private final UserService userService;

    public void save(Long weeklyId, AssignmentSaveDto assignmentSaveDto) {
        Assignment assignment = assignmentSaveDto.toEntity();
        assignment.confirmWriter(userService.findByEmail());
        assignment.confirmWeekly(weeklyPointRepository.findById(weeklyId).orElseThrow(() -> new IllegalArgumentException("2")));
        assignmentRepository.save(assignment);

        SubofAssignment subofAssignment = new SubofAssignment();
        subofAssignment.setFilepath(null);
        subofAssignment.setIsOk(0);
        subofAssignment.setAssignment(assignment);
        subofAssignmentRepository.save(subofAssignment);

    }

    public List<AssignmentInfoDto> getAssignment(Long weeklyId){
        Optional<WeeklyPoint> weeklyPoint = weeklyPointRepository.findById(weeklyId);
        WeeklyPoint weeklyPoint1 = weeklyPoint.get();
        List<Assignment> assignments = assignmentRepository.findByWeeklyPoint(weeklyPoint1);
        return assignments.stream().map(AssignmentInfoDto::new)
                .collect(Collectors.toList());
    }




    public void update(Long id, AssignmentUpdateDto assignmentUpdateDto) {

        Assignment assignment = assignmentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("x"));
        checkAuthority(assignment);
        assignmentUpdateDto.title().ifPresent(assignment::updateTitle);
    }

    public void delete(Long id) {
        Assignment assignment = assignmentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("x"));
        checkAuthority(assignment);
        assignmentRepository.delete(assignment);
    }

    public void checkAuthority(Assignment assignment) {
        if(!assignment.getUser().getEmail().equals(SecurityUtil.getLoginUsername())) 
            throw new IllegalArgumentException("x");
    }

    public AssignmentInfoDto getSubInfo(Long id) {
        return new AssignmentInfoDto(assignmentRepository.findWithUserById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 과제입니다.")));
    }
}
