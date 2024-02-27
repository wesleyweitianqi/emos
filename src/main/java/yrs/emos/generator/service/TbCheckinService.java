package yrs.emos.generator.service;

import yrs.emos.generator.domain.TbCheckin;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.HashMap;

/**
* @author tianqiwei
* @description 针对表【tb_checkin(签到表)】的数据库操作Service
* @createDate 2024-02-14 21:51:41
*/
public interface TbCheckinService  {
    public Integer haveCheckin(HashMap param);
    public String validCanCheckIn(int userId,String date);
}
