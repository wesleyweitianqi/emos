package yrs.emos.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import yrs.emos.common.util.R;
import yrs.emos.controller.form.TestSayHelloForm;

import javax.validation.Valid;

@RestController
@RequestMapping("/test")
@Api("test web interface")
public class TestController {
    @PostMapping("sayHello")
    @ApiOperation("test")
    public R sayHello(@Valid @RequestBody TestSayHelloForm form){
        System.out.println("testst++++++++");
        return R.ok().put("message", "helloWorld" + form.getName());
    }
}
