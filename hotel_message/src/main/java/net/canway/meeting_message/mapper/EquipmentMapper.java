package net.canway.meeting_message.mapper;

import net.canway.meeting_message.model.Equipment;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipmentMapper {

    @Insert("insert into tb_equ values(null,#{name})")
    boolean insert(Equipment equipment);

    @Delete("delete from tb_equ where id=#{id}")
    boolean delete(Integer id);

    @Select("select * from tb_equ where id=#{id}")
    Equipment findById(Integer id);

    @Select("select * from tb_equ")
    List<Equipment> findAll();

    @Select("select name from tb_equ where id=#{id}")
    String findOne(Integer id);

}
