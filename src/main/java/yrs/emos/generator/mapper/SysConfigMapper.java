package yrs.emos.generator.mapper;

import yrs.emos.generator.domain.SysConfig;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author tianqiwei
* @description 针对表【sys_config】的数据库操作Mapper
* @createDate 2024-02-14 21:51:41
* @Entity generator.domain.SysConfig
*/
@Mapper
public interface SysConfigMapper extends BaseMapper<SysConfig> {
    public List<SysConfig> selectAllParam();
}




