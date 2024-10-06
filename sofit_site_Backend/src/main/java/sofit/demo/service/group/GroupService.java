
package sofit.demo.service.group;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sofit.demo.domain.*;
import sofit.demo.dto.group.*;
import sofit.demo.global.util.SecurityUtil;
import sofit.demo.repository.AlarmRepository;
import sofit.demo.repository.GroupMemberRepository;
import sofit.demo.repository.GroupRepository;
import sofit.demo.service.user.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class GroupService {
    private final UserService userService;
    private final GroupRepository groupRepository;
    private final GroupMemberRepository groupMemberRepository;
    private final AlarmRepository alarmRepository;

    public void create(GroupSaveDto groupSaveDto) {

        User user = userService.findByEmail();

        Group group = groupSaveDto.toEntity();
        group.setGroupLeader(user.getNickname());
        groupRepository.save(group);

        GroupMember groupMember = new GroupMember();
        groupMember.setGroup(group);
        groupMember.setGroupRole(true);
        groupMember.setUser(user);
        groupMemberRepository.save(groupMember);
    }

    public boolean join(Long id) {
        Optional<Group> optionalGroup = groupRepository.findById(id);
        User user = userService.findByEmail();
        if (optionalGroup.isPresent()) {
            Group group = optionalGroup.get();
            Optional<GroupMember> existingMember = group.getGroupMembers().stream()
                    .filter(groupMember -> groupMember.getUser().getEmail().equals(SecurityUtil.getLoginUsername()))
                    .findAny();
            if (existingMember.isPresent()) {
                throw new IllegalStateException("해당 그룹에 이미 회원이 존재합니다.");
            }
            if (!group.getGroupMaxPeople().equals(group.getGroupNumPeople())) {
                // TODO content << 넣기
                Alarm alarm = getAlarm(group, user);

                GroupMember groupMember = new GroupMember();
                group.setGroupNumPeople(group.getGroupNumPeople() + 1);
                groupMember.setUser(user);
                groupMember.setGroup(group);
                groupMember.setGroupRole(false);

                alarmRepository.save(alarm);
                groupMemberRepository.save(groupMember);
                return true;
            }
        }
        return false;

    }

    private static Alarm getAlarm(Group group, User user) {
        return Alarm.builder()
                .receiver(group.getGroupLeader())
                .sender(user.getNickname())
                .groupId(group.getId())
                .build();
    }

    /* 패스워드 로직 에러 */

    public boolean joinPassword(Long id, GroupPassword groupPassword) {
        Optional<Group> optionalGroup = groupRepository.findById(id);
        User user = userService.findByEmail();
        if (optionalGroup.isPresent()) {
            Group group = optionalGroup.get();
            Optional<GroupMember> existingMember = group.getGroupMembers().stream()
                    .filter(groupMember -> groupMember.getUser().getEmail().equals(SecurityUtil.getLoginUsername()))
                    .findAny();

            if (existingMember.isPresent()) {
                throw new IllegalStateException("해당 그룹에 이미 회원이 존재합니다.");
            }
            if (!group.getGroupMaxPeople().equals(group.getGroupNumPeople())
                    && (group.getGroupPassword().equals((groupPassword.getGroupPassword())))) {
                Alarm alarm = getAlarm(group, user);

                GroupMember groupMember = new GroupMember();
                group.setGroupNumPeople(group.getGroupNumPeople() + 1);
                groupMember.setUser(user);
                groupMember.setGroup(group);
                groupMember.setGroupRole(false);

                alarmRepository.save(alarm);
                groupMemberRepository.save(groupMember);
                return true;
            } else {
                throw new IllegalStateException("비밀번호 불일치");

            }
        }
        return false;
    }

    @Transactional
    public void chageStatus(Long id, GroupPassword groupPassword) {
        Group group = groupRepository.findById(id).orElse(null);
        if(group == null) throw new IllegalStateException("Bad Request");

        User user = userService.findByEmail();
        GroupMember groupMember = groupMemberRepository.findByUserAndGroup(user, group);

        if (groupMember.isGroupRole()) {
            int isPrivate = group.getGroupPrivate();
            group.setGroupPrivate(isPrivate == 0 ? 1 : 0);
            group.setGroupPassword(isPrivate == 0 ? groupPassword.getGroupPassword() : null);
        }
    }

    public boolean leave(Long id) {
        Optional<Group> optionalGroup = groupRepository.findById(id);
        User user = userService.findByEmail();
        if (optionalGroup.isPresent()) {
            Group group = optionalGroup.get();
            GroupMember groupMember = groupMemberRepository.findByUserAndGroup(user, group);
            checkAuthority(groupMember);
            groupMemberRepository.delete(groupMember);
            group.setGroupNumPeople(group.getGroupNumPeople() - 1L);
            groupRepository.save(group);
            return true;
        }
        return false;
    }

    public boolean delete(Long id) {
        Optional<Group> optionalGroup = groupRepository.findById(id);
        User user = userService.findByEmail();
        if (optionalGroup.isPresent()) {
            Group group = optionalGroup.get();

            GroupMember groupMember = groupMemberRepository.findByUserAndGroup(user, group);

            if (groupMember.isGroupRole()) {
                List<GroupMember> groupMemberList = groupMemberRepository.findByGroup(group);
                groupMemberRepository.deleteAll(groupMemberList);
                groupRepository.delete(group);
                return true;
            } else {
                throw new IllegalStateException("그룹 삭제 권한 없습니다.");
            }
        }
        return false;
    }

    public List<GroupInfoDto> getAllGroups() {
        return groupRepository.findAll().stream()
                .map(GroupInfoDto::new)
                .collect(Collectors.toList());
    }

    public boolean checkGroupPassword(GroupPassWordCheckDto checkGroupPassword) {
        Group group = groupRepository.findById(checkGroupPassword.getGroupId()).orElse(null);

        return group.getGroupPassword().equals(checkGroupPassword.getGroupPassword());
    }

    public List<GroupInfoDto> getMyGroups() {
        User user = userService.findByEmail();
        List<GroupMember> groupMembers = groupMemberRepository.findByUser(user);

        return groupMembers.stream()
                .map(groupMember -> new GroupInfoDto(groupMember.getGroup()))
                .collect(Collectors.toList());
    }

    public List<GroupInfoDto> getOneGroup(Long id) {
        User user = userService.findByEmail();
        Optional<Group> optionalGroup = groupRepository.findById(id);
        if (optionalGroup.isPresent()) {
            Group group = optionalGroup.get();
            GroupMember groupMember = groupMemberRepository.findByUserAndGroup(user, group);
            boolean groupIsLeader = groupMember.isGroupRole();
            return groupRepository.findById(id).stream()
                    .map(g -> new GroupInfoDto(g, groupIsLeader ? 1 : 0))
                    .collect(Collectors.toList());
        }
        return null;
    }

    public void checkAuthority(GroupMember groupMember) {
        if (!groupMember.getUser().getEmail().equals(SecurityUtil.getLoginUsername()))
            throw new IllegalArgumentException("x");
    }

    public Page<GroupInfoDto> searchingGroupList(String keyword, Pageable pageable) {
        Page<Group> groups = groupRepository.findByGroupNameContaining(keyword, pageable);
        return getGroupResponseDTOS(pageable, groups);
    }

    private Page<GroupInfoDto> getGroupResponseDTOS(Pageable pageable, Page<Group> groups) {
        List<GroupInfoDto> groupInfoDtos = new ArrayList<>();
        for (Group group : groups) {
            GroupInfoDto result = GroupInfoDto.builder()
                    .group(group)
                    .build();
            groupInfoDtos.add(result);
        }
        return new PageImpl<>(groupInfoDtos, pageable, groups.getTotalElements());
    }

    public boolean setMaxPeople(Long id, GroupNumDto groupNumDto) {
        System.out.println(groupNumDto.getGroupMaxPeople());
        User user = userService.findByEmail();
        Optional<Group> optionalGroup = groupRepository.findById(id);
        if (optionalGroup.isPresent()) {
            Group group = optionalGroup.get();
            GroupMember groupMember = groupMemberRepository.findByUserAndGroup(user, group);
            if (groupMember.isGroupRole()) {
                if (group.getGroupNumPeople() < groupNumDto.getGroupMaxPeople()) {
                    group.setGroupMaxPeople(groupNumDto.getGroupMaxPeople());
                    groupRepository.save(group);
                    return true;
                } else {
                    throw new IllegalStateException("현재인원보다 적게 설정하셨습니다.");

                }
            }
        }
        return false;
    }

}
