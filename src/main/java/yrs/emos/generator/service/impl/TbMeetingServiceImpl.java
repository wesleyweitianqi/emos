package yrs.emos.generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import yrs.emos.generator.domain.TbMeeting;
import yrs.emos.generator.service.TbMeetingService;
import yrs.emos.generator.mapper.TbMeetingMapper;
import org.springframework.stereotype.Service;

/**
* @author tianqiwei
* @description 针对表【tb_meeting(会议表)】的数据库操作Service实现
* @createDate 2024-02-14 21:51:41
*/
@Service
public class TbMeetingServiceImpl extends ServiceImpl<TbMeetingMapper, TbMeeting>
    implements TbMeetingService{

}




