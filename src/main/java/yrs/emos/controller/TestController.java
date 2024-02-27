package yrs.emos.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import yrs.emos.common.util.R;
import yrs.emos.config.shiro.JwtUtil;
import yrs.emos.controller.form.TestSayHelloForm;
import yrs.emos.generator.service.TbUserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping("/test")
@Api("test web interface")
public class TestController {
    @Autowired
    private TbUserService userService;
    @Autowired
    private JwtUtil jwtUtil;
    @PostMapping("sayHello")
    @ApiOperation("test")
    public R sayHello(@Valid @RequestBody TestSayHelloForm form){
        return R.ok().put("message", "helloWorld" + form.getName());
    }
    @PostMapping("/addUser")
    @ApiOperation("add user")
    @RequiresPermissions(value = {"a", "b"}, logical = Logical.OR)
    public R addUser(HttpServletRequest request){
//        String token = request.getHeader("token");
//        int userId = jwtUtil.getUserId(token);
//        Set<String> permitSet = userService.searchUserPermissions(userId);
//        System.out.println(permitSet.toString());
        return R.ok("added");
    }
}
