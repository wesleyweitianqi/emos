package generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import generator.domain.TbAction;
import generator.service.TbActionService;
import generator.mapper.TbActionMapper;
import org.springframework.stereotype.Service;

/**
* @author tianqiwei
* @description 针对表【tb_action(行为表)】的数据库操作Service实现
* @createDate 2024-02-14 21:51:41
*/
@Service
public class TbActionServiceImpl extends ServiceImpl<TbActionMapper, TbAction>
    implements TbActionService{

}




