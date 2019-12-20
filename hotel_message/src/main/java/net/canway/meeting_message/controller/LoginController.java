package net.canway.meeting_message.controller;



import net.canway.meeting_message.api.LoginApi;
import net.canway.meeting_message.model.Result;
import net.canway.meeting_message.service.LoginService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController implements LoginApi {
    @Autowired
    private LoginService loginService;

    @PostMapping("/doLogin")
    public Result doLogin(String username, String password){
        return loginService.doLogin(username,password);
    }

    @GetMapping("/hello")
    public String hello() {
        return "hello";

    }

    @GetMapping("/checkLogin")
    public String login() {
        return "please login!";
    }

    @GetMapping("/doLoginOut")
    @RequiresAuthentication
    public Result doLoginOut(){ return loginService.doLoginOut(); }

    @GetMapping("/loginStatus")
    public Result loginStatus(){ return loginService.loginStatus(); }
}
