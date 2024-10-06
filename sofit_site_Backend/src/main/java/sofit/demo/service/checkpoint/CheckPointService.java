package sofit.demo.service.checkpoint;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import sofit.demo.domain.*;
import sofit.demo.dto.checkpoint.CheckPointDto;
import sofit.demo.dto.checkpoint.CheckPointInfoDto;
import sofit.demo.dto.checkpoint.CheckPointSaveDto;
import sofit.demo.dto.checkpoint.CheckPointUpdateDto;
import sofit.demo.dto.user.UserInfoDto;
import sofit.demo.global.util.SecurityUtil;
import sofit.demo.repository.*;
import sofit.demo.service.user.UserService;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class CheckPointService {

    private final CheckPointRepository checkPointRepository;
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final UserService userService;
    private final WeeklyPointRepository weeklyPointRepository;
    private final GroupMemberRepository groupMemberRepository;

    public CheckPointDto getGoal(Long groupId) {
        CheckPoint checkPoint = checkPointRepository.findByGroupId(groupId);
        return checkPoint != null ? CheckPointDto.builder()
                .createdAt(checkPoint.getCreatedDate())
                .goal(checkPoint.getTitle())
                .id(checkPoint.getId())
                .build() : null;
    }
    
    public CheckPointDto save(Long groupId, CheckPointSaveDto checkPointSaveDto) {

        CheckPoint newCheckPoint = checkPointSaveDto.toEntity();
    
        newCheckPoint.confirmWriter(userRepository.findByEmail(SecurityUtil.getLoginUsername())
                .orElseThrow(() -> new IllegalArgumentException("로그인된 사용자를 찾을 수 없습니다.")));
    
                newCheckPoint.confirmGroup(groupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("그룹을 찾을 수 없습니다.")));
        checkPointRepository.save(newCheckPoint);

        CheckPoint checkPoint = checkPointRepository.findByGroupId(groupId);
        
        return CheckPointDto.builder()
        .createdAt(checkPoint.getCreatedDate())
        .goal(checkPoint.getTitle())
        .id(checkPoint.getId())
        .build();
    }
    

    public void update(Long id, CheckPointUpdateDto checkPointUpdateDto) {
        CheckPoint checkPoint = checkPointRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("xx"));
        checkAuthority(checkPoint);
        checkPointUpdateDto.title().ifPresent(checkPoint::updateTitle);
    }

    public void delete(Long id) {
        CheckPoint checkPoint = checkPointRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("xx"));
        checkAuthority(checkPoint);
        checkPointRepository.delete(checkPoint);
    }
    // public List<CheckPointInfoDto> countAllAssignment(Long checkpointId) {
    //     Map<String,Double> userTotalScore = new HashMap<>();

    //     Optional<CheckPoint> optionalCheckPoint = checkPointRepository.findById(checkpointId);
    //     if (optionalCheckPoint.isPresent()) {
    //         CheckPoint checkPoint = optionalCheckPoint.get();
    //         String checkPointTitle = checkPoint.getTitle();
    //         Group group = checkPoint.getGroup();
    //         List<GroupMember> groupMemberList = groupMemberRepository.findByGroup(group);
    //         for (GroupMember member : groupMemberList) {
    //             Long userId = member.getUser().getId(); // GroupMember 엔티티의 user_id
    //             Optional<User> optionalUser = userRepository.findById(userId);
    //             if (optionalUser.isPresent()) {
    //                 User user = optionalUser.get();
    //                 String nickname = user.getNickname(); // 사용자의 nickname
    //                 double totalScore = groupMemberRepository.findByUserAndGroup(user,group).getTotalScore(); // 사용자의 총 점수를 계산하는 메서드 호출
    //                 userTotalScore.put(nickname, totalScore); // Map에 사용자의 이름과 총 점수 저장
    //             }
    //         }
    //     }
    //     List<CheckPointInfoDto> resultList = new ArrayList<>();
    //     for (Map.Entry<String, Double> entry : userTotalScore.entrySet()) {
    //         CheckPointInfoDto dto = new CheckPointInfoDto();
    //         dto.setNickname(entry.getKey());
    //         dto.setTotalScore(entry.getValue());
    //         dto.setTitle(entry.getKey());
    //         resultList.add(dto);
    //     }

    //     return resultList;
    // }
    public List<CheckPointInfoDto> countAllAssignment(Long checkpointId) {
        Map<String, Double> userTotalScore = new HashMap<>();

        Optional<CheckPoint> optionalCheckPoint = checkPointRepository.findById(checkpointId);
        if (optionalCheckPoint.isPresent()) {
            CheckPoint checkPoint = optionalCheckPoint.get();
            String checkPointTitle = checkPoint.getTitle(); // Get the title from CheckPoint
            Group group = checkPoint.getGroup();
            List<GroupMember> groupMemberList = groupMemberRepository.findByGroup(group);
            for (GroupMember member : groupMemberList) {
                Long userId = member.getUser().getId(); // GroupMember 엔티티의 user_id
                Optional<User> optionalUser = userRepository.findById(userId);
                if (optionalUser.isPresent()) {
                    User user = optionalUser.get();
                    String nickname = user.getNickname(); // 사용자의 nickname
                    double totalScore = groupMemberRepository.findByUserAndGroup(user, group).getTotalScore(); // 사용자의 총 점수를 계산하는 메서드 호출
                    userTotalScore.put(nickname, totalScore); // Map에 사용자의 이름과 총 점수 저장
                }
            }

            List<CheckPointInfoDto> resultList = new ArrayList<>();
            for (Map.Entry<String, Double> entry : userTotalScore.entrySet()) {
                CheckPointInfoDto dto = new CheckPointInfoDto();
                dto.setNickname(entry.getKey());
                dto.setTotalScore(entry.getValue());
                dto.setTitle(checkPointTitle);
                resultList.add(dto);
            }

            return resultList;
        } else {
            return Collections.emptyList();
        }
    }

    public void checkAuthority(CheckPoint checkPoint) {
        if(!checkPoint.getUser().getEmail().equals(SecurityUtil.getLoginUsername())) 
            throw new IllegalArgumentException("x");
    }

    public List<String> getGroupUsers(Long groupId){
        Optional<Group> optionalGroup = groupRepository.findById(groupId);
        Group group = optionalGroup.get();
        List<GroupMember> groupMembers = groupMemberRepository.findByGroup(group);
        List<String> userNicknames = new ArrayList<>();
        userNicknames.add("리더 : " + group.getGroupLeader());
        if (groupMembers != null && !groupMembers.isEmpty()) {
            // Index-based iteration starting from the second element (index 1)
            for (int i = 1; i < groupMembers.size(); i++) {
                GroupMember groupMember = groupMembers.get(i);
                User user = groupMember.getUser();
                String userNickname = user.getNickname(); // User 객체에서 닉네임을 가져옵니다.
                userNicknames.add(userNickname);
            }
        }
         else {
            // groupMembers가 null이거나 비어 있을 때의 처리
            System.out.println("Group members list is null or empty.");
        }
        return userNicknames;
    }


}
