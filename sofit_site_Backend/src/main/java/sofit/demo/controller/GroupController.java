package sofit.demo.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sofit.demo.domain.User;
import sofit.demo.dto.board.BoardInfoDto;
import sofit.demo.dto.board.BoardSaveDto;
import sofit.demo.dto.group.*;
import sofit.demo.global.template.ResponseTemplate;
import sofit.demo.repository.GroupMemberRepository;
import sofit.demo.repository.GroupRepository;
import sofit.demo.service.group.GroupService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import sofit.demo.service.user.UserService;


@RestController
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;
    private final GroupRepository groupRepository;
    private final GroupMemberRepository groupMemberRepository;
    private final UserService userService;
    //그룹 생성
    @PostMapping("/groups/create")
    public void create(@RequestBody GroupSaveDto groupSaveDto){
        groupService.create(groupSaveDto);}
    //공개 그룹 참가
    @PostMapping("/groups/join/{id}")
    public ResponseTemplate<?> join(@PathVariable("id")Long id){
        if(groupService.join(id)){
            return new ResponseTemplate<>(HttpStatus.OK,"그룹 참가 성공");
        }
        return new ResponseTemplate<>(HttpStatus.OK,"그룹 참가 실패");
    }
    //비공개 방 참가
    @PostMapping("/groups/password/{id}")
    public ResponseTemplate<?> password(@PathVariable("id")Long id,@RequestBody GroupPassword groupPassword){

        if(groupService.joinPassword(id,groupPassword)){
            return new ResponseTemplate<>(HttpStatus.OK,"그룹 참가 성공");
        }
        return new ResponseTemplate<>(HttpStatus.BAD_REQUEST, "그룹 참가 실패");
    }
    @GetMapping("/groups/changeStatus/{id}")
    public ResponseTemplate<?> getchangeStatus(@PathVariable("id")Long id){
        return new ResponseTemplate<>(HttpStatus.OK,"선택한 그룹 설정 조회 성공",groupService.getOneGroup(id));
    }
    // 비공개, 그룹 전환
    @PostMapping("/groups/changeStatus/{id}")
    public ResponseTemplate<?> changeStatus(@PathVariable("id")Long id, @RequestBody GroupPassword groupPassword){
        groupService.chageStatus(id,groupPassword);
        return new ResponseTemplate<>(HttpStatus.OK,"그룹 상태 전환 성공");
    }



    //그룹 탈퇴
    @DeleteMapping("/groups/leave/{id}")
    public ResponseTemplate<?> leave(@PathVariable("id")Long id){
        if(groupService.leave(id)){
            return new ResponseTemplate<>(HttpStatus.OK,"그룹 탈퇴 성공");
        }
        return new ResponseTemplate<>(HttpStatus.OK,"그룹 탈퇴 실패");
    }
    //그룹 삭제
    @DeleteMapping("/groups/delete/{id}")
    public ResponseTemplate<?> delete(@PathVariable("id")Long id){

        if(groupService.delete(id)){
            return new ResponseTemplate<>(HttpStatus.OK,"그룹 삭제 성공");
        }
        return new ResponseTemplate<>(HttpStatus.OK,"그룹 삭제 실패");
    }
    //모든 그룹 리스트
    @GetMapping("/groups/all")
    public ResponseTemplate<?> getAllGroups(){
        return new ResponseTemplate<>(HttpStatus.OK, "전체 그룹 조회 성공", groupService.getAllGroups());
    }
    //나의 그룹 리스트
    @GetMapping("/groups/myGroups")
    public ResponseTemplate<?> getMyGroups(){
        return new ResponseTemplate<>(HttpStatus.OK, "참여중인 그룹 조회 성공", groupService.getMyGroups());
    }

    @PostMapping("/groups/pw")
    public ResponseTemplate<?> passwordCheck(@RequestBody GroupPassWordCheckDto groupPassWordCheckDto) {
        if(groupService.checkGroupPassword(groupPassWordCheckDto)) {
            return new ResponseTemplate<>(HttpStatus.OK, "그룹 비밀번호 일치");
        }
        return new ResponseTemplate<>(HttpStatus.BAD_REQUEST, "그룹 비밀번호 불일치");
    }
    @GetMapping("/groups/{id}")
    public ResponseTemplate<?> getSelectGroup(@PathVariable("id")Long id){

        return new ResponseTemplate<>(HttpStatus.OK,"선택한 그룹 조회 성공",groupService.getOneGroup(id));
    }

    // @GetMapping("/groups/search")
    // public Page<GroupInfoDto> searchGroupTitle(@RequestParam String keyword, Pageable pageable) {
    //     return groupService.searchingGroupList(keyword, pageable);
    // }


    @GetMapping("/groups/search")
    public ResponseTemplate<Page<GroupInfoDto>> searchGroupTitle(@RequestParam String keyword, Pageable pageable) {
        Page<GroupInfoDto> result = groupService.searchingGroupList(keyword, pageable);
        return new ResponseTemplate<>(HttpStatus.OK, "검색 성공", result);
    }

    @PostMapping("/groups/numPeople/{id}")
    public ResponseTemplate<?> setMaxPeople(@RequestBody GroupNumDto groupNumDto,@PathVariable("id") Long id){
        if(groupService.setMaxPeople(id,groupNumDto)){
            return new ResponseTemplate<>(HttpStatus.OK,"그룹 인원 수정완료");
        }
        return new ResponseTemplate<>(HttpStatus.OK,"현재인원보다 바꾸려는 인원보다 작습니다.");
    }

}
