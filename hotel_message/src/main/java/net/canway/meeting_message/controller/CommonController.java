package net.canway.meeting_message.controller;

import net.canway.meeting_message.common.UniqueValidatException;
import net.canway.meeting_message.model.Result;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

@RestControllerAdvice
public class CommonController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommonController.class);

    @ExceptionHandler({Exception.class})
    public Result exceptionHandler(Exception e) throws Exception {
        if (e instanceof MissingServletRequestParameterException) {
            Result result = new Result();
            result.setCode("401");
            result.setMessage("必填参数没有传入");
            return result;
        }
        if (e instanceof MethodArgumentNotValidException) {

            String errorMessage = ((MethodArgumentNotValidException) e)
                    .getBindingResult()
                    .getAllErrors()
                    .stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.joining());
            Result result = new Result();
            result.setCode("401");
            result.setMessage(errorMessage);
            return result;
        }
        if (e instanceof ArithmeticException) {
            String errorMessage = e.getMessage();
            Result result = new Result();
            result.setCode("500");
            result.setMessage(errorMessage);
            return result;
        } else if (e instanceof ConstraintViolationException) {
            String errorMessage = ((ConstraintViolationException) e)
                    .getConstraintViolations()
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.joining());
            Result result = new Result();
            result.setCode("401");
            result.setMessage(errorMessage);
            return result;
        } else if (e instanceof UniqueValidatException) {
            String errorMessage = e.getMessage();
            Result result = new Result();
            result.setCode("401");
            result.setMessage(errorMessage);
            return result;
        } else if (e instanceof BindException) {
            String errorMessage = ((BindException) e).getBindingResult().getAllErrors().stream().map(error -> error.getDefaultMessage()).collect(Collectors.joining());
            Result result = new Result();
            result.setCode("401");
            result.setMessage(errorMessage);
            return result;
        } else if(e instanceof IncorrectCredentialsException){
            Result result = new Result();
            result.setCode("401");
            result.setMessage("密码错误");
            return result;
        }
        else {
            /*LOGGER.error(e.getLocalizedMessage(), e);*/
            return new Result(e.getMessage(), "500", null);
        }
    }
}
