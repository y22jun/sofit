package sofit.demo.dto.email;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailRequestDto {
    
    @Email
    @NotBlank(message = "이메일을 입력해 주세요")
    private String email;
}
