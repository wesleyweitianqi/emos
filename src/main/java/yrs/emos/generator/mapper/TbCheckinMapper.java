package yrs.emos.generator.mapper;

import yrs.emos.generator.domain.TbCheckin;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;

/**
* @author tianqiwei
* @description 针对表【tb_checkin(签到表)】的数据库操作Mapper
* @createDate 2024-02-14 21:51:41
* @Entity generator.domain.TbCheckin
*/
@Mapper
public interface TbCheckinMapper  {
    public Integer haveCheckin(HashMap param);

}




