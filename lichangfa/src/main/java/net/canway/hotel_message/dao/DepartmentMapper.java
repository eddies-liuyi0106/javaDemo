package net.canway.hotel_message.dao;

import net.canway.hotel_message.model.Department;
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

    @Select("select * from tb_dep")
    List<Department> findAll();

//    List<Department> department();

    @Select("select name from tb_dep where id=#{id}")
    String findOne(Integer id);


}
