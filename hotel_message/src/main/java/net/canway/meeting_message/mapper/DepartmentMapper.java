package net.canway.meeting_message.mapper;

import net.canway.meeting_message.model.Department;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentMapper {

    @Insert("insert into tb_dep values(#{id},#{name},#{num})")
    boolean insert(Department department);

    @Delete("delete from tb_dep where id=#{id}")
    boolean delete(Integer id);

    @Select("select * from tb_dep where id=#{id}")
    Department findById(Integer id);

    @Select("select d.id,d.name,count(u.did) num FROM tb_user as u RIGHT JOIN tb_dep as d ON u.did=d.id GROUP BY d.id")
    List<Department> findAll();

    @Select("select name from tb_dep where id=#{id}")
    String findOne(Integer id);


}
