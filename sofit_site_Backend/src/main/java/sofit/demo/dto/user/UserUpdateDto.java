package sofit.demo.dto.user;

import java.util.Optional;

public record UserUpdateDto(Optional<String> email, Optional<String> username) {
    
}
