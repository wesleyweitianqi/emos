package yrs.emos.controller.form;

import io.swagger.annotations.ApiOperation;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@ApiOperation("registerForm")
public class RegisterForm {

    @NotBlank(message = "registercode cannot be blank")
    @Pattern(regexp = "^[0-9]{6}$", message = "registerCode must be 6 digits")
    private String registerCode;

    @NotBlank(message = "code cannot be blank")
    private String code;

    @NotBlank(message = "nickname cannot be blank")
    private String nickname;
    @NotBlank(message = "photo cannot be blank")
    private String photo;
}
