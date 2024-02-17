package yrs.emos.generator.mapper;

import org.apache.ibatis.annotations.Mapper;
import yrs.emos.generator.domain.TbUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author tianqiwei
* @description 针对表【tb_user(用户表)】的数据库操作Mapper
* @createDate 2024-02-14 21:51:41
* @Entity generator.domain.TbUser
*/

@Mapper
public interface TbUserMapper extends BaseMapper<TbUser> {

    List<TbUser> selectList();
}




