package generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import generator.domain.TbUser;
import generator.service.TbUserService;
import generator.mapper.TbUserMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author tianqiwei
* @description 针对表【tb_user(用户表)】的数据库操作Service实现
* @createDate 2024-02-14 21:51:41
*/
@Service
public class TbUserServiceImpl extends ServiceImpl<TbUserMapper, TbUser>
    implements TbUserService{
    
}




