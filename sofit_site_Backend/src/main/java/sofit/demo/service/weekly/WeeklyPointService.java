package sofit.demo.service.weekly;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import sofit.demo.domain.*;
import sofit.demo.dto.alarm.AlarmInfoDto;
import sofit.demo.dto.checkpoint.CheckPointInfoDto;
import sofit.demo.dto.weekly.WeeklyDto;
import sofit.demo.dto.weekly.WeeklyInfoDto;
//import sofit.demo.dto.weekly.WeeklySaveDto;
import sofit.demo.dto.weekly.WeeklyUpdateDto;
import sofit.demo.global.util.SecurityUtil;
import sofit.demo.repository.CheckPointRepository;
import sofit.demo.repository.GroupMemberRepository;
import sofit.demo.repository.UserRepository;
import sofit.demo.repository.WeeklyPointRepository;
import sofit.demo.service.user.UserService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class WeeklyPointService {
    
    private final WeeklyPointRepository weeklyPointRepository;
    private final UserRepository userRepository;
    private final CheckPointRepository checkPointRepository;
    private final UserService userService;
    private final GroupMemberRepository groupMemberRepository;

    public List<WeeklyDto> getWeekly(Long checkPointId) {
        List<WeeklyPoint> weeklyInfoDtos = weeklyPointRepository.findByCheckPointId(checkPointId);
        return weeklyInfoDtos.stream()
                .map(weeklyPoint -> new WeeklyDto(weeklyPoint.getId(), weeklyPoint.getCreatedDate()))
                .collect(Collectors.toList());
    }

    public WeeklyDto save(Long checkpointId) {
        WeeklyPoint weeklyPoint = new WeeklyPoint();

        weeklyPoint.confirmWriter(userRepository.findByEmail(SecurityUtil.getLoginUsername()).orElseThrow(() -> new IllegalArgumentException("user")));

        weeklyPoint.confirmCheckPoint(checkPointRepository.findById(checkpointId).orElseThrow(() -> new IllegalArgumentException("checkpoint")));

        weeklyPointRepository.save(weeklyPoint);

        List<WeeklyPoint> weeklyPoint1 = weeklyPointRepository.findByCheckPointId(checkpointId);
        WeeklyPoint weeklyPoint2 = weeklyPoint1.get(0);
        return WeeklyDto.builder()
                .createdAt(weeklyPoint2.getCreatedDate())
                .id(weeklyPoint2.getId())
                .build();

    }

    public void update(Long id, WeeklyUpdateDto weeklyUpdateDto) {
        WeeklyPoint weeklyPoint = weeklyPointRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("xx"));
        checkAuthority(weeklyPoint);

    }

    public void delete(Long id) {
        WeeklyPoint weeklyPoint = weeklyPointRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("xx"));
        checkAuthority(weeklyPoint);
        weeklyPointRepository.delete(weeklyPoint);
    }

    // public CheckPointInfoDto countWeekAssignment(Long id){
    //     User user = userService.findByEmail();
    //     WeeklyPoint weeklyPoint = weeklyPointRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("xx"));

    //     CheckPoint checkPoint = weeklyPoint.getCheckPoint();
    //     Group group = checkPoint.getGroup();
    //     Optional<User> optionalUser = userRepository.findByNickname(group.getGroupLeader());
    //     if(optionalUser.isPresent()){
    //         User groupLeader = optionalUser.get();
    //         GroupMember groupMember = groupMemberRepository.findByUserAndGroup(user,group);
    //         groupMember.setWeeklyScore(weeklyPointRepository.countUserWeeklyAssignment(user,weeklyPoint)/weeklyPointRepository.countAllWeekAssignment(groupLeader,weeklyPoint)*100);
    //         groupMember.setTotalScore(weeklyPointRepository.countUserAssignment(user)/weeklyPointRepository.countAllAssignment(groupLeader)*100);
    //         groupMemberRepository.save(groupMember);
            
    //         CheckPointInfoDto cDto = new CheckPointInfoDto();
    //         cDto.setNickname(user.getNickname());
    //         cDto.setTotalScore(weeklyPointRepository.countUserWeeklyAssignment(user,weeklyPoint)/weeklyPointRepository.countAllWeekAssignment(groupLeader,weeklyPoint)*100);
    //         return cDto;
    //     }
    //     return null;
    // }

    public void checkAuthority(WeeklyPoint weeklyPoint) {
        if(!weeklyPoint.getUser().getEmail().equals(SecurityUtil.getLoginUsername())) 
            throw new IllegalArgumentException("x");
    }
}
