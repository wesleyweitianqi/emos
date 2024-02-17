package yrs.emos.generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import yrs.emos.generator.domain.TbCity;
import yrs.emos.generator.service.TbCityService;
import yrs.emos.generator.mapper.TbCityMapper;
import org.springframework.stereotype.Service;

/**
* @author tianqiwei
* @description 针对表【tb_city(疫情城市列表)】的数据库操作Service实现
* @createDate 2024-02-14 21:51:41
*/
@Service
public class TbCityServiceImpl extends ServiceImpl<TbCityMapper, TbCity>
    implements TbCityService {

}




