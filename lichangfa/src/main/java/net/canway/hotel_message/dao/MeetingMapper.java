package net.canway.hotel_message.dao;

import net.canway.hotel_message.model.MRoom;
import net.canway.hotel_message.model.Meeting;
import net.canway.hotel_message.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;

@Repository
public interface MeetingMapper {

    @Select("SELECT * FROM tb_meeting WHERE LOCATE(#{message},name)>0")
    @Results(id = "resultMap1", value = {
            @Result(column = "id", property = "id"),
            @Result(column = "name", property = "name"),
            @Result(
                    property = "applicant",
                    javaType = User.class,
                    column = "reserve_id",
                    one = @One(select = "net.canway.hotel_message.dao.UserMapper.findOne")
            ),
            @Result(column = "startTime", property = "startTime"),
            @Result(column = "endTime", property = "endTime"),
            @Result(
                    property = "meetingRoom",
                    javaType = MRoom.class,
                    column = "mr_id",
                    one = @One(select = "net.canway.hotel_message.dao.MRoomMapper.findOne")
            )
    })
    ArrayList<Meeting> findAllByMessage(String message);

    @Select("SELECT * FROM tb_meeting")
    @ResultMap(value = {"resultMap1"})
    ArrayList<Meeting> findAll();

    @Select("SELECT * FROM tb_meeting WHERE LOCATE(#{message},name)>0 LIMIT #{over},#{size}")
    @ResultMap(value = {"resultMap1"})
    ArrayList<Meeting> limitFindByMessage(@Param("over") Integer over, @Param("size") Integer size, @Param("message") String message);

    @Select("SELECT * FROM tb_meeting LIMIT #{over},#{size}")
    @ResultMap(value = {"resultMap1"})
    ArrayList<Meeting> limitFind(@Param("over") Integer over, @Param("size") Integer page);

    @Select("SELECT * FROM tb_meeting WHERE id=#{meetingId}")
    @ResultMap(value = {"resultMap1"})
    Meeting findOne(Integer meetingId);

    @Select("SELECT max(id) FROM tb_meeting")
    @ResultType(value = java.lang.Integer.class)
    Integer findMaxId();

    @Select("SELECT count(id) FROM tb_meeting")
    @ResultType(value = java.lang.Integer.class)
    Integer findCount();

    @Select("SELECT count(id) FROM tb_meeting WHERE LOCATE(#{message},name)>0")
    @ResultType(value = java.lang.Integer.class)
    Integer findCountByMessage(String message);

    @Delete("DELETE FROM tb_meeting WHERE id=#{meetingId}")
    boolean delete(Integer meetingId);

    @Update("UPDATE tb_meeting SET name=#{name},reserve_id=#{applicant.id},startTime=#{startTime},endTime=#{endTime},mr_id=#{meetingRoom.id} WHERE id=#{id}")
    boolean update(Meeting meeting);

    @Insert("INSERT INTO tb_meeting VALUES(null,#{name},#{applicant.id},#{startTime},#{endTime},#{meetingRoom.id})")
    boolean add(Meeting meeting);

    @Insert("INSERT INTO tb_equ_meeting VALUES(#{eid},#{mid})")
    boolean addMeeting_equ(@Param("eid") Integer eid, @Param("mid") Integer mid);

    @Delete("DELETE FROM tb_equ_meeting WHERE mid=#{mid}")
    boolean deleteMeeting_equ(@Param("mid") Integer mid);

    @Select("SELECT DISTINCT id FROM tb_meeting WHERE id!=#{meetingId} AND (startTime BETWEEN #{startTime} AND #{endTime} OR endTime BETWEEN #{startTime} AND #{endTime}) ")
    @ResultType(value = java.lang.Integer.class)
    ArrayList<Integer> findBusinessMeetingRoom(@Param("startTime") Date startTime, @Param("endTime") Date endTime, @Param("meetingId") Integer meetingId);

    @Select("Select eid FROM tb_equ_meeting WHERE mid=#{meetingId}")
    @ResultType(value = java.lang.Integer.class)
    ArrayList<Integer> findEquipmentIds(Integer meetingId);

}
