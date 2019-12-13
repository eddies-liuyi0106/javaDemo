package net.canway.meeting_message.controller;

import net.canway.meeting_message.api.UserApi;
import net.canway.meeting_message.model.Result;
import net.canway.meeting_message.model.User;
import net.canway.meeting_message.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/user")
public class UserController implements UserApi {
    @Autowired
    private UserService userService;

    @Override
    @GetMapping("/findAll")
    public Result findAll() {
        Result result = userService.findAll();
        return result;
    }



    @Override
    @GetMapping("/findPage/{page}/{size}")
    public Result findPage(@PathVariable Integer page,@PathVariable Integer size,String username) {
        Result result = userService.findPage(page,size,username);
        return result;
    }

    @Override
    @PostMapping("/insertUser")
    public Result insertUser(User user) throws NoSuchAlgorithmException {
        Result result = userService.insertUser(user);
        return result;
    }

    @Override
    @PutMapping("/updateUser")
    public Result updateUser(User user) {
        Result result = userService.updateUser(user);
        return result;
    }

    @Override
    @PutMapping("/findOne/{id}")
    public Result findOne(@PathVariable("id") Integer id) {
        Result result = userService.findOne(id);
        return result;
    }

    @Override
    @GetMapping("/delete/{id}")
    public Result deleteUser(@PathVariable("id") Integer id) {
        Result result = userService.delete(id);
        return result;
    }


    @GetMapping("/ExcelDownload")
    public void excelDownload(HttpServletResponse response) throws IOException {
        userService.userToExcel(response);
    }

}
