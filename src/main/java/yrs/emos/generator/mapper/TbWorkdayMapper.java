package yrs.emos.generator.mapper;

import org.apache.ibatis.annotations.Mapper;
import yrs.emos.generator.domain.TbWorkday;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author tianqiwei
* @description 针对表【tb_workday】的数据库操作Mapper
* @createDate 2024-02-14 21:51:41
* @Entity generator.domain.TbWorkday
*/
@Mapper
public interface TbWorkdayMapper extends BaseMapper<TbWorkday> {
    public Integer searchTodayIsWorkDay();
}




