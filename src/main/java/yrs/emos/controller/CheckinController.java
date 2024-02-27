package yrs.emos.controller;

import cn.hutool.core.date.DateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Generated;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import yrs.emos.common.util.R;
import yrs.emos.config.shiro.JwtUtil;
import yrs.emos.generator.service.TbCheckinService;

@RequestMapping("/checkin")
@RestController
@Api("checkin web api")
@Slf4j
public class CheckinController {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private TbCheckinService tbCheckinService;


    @ApiOperation("valid Checkin")
    @GetMapping("/validCanCheckIn")
    public R validCanCheckIn(@RequestHeader("token") String token){
        int userId=jwtUtil.getUserId(token);
        String result = tbCheckinService.validCanCheckIn(userId, String.valueOf(DateUtil.date()));
        return R.ok(result);
    }
}
