package yrs.emos.config.shiro;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import org.apache.http.HttpStatus;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Component
@Scope("prototype")
public class Oauth2Filter extends AuthenticatingFilter {
    @Autowired
    private  ThreadLocalToken threadLocalToken;

    @Value("${emos.jwt.cache-expire}")
    private int cacheExpire;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    protected AuthenticationToken createToken(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        String token = getRequestToken(req);
        if(StrUtil.isBlank(token)){
            return null;
        }
        return new Oauth2Token(token);

    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
       HttpServletRequest req = (HttpServletRequest) servletRequest;
       HttpServletResponse res = (HttpServletResponse) servletResponse;
       res.setContentType("text/html");
       res.setCharacterEncoding("utf-8");
       res.setHeader("Access-Control-Allow-Credentials", "true");
       res.setHeader("Access-Control-Allow-Origin", req.getHeader("Origin"));

       threadLocalToken.clear();

       String token = getRequestToken(req);
       if(StrUtil.isBlank(token)){
           res.setStatus(HttpStatus.SC_UNAUTHORIZED);
           res.getWriter().print("invalid token");
           return false;
       }
        try {
            jwtUtil.verifyToken(token);
        } catch (TokenExpiredException e) {
            if(redisTemplate.hasKey(token)){
                redisTemplate.delete(token);
                int userId = jwtUtil.getUserId(token);
                token = jwtUtil.createToken(userId);
                redisTemplate.opsForValue().set(token, userId+"", cacheExpire, TimeUnit.DAYS);
                threadLocalToken.setToken(token);
            } else {
                res.setStatus(HttpStatus.SC_UNAUTHORIZED);
                res.getWriter().print("token expired");
                return false;
            }
        } catch (JWTDecodeException e){
            res.setStatus(HttpStatus.SC_UNAUTHORIZED);
            res.getWriter().print("invalid token");
            return false;
        }

        // execute realm method
        boolean b = executeLogin(req, res);
        return b;
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        res.setContentType("text/html");
        res.setCharacterEncoding("utf-8");
        res.setHeader("Access-Control-Allow-Credentials", "true");
        res.setHeader("Access-Control-Allow-Origin", req.getHeader("Origin"));
        res.setStatus(HttpStatus.SC_UNAUTHORIZED);
        try {
            res.getWriter().print(e.getMessage());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        return super.onLoginFailure(token, e, request, response);
    }

    // allow to shiro or not
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        HttpServletRequest req = (HttpServletRequest) request;
        return req.getMethod().equals(RequestMethod.OPTIONS.name());
    }

    @Override
    public void doFilterInternal(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        super.doFilterInternal(request, response, chain);
    }

    private  String getRequestToken(HttpServletRequest request){
        String token = request.getHeader("token");
        if(StrUtil.isBlank(token)){
            token = request.getParameter("token");
        }
        return token;
    }


}
