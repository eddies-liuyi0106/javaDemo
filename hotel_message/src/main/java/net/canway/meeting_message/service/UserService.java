package net.canway.meeting_message.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import net.canway.meeting_message.common.ExcelUtil;
import net.canway.meeting_message.common.UniqueValidatException;
import net.canway.meeting_message.mapper.UserMapper;
import net.canway.meeting_message.model.Result;
import net.canway.meeting_message.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ValidationException;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public Result findAll(){
        List<User> users = userMapper.findAll();
        return new Result("查询成功", "200", users);
    }

    public Result findOne(Integer id){
        User user = userMapper.findOne(id);
        return new Result("查询成功", "200", user);
    }



    public Result findPage(Integer page, Integer size,String username){
        PageHelper.startPage(page,size);
        List<User> users;
        if(username == null || username.equals("")){
            users = userMapper.findAll();
        }
        else {
            username = "%"+username+"%";
            users = userMapper.findByUsername(username);
        }
        PageInfo pageInfo = new PageInfo(users);
        return new Result("查询成功", "200", pageInfo);
    }

    public Result insertUser(User user) throws NoSuchAlgorithmException {
        UniqueCheck(user,false);
        String password = user.getPassword();
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        String md5Password = new BigInteger(1,md.digest()).toString(16);
        user.setPassword(md5Password);
        boolean result = userMapper.insertUser(user);
        if(!result){
            return new Result("添加失败", "404", null);
        }
        return new Result("添加成功", "200", null);
    }

    public Result updateUser(User user){
        UniqueCheck(user,true);
        User usercheck = userMapper.findOne(user.getId());
        if(usercheck == null){
            return new Result("没有该用户", "404", null);
        }
        boolean result = userMapper.updateUser(user);
        if(!result){
            return new Result("修改失败", "401", null);
        }
        return new Result("修改成功", "200", null);
    }

    public void userToExcel(HttpServletResponse response) throws IOException {
        List<String[]> userExcelList = new ArrayList<String[]>();
        String[] header = {"编号","用户名","姓名","年龄","性别","手机号码","邮箱","所属部门"};
        userExcelList.add(header);
        List<User> users = userMapper.findAll();
        for(User user:users){
            String sex = user.getSex()?"男":"女";
            String[] row = {user.getId().toString(),user.getUsername(),user.getName(),user.getAge().toString(),
                    sex,user.getPhone(),user.getEmail(),user.getDepartment().getName()};
            userExcelList.add(row);
        }
        ExcelUtil.modelToExcel(response,userExcelList,"用户表","user");

    }


    public Result delete(Integer id){
        User one = userMapper.findOne(id);
        if(one==null){
            return new Result("没有该用户的信息","404",null);
        }
        userMapper.deleteUser(id);
        return new Result("删除成功","200",null);
    }




    //唯一校验
    private void UniqueCheck(User user,boolean isUpdate){
        Map<String,User> userMap= new HashMap<String,User>();
        User userByUsername = userMapper.checkUsername(user.getUsername());
        User userByEmail = userMapper.checkEmail(user.getEmail());
        User userByPhone = userMapper.checkPhone(user.getPhone());
        if(userByUsername != null){
            userMap.put("用户名",userByUsername);
        }
        if(userByEmail != null){
            userMap.put("邮箱",userByEmail);
        }
        if(userByPhone != null){
            userMap.put("手机号",userByPhone);
        }
        if(userMap.size() == 0){
            return;
        }
        StringJoiner errorMessage = new StringJoiner(",");
        if(isUpdate){
            for (Map.Entry<String, User> userEntry : userMap.entrySet()) {
                if(!userEntry.getValue().getId().equals(user.getId())){
                    errorMessage.add(userEntry.getKey());
                }
            }
            if(errorMessage.length()==0){
                return;
            }
        }
        else {

                for(String key:userMap.keySet()){
                    errorMessage.add(key);
                }

        }
        throw new UniqueValidatException(errorMessage.toString()+"已存在，请修改");

    }



}
