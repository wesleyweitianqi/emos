package yrs.emos.generator.service;

import io.swagger.models.auth.In;
import yrs.emos.generator.domain.TbUser;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Set;

/**
* @author tianqiwei
* @description 针对表【tb_user(用户表)】的数据库操作Service
* @createDate 2024-02-14 21:51:41
*/
public interface TbUserService  {
    public int registerUser(String registerCode,String code,String nickname,String photo);
    public Set<String> searchUserPermissions(int userId);
    public  Integer login(String code);

    public TbUser searchById(Integer userId);

}
