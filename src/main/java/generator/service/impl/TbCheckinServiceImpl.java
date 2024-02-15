package generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import generator.domain.TbCheckin;
import generator.service.TbCheckinService;
import generator.mapper.TbCheckinMapper;
import org.springframework.stereotype.Service;

/**
* @author tianqiwei
* @description 针对表【tb_checkin(签到表)】的数据库操作Service实现
* @createDate 2024-02-14 21:51:41
*/
@Service
public class TbCheckinServiceImpl extends ServiceImpl<TbCheckinMapper, TbCheckin>
    implements TbCheckinService{

}




