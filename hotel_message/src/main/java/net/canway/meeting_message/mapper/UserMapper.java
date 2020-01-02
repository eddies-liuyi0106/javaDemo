package net.canway.meeting_message.mapper;


import net.canway.meeting_message.model.Department;
import net.canway.meeting_message.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {
    @Select("select id,username,name,age,sex,phone,email,did from tb_user")
    @Results({
            @Result(column = "did", property = "department", javaType = Department.class,
                    one = @One(select = "net.canway.meeting_message.mapper.DepartmentMapper.findById")) //部门通过ID查询接口
    })
    List<User> findAll();

    @Select("select id,username,name,age,sex,phone,email,did from tb_user where username like #{username}")
    @Results({
            @Result(column = "did", property = "department", javaType = Department.class,
                    one = @One(select = "net.canway.meeting_message.mapper.DepartmentMapper.findById"))
    })
    List<User> findByUsername(String username);

    @Select("select * from tb_user where id=#{id}")
    @Results({
            @Result(column = "did", property = "department", javaType = Department.class,
                    one = @One(select = "net.canway.meeting_message.mapper.DepartmentMapper.findById"))
    })
    User findOne(Integer id);

    @Update("update tb_user set username=#{username},name=#{name},age=#{age},sex=#{sex},phone=#{phone},email=#{email}" +
            " where id=#{id}")
    boolean updateUser(User user);

    @Insert("insert into tb_user(username,password,name,age,sex,phone,email,did,salt)" +
            "values(#{username},#{password},#{name},#{age},#{sex},#{phone},#{email},#{did},#{salt})")
    boolean insertUser(User user);

    @Delete("delete from tb_user where id=#{id}")
    boolean deleteUser(Integer id);

    //数据唯一性校验
    @Select("select id from tb_user where username=#{username}")
    User checkUsername(String username);

    @Select("select id from tb_user where email=#{email}")
    User checkEmail(String email);

    @Select("select id from tb_user where phone=#{phone}")
    User checkPhone(String phone);

    @Select("select username,password,salt from tb_user where username=#{username}")
    User doLogin(String username);

    @Select("select email from tb_user where username=#{username}")
    String findEmailByName(String username);

    @Update("update tb_user set password=#{password},salt=#{salt} where username=#{username}")
    boolean changePasswd(User user);
}
