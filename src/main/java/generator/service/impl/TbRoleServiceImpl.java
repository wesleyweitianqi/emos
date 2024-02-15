package generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import generator.domain.TbRole;
import generator.service.TbRoleService;
import generator.mapper.TbRoleMapper;
import org.springframework.stereotype.Service;

/**
* @author tianqiwei
* @description 针对表【tb_role(角色表)】的数据库操作Service实现
* @createDate 2024-02-14 21:51:41
*/
@Service
public class TbRoleServiceImpl extends ServiceImpl<TbRoleMapper, TbRole>
    implements TbRoleService{

}




