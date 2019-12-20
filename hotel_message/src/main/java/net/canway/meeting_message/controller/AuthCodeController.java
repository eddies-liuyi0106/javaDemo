package net.canway.meeting_message.controller;

import net.canway.meeting_message.api.AuthCodeApi;
import net.canway.meeting_message.common.CommonTimer;
import net.canway.meeting_message.common.EmailUtil;
import net.canway.meeting_message.mapper.UserMapper;
import net.canway.meeting_message.model.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/code")
public class AuthCodeController implements AuthCodeApi {

    @Autowired
    EmailUtil emailUtil;
    @Autowired
    UserMapper userMapper;

    private static String authCode;
    private static Map<String,String> codes = new HashMap<>();

    @GetMapping("/findPassWord/{name}")
    public Result findPassWord(@PathVariable("name") String name) {
        String email = userMapper.findEmailByName(name);
        if (email == null || "".equals(email)) {
            return new Result("没有相关用户信息", "404", null);
        }
        String regEx1 = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern p = Pattern.compile(regEx1);
        Matcher m = p.matcher(email);
        if (m.matches()) {
            try {
                authCode = emailUtil.code();
                codes.put(name,authCode);
                Result mimeMessage = EmailUtil.createMimeMessage(email, codes.get(name));
                ScheduledExecutorService service = CommonTimer.service;
                service.schedule(new Runnable() {
                    @Override
                    public void run() {
                        codes.remove(name);
                        authCode = null;
                    }
                }, 60000, TimeUnit.MILLISECONDS);
                return mimeMessage;
            } catch (Exception e) {
                return new Result("发送失败，请重新发送", "500", null);
            }
        } else {
            new Result("用户信息错误，请联系管理员修改", "500", null);
        }
        return new Result("用户信息填写不完整，请联系管理员", "500", null);
    }

    @GetMapping("/auth/{code}/{name}")
    public Result auth(@PathVariable("code") String code,@PathVariable("name") String name) {
        String auth = codes.get(name);
        if(auth==null){
            return new Result("验证码超时","500",null);
        }
        if (auth.equals(code)) {
            return new Result("验证成功", "200", null);
        } else {
            return new Result("验证码不正确", "500", null);
        }
    }
}
