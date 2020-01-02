package net.canway.meeting_message.service;

import net.canway.meeting_message.mapper.EquipmentMapper;
import net.canway.meeting_message.mapper.MRoomMapper;
import net.canway.meeting_message.mapper.MeetingMapper;
import net.canway.meeting_message.mapper.UserMapper;
import net.canway.meeting_message.model.*;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class MeetingService {

    @Autowired
    private MeetingMapper meetingMapper;


    @Autowired
    private EquipmentMapper equipmentMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MRoomMapper mRoomMapper;

    @Autowired
    private MeetingRoomService meetingRoomService;

    public Result findAll(String message) {
        ArrayList<Meeting> meetings = null;
        if (message == null || "".equals(message)) {
            meetings = meetingMapper.findAll();
        } else {
            meetings = meetingMapper.findAllByMessage(message);
        }

        return new Result("查询成功", "200", addEquipment(meetings));
    }

    public Result limitFind(Integer size, Integer page, String message) {
        if (page <= 0 || page == null) {
            page = 1;
        }
        if (size <= 0 || size == null) {
            size = 10;
        }
        int over = size * (page - 1);

        ArrayList<Meeting> meetings = null;
        if (message == null || "".equals(message)) {
            meetings = meetingMapper.limitFind(over, size);
        } else {
            meetings = meetingMapper.limitFindByMessage(over, size, message);
        }
        return new Result("查询成功", "200", addEquipment(meetings));
    }

    public Result findPage(Integer size, String message) {
        Integer count = null;
        if (message == null || "".equals(message)) {
            count = meetingMapper.findCount();

        } else {
            count = meetingMapper.findCountByMessage(message);
        }
        return new Result("查询成功", "200", count);
    }

    public Result findOne(Integer meetingId) {
        Meeting meeting = meetingMapper.findOne(meetingId);
        ArrayList<Integer> equipmentIds = meetingMapper.findEquipmentIds(meetingId);
        List<Equipment> equipments = findEquipments(equipmentIds);
        Map<Meeting, List<Equipment>> resultMapping = new HashMap<>();
        resultMapping.put(meeting, equipments);
        return new Result("查询成功", "200", resultMapping);
    }

    @Transactional(rollbackFor = Exception.class)
    public Result delete(Integer meetingId) throws Exception {
        meetingMapper.delete(meetingId);
        meetingMapper.deleteMeeting_equ(meetingId);
        return new Result("删除成功", "200", null);
    }

    @Transactional(rollbackFor = Exception.class)
    public Result update(Meeting meeting, ArrayList<Integer> equipmentIds) throws Exception {
        Meeting beFund = meetingMapper.findOne(meeting.getId());
        if(beFund.getStartTime().getTime()<new Date().getTime()){
            return new Result("过期的会议不可以修改","500",null);
        }
        Result chooseResult = checkMeeting(meeting, equipmentIds);
        if (chooseResult.getCode().equals("500")) {
            return chooseResult;
        }
        Meeting beChanged = meetingMapper.findOne(meeting.getId());
        if (beChanged.getStartTime().getTime() != meeting.getStartTime().getTime() || beChanged.getEndTime().getTime() != meeting.getEndTime().getTime()) {
            Result result = meetingRoomService.updateStatus(meeting.getStartTime(), meeting.getEndTime(), meeting.getMeetingRoom().getId());
            if (result.getCode() != "200") {
                return result;
            }
        }
        meetingMapper.update(meeting);
        meetingMapper.deleteMeeting_equ(meeting.getId());
        if (equipmentIds != null && equipmentIds.size() != 0) {
            for (Integer equipmentId : equipmentIds) {

                meetingMapper.addMeeting_equ(equipmentId, meeting.getId());
            }
        }
        return new Result("修改成功", "200", null);
    }

    @Transactional(rollbackFor = Exception.class)
    public Result add(Meeting meeting, ArrayList<Integer> equipmentIds) throws Exception {
        Result chooseResult = checkMeeting(meeting, equipmentIds);
        if (chooseResult.getCode().equals("500")) {
            return chooseResult;
        }
        Result result = meetingRoomService.updateStatus(meeting.getStartTime(), meeting.getEndTime(), meeting.getMeetingRoom().getId());
        if (result.getCode() != "200") {
            return result;
        }
        meetingMapper.add(meeting);
        if (equipmentIds != null && equipmentIds.size() != 0) {
            for (Integer equipmentId : equipmentIds) {
                meetingMapper.addMeeting_equ(equipmentId, meetingMapper.findMaxId());
            }
        }
        return new Result("添加成功", "200", null);
    }

    private boolean checkEquipment(Integer equipmentId) {
        Equipment equipment = equipmentMapper.findById(equipmentId);
        if (equipment == null) {
            return false;
        }
        return true;
    }

    public boolean checkDate(Date startTime, Date endTime) {
        Date now = new Date();
        if (startTime.getTime() < now.getTime() || startTime.getTime() > endTime.getTime()) {
            return false;
        }
        return true;
    }


    private boolean checkMeetingRoom(Integer meetingRoomId) {
        MRoom mRoom = mRoomMapper.findOne(meetingRoomId);
        if (mRoom == null) {
            return false;
        }
        return true;
    }

    public Result checkMeeting(Meeting meeting, ArrayList<Integer> equipmentIds) {
        if (!checkDate(meeting.getStartTime(), meeting.getEndTime())) {
            return new Result("不合法的时间", "500", null);
        }
        if (!checkMeetingRoom(meeting.getMeetingRoom().getId())) {
            return new Result("不存在的会议室", "500", null);
        }
        if (equipmentIds != null && equipmentIds.size() != 0) {
            for (Integer i : equipmentIds) {
                if (!checkEquipment(i)) {
                    return new Result("不存在的设备", "500", null);
                }
                ;
            }
        }
        ArrayList<Integer> intersection = meetingMapper.findBusinessMeetingRoom(meeting.getStartTime(), meeting.getEndTime(), meeting.getId());
        User user = userMapper.findOne(meeting.getApplicant().getId());
        if (intersection.contains(meeting.getMeetingRoom().getId())) {
            return new Result("修改失败，时间段冲突", "500", null);
        }
        if (user == null) {
            return new Result("修改失败，不存在的用户", "500", null);
        }
        return new Result("可以修改", "200", null);
    }

    private List<Equipment> findEquipments(ArrayList<Integer> equipmentIds) {
        if (equipmentIds == null || equipmentIds.size() == 0) {
            return null;
        }
        List<Equipment> equipments = new ArrayList<>();
        for (Integer i : equipmentIds) {
            equipments.add(equipmentMapper.findById(i));
        }
        return equipments;
    }

    public Result findFreeMeetingRoom(Date startTime, Date endTime, Integer meetingId) {
        ArrayList<Integer> businessMeetingRoomIds = meetingMapper.findBusinessMeetingRoom(startTime, endTime, meetingId);
        List<MRoom> allRooms = mRoomMapper.findAll();
        Iterator<MRoom> iterator = allRooms.iterator();
        while (iterator.hasNext()) {
            MRoom mRoom = iterator.next();
            for (int i : businessMeetingRoomIds) {
                if (mRoom.getId().intValue() == i) {
                    iterator.remove();
                    break;
                }
            }
        }
        return new Result("查询成功", "200", allRooms);
    }


    private ArrayList<Map<String, Object>> addEquipment(ArrayList<Meeting> meetings) {
        ArrayList<Map<String, Object>> result = new ArrayList<>();
        for (Meeting meeting : meetings) {
            Map<String, Object> map = new HashMap<>();
            ArrayList<Integer> equipmentIds = meetingMapper.findEquipmentIds(meeting.getId());
            List<Equipment> equipments = findEquipments(equipmentIds);
            map.put("meeting", meeting);
            map.put("equipments", equipments);
            result.add(map);
        }
        return result;
    }
}
