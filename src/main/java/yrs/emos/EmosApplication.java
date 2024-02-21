package yrs.emos;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import yrs.emos.config.SystemConstants;
import yrs.emos.generator.domain.SysConfig;
import yrs.emos.generator.mapper.SysConfigMapper;

import javax.annotation.PostConstruct;
import java.lang.reflect.Field;
import java.util.List;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@ServletComponentScan   //enable XssFilter
@Slf4j
public class EmosApplication  {
    @Autowired
    private SysConfigMapper sysConfigMapper;
    @Autowired
    private SystemConstants systemConstants;
    public static void main(String[] args) {
        SpringApplication.run(EmosApplication.class, args);
    }

    @PostConstruct
    public void init(){
        List<SysConfig> list = sysConfigMapper.selectAllParam();
        list.forEach(one -> {
            String key = one.getParamKey();
            key = StrUtil.toCamelCase(key);
            String value = one.getParamValue();
            try {
                Field  field = systemConstants.getClass().getDeclaredField(key);
                field.set(systemConstants, value);
            } catch (Exception e) {
                log.error(String.valueOf(e));
            }
        });
    }
}
