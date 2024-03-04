package yrs.emos.controller.form;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@ApiModel
public class SearchMessageByIdForm {
    @NotNull
    @Min(1)
    private Integer id;
}
