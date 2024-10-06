package sofit.demo.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import sofit.demo.domain.SocialType;
import sofit.demo.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByNickname(String nickname);

    //public User findByusername(String nickname);

    Optional<User> findByRefreshToken(String refreshToken);

    Optional<User> findBySocialTypeAndSocialId(SocialType socialType, String socialId);
}
