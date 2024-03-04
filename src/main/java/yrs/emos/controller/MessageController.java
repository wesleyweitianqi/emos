package yrs.emos.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import yrs.emos.common.util.R;
import yrs.emos.config.shiro.JwtUtil;
import yrs.emos.controller.form.DeleteMessageByIdForm;
import yrs.emos.controller.form.SearchMessageByIdForm;
import yrs.emos.controller.form.SearchMessageByPageForm;
import yrs.emos.controller.form.UpdateUnreadMessageForm;
import yrs.emos.generator.service.MessageService;
import yrs.emos.task.MessageTask;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/message")
@Api("message api")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private MessageTask messageTask;

    @ApiOperation("get pages of message")
    @PostMapping("/searchMessageByPage")
    public R searchMessageByPage(@Valid @RequestBody SearchMessageByPageForm form, @RequestHeader("token") String token){
        int userId = jwtUtil.getUserId(token);
        int page = form.getPage();
        int length = form.getLength();
        long start = (page - 1) * length;
        List<HashMap> list = messageService.searchMessageByPage(userId, start, length);
        return R.ok().put("result", list);
    }
    @PostMapping("/searchMessageById")
    @ApiOperation("search message by Id")
    public R searchMessageById(@Valid  @RequestBody SearchMessageByIdForm form,@RequestHeader("token") String token){int userId = jwtUtil.getUserId(token);HashMap map = messageService.searchMessageById(String.valueOf(form.getId()));return R.ok().put("result", map);
    }

    @PostMapping("/updateUnreadMessageById")
    public R updateUnreadMessage(@Valid @RequestBody UpdateUnreadMessageForm form){
        long rows = messageService.updateUnreadMessage(form.getId());
        return R.ok().put("result", rows == 1);
    }
    @PostMapping("/deleteMessageById")
    @ApiOperation("delete message by id")
    public R deleteMessageById(@Valid @RequestBody DeleteMessageByIdForm form){
        long rows = messageService.deleteMessageRefById(form.getId());
        return R.ok().put("result", rows == 1);
    }

    @GetMapping("/refreshMessage")
    @ApiOperation("refresh message")
    public R refreshMessage(@RequestHeader("token") String token){
        int userId = jwtUtil.getUserId(token);
        messageTask.receiveAsync(userId+"");
        long lastRows = messageService.searchLastCount(userId);
        long unreadRows = messageService.searchUnreadCount(userId);
        return R.ok().put("lastRows", lastRows).put("unreadRows", unreadRows);
    }
}
