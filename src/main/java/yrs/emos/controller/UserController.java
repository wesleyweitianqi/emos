package yrs.emos.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import yrs.emos.common.util.R;
import yrs.emos.config.shiro.JwtUtil;
import yrs.emos.controller.form.LoginForm;
import yrs.emos.controller.form.RegisterForm;
import yrs.emos.generator.service.TbUserService;

import javax.validation.Valid;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
@Api("user web interface")
public class UserController {
    @Autowired
    private TbUserService userService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private RedisTemplate redisTemplate;
    @Value("${emos.jwt.cache-expire}")
    private long cacheExpire;

    @PostMapping("/register")
    @ApiOperation("register")
    public R register(@Valid @RequestBody RegisterForm form){
        int id = userService.registerUser(form.getRegisterCode(), form.getCode(), form.getNickname(), form.getPhoto());
        String token = jwtUtil.createToken(id);
        Set<String> permissionSet = userService.searchUserPermissions(id);
        saveCacheToken(token, id);
        return R.ok("user registered successfully").put("token", token).put("permission", permissionSet);
    }
    private void saveCacheToken(String token, int userId){
        redisTemplate.opsForValue().set(token, userId+"", cacheExpire, TimeUnit.DAYS);
    }

    @PostMapping("/login")
    @ApiOperation("login")
    public R login(@Valid @RequestBody LoginForm form){
        int id = userService.login(form.getCode());
        String token = jwtUtil.createToken(id);
        saveCacheToken(token, id);
        Set<String> permissionSet = userService.searchUserPermissions(id);
        return R.ok("login successfully").put("token", token).put("permission", permissionSet);
    }
}
