package yrs.emos.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import yrs.emos.exception.EmosException;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public String validExceptionHandler(Exception e){
        log.error("exception", e);
        if(e instanceof MethodArgumentNotValidException){
            MethodArgumentNotValidException exception = (MethodArgumentNotValidException) e;
            return exception.getBindingResult().getFieldError().getDefaultMessage();
        }
        if (e instanceof EmosException){
            EmosException exception = (EmosException) e;
            return exception.getMsg();
        }
        if (e instanceof UnauthorizedException) {
            return "unauthorized";
        } else {
            return "back end exception";
        }
    }
}
