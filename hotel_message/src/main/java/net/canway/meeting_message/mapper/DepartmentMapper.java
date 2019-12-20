package net.canway.meeting_message.mapper;

import net.canway.meeting_message.model.Department;
import net.canway.meeting_message.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentMapper {

    @Insert("insert into tb_dep values(null,#{name})")
    boolean insert(Department department);

    @Delete("delete from tb_dep where id=#{id}")
    boolean delete(Integer id);

    /*select d.id,d.name,count(u.did) num FROM tb_user as u RIGHT JOIN tb_dep as d ON u.did=d.id where d.id=#{id} GROUP BY d.id*/
    /*select * from tb_dep where id=#{id}*/
    @Select("select d.id,d.name,count(u.did) num FROM tb_user as u RIGHT JOIN tb_dep as d ON u.did=d.id where d.id=#{id} GROUP BY d.id")
    Department findById(Integer id);

    @Select("select d.id,d.name,count(u.did) num FROM tb_user as u RIGHT JOIN tb_dep as d ON u.did=d.id GROUP BY d.id")
    List<Department> findAll();

    @Select("select name from tb_dep where id=#{id}")
    String findOne(Integer id);

    @Select("select * from tb_user where did=#{id}")
    @Results({
            @Result(column="did",property="department",javaType= Department.class,
                    one=@One(select="net.canway.meeting_message.mapper.DepartmentMapper.findById"))
    })
    List<User> findUsers(Integer id);
}
