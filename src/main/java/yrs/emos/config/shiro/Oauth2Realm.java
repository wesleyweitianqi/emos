package yrs.emos.config.shiro;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import yrs.emos.generator.domain.TbUser;
import yrs.emos.generator.service.TbUserService;

import java.util.Set;

@Component
public class Oauth2Realm extends AuthorizingRealm {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private TbUserService userService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof AuthenticationToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        TbUser user = (TbUser) principals.getPrimaryPrincipal();
        int userId = user.getId();
        Set<String> permitSet = userService.searchUserPermissions(userId);
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(permitSet);
        return info;  // Return SimpleAuthorizationInfo object
    }
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //TODO get userid from token, authenticate token status
        String token = (String) authenticationToken.getPrincipal();
        int userId = jwtUtil.getUserId(token);
        TbUser user = userService.searchById(userId);
        if (user == null){
            throw new LockedAccountException("account has been locked, please contact admin");
        }
        //TODO add userinfo, token into info object
        SimpleAuthenticationInfo info  = new SimpleAuthenticationInfo(user, token, getName());
        return info;
    }

}
