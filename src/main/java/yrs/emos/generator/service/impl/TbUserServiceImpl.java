package yrs.emos.generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import yrs.emos.generator.domain.TbUser;
import yrs.emos.generator.service.TbUserService;
import yrs.emos.generator.mapper.TbUserMapper;
import org.springframework.stereotype.Service;

/**
* @author tianqiwei
* @description 针对表【tb_user(用户表)】的数据库操作Service实现
* @createDate 2024-02-14 21:51:41
*/
@Service
public class TbUserServiceImpl extends ServiceImpl<TbUserMapper, TbUser>
    implements TbUserService {
    
}




