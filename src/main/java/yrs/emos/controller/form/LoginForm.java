package yrs.emos.controller.form;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@ApiModel
@Data
public class LoginForm {
    @NotBlank(message = "invitation code cannot be null")
    private String code;
}
