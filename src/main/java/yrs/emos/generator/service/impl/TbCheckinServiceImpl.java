package yrs.emos.generator.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import yrs.emos.config.SystemConstants;
import yrs.emos.generator.domain.TbCheckin;
import yrs.emos.generator.mapper.TbHolidaysMapper;
import yrs.emos.generator.mapper.TbWorkdayMapper;
import yrs.emos.generator.service.TbCheckinService;
import yrs.emos.generator.mapper.TbCheckinMapper;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
* @author tianqiwei
* @description 针对表【tb_checkin(签到表)】的数据库操作Service实现
* @createDate 2024-02-14 21:51:41
*/
@Service
@Scope("prototype")
@Slf4j
public class TbCheckinServiceImpl implements TbCheckinService {

    @Autowired
    private SystemConstants constants;

    @Autowired
    private TbHolidaysMapper holidaysMapper;

    @Autowired
    private TbWorkdayMapper workdayMapper;

    @Autowired
    private TbCheckinMapper tbCheckinMapper;

    @Override
    public Integer haveCheckin(HashMap param) {
        return null;
    }

    @Override
    public String validCanCheckIn(int userId, String date) {
        boolean bool_1 = holidaysMapper.searchTodayIsHolidays() != null;
        boolean bool_2 = workdayMapper.searchTodayIsWorkDay() != null;
        String type = "weekday";
        if (DateUtil.date().isWeekend()) {
            type = "holiday";
        }
        if (bool_1) {
            type = "holiday";
        } else if (bool_2) {
            type = "weekday";
        }

        if (type.equals("holiday")) {
            return "no need to punch";
        } else {
            DateTime now = DateUtil.date();
            String start = DateUtil.today() + " " + constants.attendanceStartTime;
            String end = DateUtil.today() + " " + constants.attendanceEndTime;
            DateTime attendanceStart = DateUtil.parse(start);
            DateTime attendanceEnd = DateUtil.parse(end);
            if(now.isBefore(attendanceStart)){
                return "punch not start yet";
            }
            else if(now.isAfter(attendanceEnd)){
                return "punch time passed";
            }else {
                HashMap map=new HashMap();
                map.put("userId",userId);
                map.put("date",date);
                map.put("start",start);
                map.put("end",end);
                boolean bool= tbCheckinMapper.haveCheckin(map) != null;
                return bool?"have punched，no repeat" : "punch";
            }
        }

    }


}




