package sofit.demo.service.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import sofit.demo.domain.Role;
import sofit.demo.domain.User;
import sofit.demo.dto.user.UserInfoDto;
import sofit.demo.dto.user.UserSignUpDto;
import sofit.demo.dto.user.UserUpdateDto;
import sofit.demo.global.util.SecurityUtil;
import sofit.demo.repository.UserRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void signUp(UserSignUpDto userSignUpDto) throws Exception {

        if (userRepository.findByEmail(userSignUpDto.getEmail()).isPresent()) {
            throw new Exception("이미 존재하는 이메일입니다.");
        }

        if (userRepository.findByNickname(userSignUpDto.getNickname()).isPresent()) {
            throw new Exception("이미 존재하는 닉네임입니다.");
        }

        User user = User.builder()
                .email(userSignUpDto.getEmail())
                .password(userSignUpDto.getPassword())
                .nickname(userSignUpDto.getNickname())
                .role(Role.USER)
                .build();

        user.passwordEncode(passwordEncoder);
        userRepository.save(user);
    }

    public void update(UserUpdateDto userUpdateDto, String email) throws Exception {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("x"));


        userUpdateDto.email().ifPresent(user::updateEmail);
        userUpdateDto.username().ifPresent(user::updateNickname);
    }

    public void updatePassword(String asIsPassword, String toBePassword, String email) throws Exception {

        User user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("x"));

        if(!user.matchPassword(passwordEncoder, asIsPassword) ) {
            throw new IllegalArgumentException("xxxxxxxxxxxxxxxxxxxxxxxxxxx");
        }

        user.updatePassword(toBePassword, passwordEncoder);
    }

    public void withdraw(String checkPassword, String email) throws Exception {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("x"));

        if(!user.matchPassword(passwordEncoder, checkPassword) ) {
            throw new IllegalArgumentException("x");
        }

        userRepository.delete(user);
    }

    public UserInfoDto getMyInfo() throws Exception {
        User findUser = userRepository.findByEmail(SecurityUtil.getLoginUsername()).orElseThrow(() -> new IllegalArgumentException("x"));
        return new UserInfoDto(findUser);
    }

    public User findByEmail() {
        return userRepository.findByEmail(SecurityUtil.getLoginUsername())
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));
    }
}
