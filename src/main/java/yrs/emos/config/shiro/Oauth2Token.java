package yrs.emos.config.shiro;

import org.apache.shiro.authc.AuthenticationToken;
import org.springframework.stereotype.Component;



public class Oauth2Token implements AuthenticationToken {
    private String token;

    public Oauth2Token(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
