package sofit.demo.domain;

import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import jakarta.persistence.*;
import java.util.*;

@Getter
@NoArgsConstructor
@Entity
@Builder
@Table(name = "USERS")
@AllArgsConstructor
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String email; // 이메일
    private String password; // 비밀번호
    private String nickname; // 닉네임

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private SocialType socialType; // KAKAO, NAVER, GOOGLE

    private String socialId; // 로그인한 소셜 타입의 식별자 값 (일반 로그인인 경우 null)

    private String refreshToken; // 리프레시 토큰

    @Builder.Default
    @OneToMany(mappedBy = "user")
    private List<Board> boardList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "user" )
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<CheckPoint> checkPointList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<WeeklyPoint> weeklyPointList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Assignment> assignmentList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<SubofAssignment> subofAssignmentList = new ArrayList<>();

    // 유저 권한 설정 메소드
    public void authorizeUser() {
        this.role = Role.USER;
    }

    // 비밀번호 암호화 메소드
    public void passwordEncode(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
    }

    public void updateEmail(String email){
        this.email = email;
    }

    //== 유저 필드 업데이트 ==//
    public void updateNickname(String updateNickname) {
        this.nickname = updateNickname;
    }

    public void updatePassword(String password, PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(password);
    }

    public void updateRefreshToken(String updateRefreshToken) {
        this.refreshToken = updateRefreshToken;
    }
    @OneToMany(mappedBy = "user")
    private List<GroupMember>groupMembers;

    public void addBoard(Board board){
        //post의 user 설정은 post에서 함
        boardList.add(board);
    }

    public void addComment(Comment comment){
        //comment의 writer 설정은 comment에서 함
        commentList.add(comment);
    }

    public void addGroup(GroupMember groupMember){
        groupMembers.add(groupMember);
    }

    public boolean matchPassword(PasswordEncoder passwordEncoder, String checkPassword){
        return passwordEncoder.matches(checkPassword, getPassword());
    }

    public void addCheckPoint(CheckPoint checkPoint) {
        checkPointList.add(checkPoint);
    }

    public void addWeeklyPoint(WeeklyPoint weeklyPoint) {
        weeklyPointList.add(weeklyPoint);
    }

    public void addAssignment(Assignment assignment) {
        assignmentList.add(assignment);
    }

    public void addSubofAssignment(SubofAssignment subofAssignment) {
        subofAssignmentList.add(subofAssignment);
    }
}

