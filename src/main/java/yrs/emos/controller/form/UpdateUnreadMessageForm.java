package yrs.emos.controller.form;


import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotNull;

@Data
@ApiModel
public class UpdateUnreadMessageForm {
    @NotNull
    private String Id;
}
