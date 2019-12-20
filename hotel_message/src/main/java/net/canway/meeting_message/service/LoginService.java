package net.canway.meeting_message.service;


import net.canway.meeting_message.model.Result;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    public Result doLogin(String username, String password){
        Subject subject = SecurityUtils.getSubject();
        try {
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password);
            usernamePasswordToken.setRememberMe(true);
            subject.login(usernamePasswordToken);
            System.out.println("登录成功!");
            Session session = subject.getSession();
            System.out.println(session.getStartTimestamp());

        }
        catch (IncorrectCredentialsException e) {
            return new Result("密码错误","500",null);
        }catch (UnknownAccountException e){
            return new Result("账号不存在","500",null);
        }
        return new Result("登录成功","200",null);
    }

    public Result doLoginOut(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return new Result("注销成功","200",null);

    }

    public Result loginStatus(){
        Subject subject = SecurityUtils.getSubject();
        if(subject.getPrincipal() == null){
            return new Result("未登录","500",null);
        }
        else {
            return new Result("","200",null);
        }
    }
}
