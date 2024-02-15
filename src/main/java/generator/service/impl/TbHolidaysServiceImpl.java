package generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import generator.domain.TbHolidays;
import generator.service.TbHolidaysService;
import generator.mapper.TbHolidaysMapper;
import org.springframework.stereotype.Service;

/**
* @author tianqiwei
* @description 针对表【tb_holidays(节假日表)】的数据库操作Service实现
* @createDate 2024-02-14 21:51:41
*/
@Service
public class TbHolidaysServiceImpl extends ServiceImpl<TbHolidaysMapper, TbHolidays>
    implements TbHolidaysService{

}




