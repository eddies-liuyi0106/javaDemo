package net.canway.hotel_message.dao;

import net.canway.hotel_message.model.MRoom;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MRoomMapper {

    @Select("select * from tb_meeting_Room")
    public List<MRoom> findAll();

    @Insert("insert into tb_meeting_Room values(#{id},#{name},#{num_peop},#{status})")
    public void insert(MRoom room);

    @Update("update tb_meeting_Room set name=#{name},num_peop=#{num_peop},status=#{status} where id=#{id}")
    public void update(MRoom room);

    @Delete("DELETE FROM tb_meeting_Room WHERE id=#{id}")
    public void delete(Integer id);

    @Select("select * from tb_meeting_Room where name like '%${name}%'")
    public List<MRoom> search(@Param("name") String name);

    @Select("select * from tb_meeting_Room where id=#{id}")
    public MRoom findOne(Integer id);

    @Update("update tb_meeting_Room set status=#{status} where id=#{id}")
    public void updateStatus(@Param("id") Integer id, @Param("status") boolean status);

}
