package net.canway.hotel_message.dao;

import net.canway.hotel_message.model.Department;
import net.canway.hotel_message.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {
    @Select("select id,username,name,age,sex,phone,email,did from tb_user")
    @Results({
            @Result(column="did",property="department",javaType= Department.class,
                    one=@One(select="net.canway.hotel_message.dao.DepartmentMapper.findById")) //部门通过ID查询接口
    })
    List<User> findAll();

    @Select("select * from tb_user where id=#{id}")
    @Results({
            @Result(column="did",property="department",javaType=Department.class,
                    one=@One(select="net.canway.hotel_message.dao.DepartmentMapper.findById"))
    })
    User findOne(Integer id);
    @Update("update tb_user set username=#{username},name=#{name},age=#{age},sex=#{sex},phone=#{phone},email=#{email}" +
            " where id=#{id}")
    boolean updateUser(User user);

    @Insert("insert into tb_user(username,password,name,age,sex,phone,email,did) " +
            "values(#{username},#{password},#{name},#{age},#{sex},#{phone},#{email},#{did})")
    boolean insertUser(User user);

    @Delete("delete from tb_user where id=#{id}")
    boolean deleteUser(Integer id);
}
