package yrs.emos.controller.form;


import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel
public class DeleteMessageByIdForm {
    @NotNull
    private String id;
}
