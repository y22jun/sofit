package sofit.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import sofit.demo.dto.email.EmailCheckDto;
import sofit.demo.dto.email.EmailRequestDto;
import sofit.demo.dto.user.UpdatePasswordDto;
import sofit.demo.dto.user.UserInfoDto;
import sofit.demo.dto.user.UserSignUpDto;
import sofit.demo.dto.user.UserUpdateDto;
import sofit.demo.dto.user.UserWithdrawDto;
import sofit.demo.global.template.ResponseTemplate;
import sofit.demo.global.util.SecurityUtil;
import sofit.demo.service.email.EmailService;
import sofit.demo.service.user.UserService;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final EmailService emailService;

    @PostMapping("/signUp")
    public ResponseTemplate<?> signUp(@RequestBody UserSignUpDto userSignUpDto) throws Exception {
        userService.signUp(userSignUpDto);
        return new ResponseTemplate<>(HttpStatus.CREATED, "회원가입 성공");
    }

    @PostMapping("/email/path")
    public ResponseTemplate<?> mailSend(@RequestBody @Valid EmailRequestDto emailRequestDto) {
        System.out.println("이메일 인증 이메일: " + emailRequestDto.getEmail());
        return new ResponseTemplate<>(HttpStatus.OK, "인증번호 전송 성공", emailService.joinEmail(emailRequestDto.getEmail()));
    }

    @PostMapping("/email/check")
    public String AuthCheck(@RequestBody @Valid EmailCheckDto emailCheckDto) {
        Boolean result = emailService.CheckAuthNum(emailCheckDto.getEmail(), emailCheckDto.getAuthNum());
        if(result){
                    return "ok";
                }
                else{
                    throw new NullPointerException("뭔가 잘못!");
                }
    }

    @PutMapping("/user/update")
    @ResponseStatus(HttpStatus.OK)
    public ResponseTemplate<?> updateBasicInfo(@Valid @RequestBody UserUpdateDto userUpdateDto) throws Exception {
        userService.update(userUpdateDto, SecurityUtil.getLoginUsername());
        return new ResponseTemplate<>(HttpStatus.OK, "회원 정보 업데이트 성공");
    }

    @PutMapping("/user/password")
    public ResponseTemplate<?> updatePassword(@Valid @RequestBody UpdatePasswordDto updatePasswordDto) throws Exception {
        userService.updatePassword(updatePasswordDto.checkPassword(),updatePasswordDto.toBePassword(), SecurityUtil.getLoginUsername());
        return new ResponseTemplate<>(HttpStatus.OK, "비밀번호 변경성공");
    }

    @DeleteMapping("/user/delete")
    public ResponseTemplate<?> withdraw(@Valid @RequestBody UserWithdrawDto userWithdrawDto) throws Exception {
        userService.withdraw(userWithdrawDto.checkPassword(), SecurityUtil.getLoginUsername());
        return new ResponseTemplate<>(HttpStatus.OK, "회원 탈퇴 성공");
    }

    @GetMapping("/user")
    public ResponseTemplate<?> getMyInfo(HttpServletResponse response) throws Exception {
        return new ResponseTemplate<>(HttpStatus.OK, "회원 정보 조회 성공", userService.getMyInfo());
    }

    // @PostMapping("/emails/verification-requests")
    // public String mailSend(@RequestBody @Valid EmailRequestDto emailRequest) {
    //     System.out.println("이메일 인증 이메일: " + emailRequest.getEmail());
    //     return emailService.joinEmail(emailRequest.getEmail());
    // }

    // @PostMapping("/emails/verfications")
    // public String AuthCheck(@RequestBody @Valid EmailCheckDto emailCheckDto) {
    //     Boolean result = emailService.CheckAuthNum(emailCheckDto.getEmail(), emailCheckDto.getAuthNum());
    //     if(result){
    //         System.out.println("인증 성공이요");
    //         return "ok";
    //     }
    //     else{
    //         throw new NullPointerException("뭔가 잘못!");
    //     }
    // }

    // @PostMapping("/emaills/verfications")
    // public boolean verifiedCode(@RequestBody EmailCheckDto emailCheckDto){
    //     boolean result = emailService.CheckAuthNum(emailCheckDto.getEmail(), emailCheckDto.getAuthNum());
    //     return result;
    // }

    // @GetMapping("/jwt-test")
    // public String jwtTest() {
    //     return "jwtTest 요청 성공";
    // }
    

}