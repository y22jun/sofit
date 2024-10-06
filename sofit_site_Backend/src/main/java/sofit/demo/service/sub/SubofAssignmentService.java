package sofit.demo.service.sub;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import sofit.demo.dto.board.BoardInfoDto;
import sofit.demo.dto.sub.SubInfoDto;
import sofit.demo.global.util.SecurityUtil;
import sofit.demo.repository.*;
import sofit.demo.service.file.FileService;
import sofit.demo.service.user.UserService;
import sofit.demo.domain.*;

@Service
@RequiredArgsConstructor
@Transactional
public class SubofAssignmentService {

    private final UserRepository userRepository;
    private final SubofAssignmentRepository subofAssignmentRepository;
    private final AssignmentRepository assignmentRepository;
    private final FileService fileService;
    private final UserService userService;
    private final WeeklyPointRepository weeklyPointRepository;
    private final GroupMemberRepository groupMemberRepository;

    public void save(Long assignmentId, MultipartFile multipartFile) {
       SubofAssignment subofAssignment = new SubofAssignment();
        subofAssignment.confirmWriter(userRepository.findByEmail(SecurityUtil.getLoginUsername()).orElseThrow(() -> new IllegalArgumentException("xxx")));
        subofAssignment.confirmAssignment(assignmentRepository.findById(assignmentId).orElseThrow(() -> new IllegalArgumentException("xx")));
        Assignment assignment = assignmentRepository.findById(assignmentId).orElseThrow(()->new IllegalArgumentException("assignment X"));

        User user = userService.findByEmail();

        WeeklyPoint weeklyPoint = assignment.getWeeklyPoint();
        CheckPoint checkPoint = weeklyPoint.getCheckPoint();
        Group group = checkPoint.getGroup();


        Optional<User> optionalUser = userRepository.findByNickname(group.getGroupLeader());
        if(optionalUser.isPresent()){
            if (multipartFile != null && !multipartFile.isEmpty()) {
                String filePath = fileService.save(multipartFile);
                subofAssignment.updateFilePath(filePath);
                subofAssignment.save();
            }
            subofAssignmentRepository.save(subofAssignment);
            User groupLeader = optionalUser.get();
            GroupMember groupMember = groupMemberRepository.findByUserAndGroup(user,group);
            groupMember.setWeeklyScore(weeklyPointRepository.countUserWeeklyAssignment(user,weeklyPoint)/weeklyPointRepository.countAllWeekAssignment(groupLeader,weeklyPoint)*100);
            groupMember.setTotalScore(weeklyPointRepository.countUserAssignment(user,checkPoint)/weeklyPointRepository.countAllAssignment(groupLeader,checkPoint)*100);
            groupMemberRepository.save(groupMember);

        }

    }


    public void update(Long id, MultipartFile multipartFile) {

        SubofAssignment subofAssignment = subofAssignmentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("x"));
        checkAuthority(subofAssignment);
        if(subofAssignment.getFilepath() != null) {
            fileService.delete(subofAssignment.getFilepath());
        }

        if (multipartFile != null && !multipartFile.isEmpty()) {
            String filePath = fileService.save(multipartFile);
            subofAssignment.updateFilePath(filePath);
            subofAssignment.save();
        }

    }

    public void delete(Long id) {
        SubofAssignment subofAssignment = subofAssignmentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("x"));
        checkAuthority(subofAssignment);
        if(subofAssignment.getFilepath() != null) {
            fileService.delete(subofAssignment.getFilepath());
        }

        subofAssignmentRepository.delete(subofAssignment);
    }

    public void checkAuthority(SubofAssignment subofAssignment) {
        if(!subofAssignment.getUser().getEmail().equals(SecurityUtil.getLoginUsername()))
            throw new IllegalArgumentException("x");
    }

    public SubInfoDto getSubInfo(Long id) {
        return new SubInfoDto(subofAssignmentRepository.findWithUserById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 과제물입니다.")));
    }

    public List<SubInfoDto> getSubOfAssignment(Long assignmentId){
        Optional<Assignment> assignment = assignmentRepository.findById(assignmentId);
        Assignment assignment1 = assignment.get();
        List<SubofAssignment> subofAssignments = subofAssignmentRepository.findByAssignment(assignment1);
        return subofAssignments.stream().map(SubInfoDto::new)
                .collect(Collectors.toList());
    }
}
