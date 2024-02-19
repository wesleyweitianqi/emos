package yrs.emos.generator.service.impl;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import yrs.emos.exception.EmosException;
import yrs.emos.generator.domain.TbUser;
import yrs.emos.generator.service.TbUserService;
import yrs.emos.generator.mapper.TbUserMapper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Set;

/**
* @author tianqiwei
* @description 针对表【tb_user(用户表)】的数据库操作Service实现
* @createDate 2024-02-14 21:51:41
*/
@Service
@Slf4j
@Scope("prototype")
public class TbUserServiceImpl implements TbUserService {
    @Value("${wx.app-id}")
    private String appId;
    @Value("${wx.secret}")
    private String secret;

    @Autowired
    private TbUserMapper userMapper;

    private String getOpenId(String code) {
        String url = "https://api.weixin.qq.com/sns/jscode2session";
        HashMap map = new HashMap();
        map.put("appId", appId);
        map.put("secret", secret);
        map.put("js_code", code);
        map.put("grant_type", "authorization_code");
        String response = HttpUtil.post(url, map);
        JSONObject json = JSONUtil.parseObj(response);
        System.out.println(json.toString());
        String openId = json.getStr("openid");
        if (openId == null || openId.isEmpty()) {
            throw new RuntimeException("temp token openid is wrong");
        }
        return openId;
    }

    @Override
    public int registerUser(String registerCode, String code, String nickname, String photo) {
        if(registerCode.equals("000000")){
            Boolean b = userMapper.hasRootUser();
            if(!b){
                String openId = getOpenId(code);
                HashMap param = new HashMap();
                param.put("openId", openId);
                param.put("nickname", nickname);
                param.put("photo", photo);
                param.put("role", "[0]");
                param.put("status", 1);
                param.put("createTime", new Date());
                param.put("root", true);
                userMapper.insert(param);
                int id = userMapper.searchIdByOpenId(openId);
                return id;
            }else {
                throw new EmosException("cannot bind admin account");
            }
        }else {

        }
        return 0;
    }

    @Override
    public Set<String> searchUserPermissions(int userId) {
        Set<String> permissions = userMapper.searchUserPermissions(userId);
        return permissions;
    }

    @Override
    public Integer login(String code) {
        String openId = getOpenId(code);
        Integer id = userMapper.searchIdByOpenId(openId);
        if(id == null ){
            throw new EmosException("account not exist");
        }
        return id;
    }
}




