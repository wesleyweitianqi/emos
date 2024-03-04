package yrs.emos.generator.domain;

import lombok.Data;

import java.util.List;

@Data
public class MessageWithRefs {

    private List<MessageRefEntity> refs;
}
