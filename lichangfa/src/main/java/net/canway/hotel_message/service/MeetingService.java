package net.canway.hotel_message.service;

import net.canway.hotel_message.dao.EquipmentMapper;
import net.canway.hotel_message.dao.MRoomMapper;
import net.canway.hotel_message.dao.MeetingMapper;
import net.canway.hotel_message.dao.UserMapper;
import net.canway.hotel_message.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        if (size <= 0 || size == null) {
            size = 10;
        }
        int page = 0;
        if (count % size != 0) {
            page = count / size + 1;
        } else {
            page = count / size;
        }
        return new Result("查询成功", "200", page);
    }

    public Result findOne(Integer meetingId) {
        Meeting meeting = meetingMapper.findOne(meetingId);
        ArrayList<Integer> equipmentIds = meetingMapper.findEquipmentIds(meetingId);
        List<Equipment> equipments = findEquipments(equipmentIds);
        Map<Meeting, List<Equipment>> resultMapping = new HashMap<>();
        resultMapping.put(meeting, equipments);
        return new Result("查询成功", "200", resultMapping);
    }

    public Result delete(Integer meetingId) {
        boolean success = meetingMapper.delete(meetingId);
        if (success) {
            return new Result("删除成功", "200", null);
        }
        return new Result("删除失败，不存在此会议", "500", null);
    }

    public Result update(Meeting meeting, ArrayList<Integer> equipmentIds) {
        Result chooseResult = checkMeeting(meeting, equipmentIds);
        if (chooseResult.getCode().equals("500")) {
            return chooseResult;
        }
        Meeting beChanged = meetingMapper.findOne(meeting.getId());
        if (beChanged.getStartTime().getTime() != meeting.getStartTime().getTime() || beChanged.getEndTime().getTime() != meeting.getEndTime().getTime()) {
            meetingRoomService.updateStatus(meeting.getStartTime(), meeting.getEndTime(), meeting.getMeetingRoom().getId());
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

    public Result add(Meeting meeting, ArrayList<Integer> equipmentIds) {
        Result chooseResult = checkMeeting(meeting, equipmentIds);
        if (chooseResult.getCode().equals("500")) {
            return chooseResult;
        }
        meetingRoomService.updateStatus(meeting.getStartTime(), meeting.getEndTime(), meeting.getMeetingRoom().getId());
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

    public Result checkMeeting(Meeting meeting, ArrayList<Integer> equipmentIds) {
        if (equipmentIds != null && equipmentIds.size() != 0) {
            for (Integer i : equipmentIds) {
                checkEquipment(i);
            }
        }
        ArrayList<Integer> intersection = meetingMapper.findBusinessMeetingRoom(meeting.getStartTime(), meeting.getEndTime(), meeting.getId());
        User user = userMapper.findOne(meeting.getApplicant().getId());
        if (intersection != null && intersection.size() != 0) {
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

    public Result findFreeMeetingRoom(Date startTime, Date endTime) {
        ArrayList<Integer> businessMeetingRoomIds = meetingMapper.findBusinessMeetingRoom(startTime, endTime, -1);
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


    private Map<Meeting, List<Equipment>> addEquipment(ArrayList<Meeting> meetings) {
        Map<Meeting, List<Equipment>> result = new HashMap<>();
        for (Meeting meeting : meetings) {
            ArrayList<Integer> equipmentIds = meetingMapper.findEquipmentIds(meeting.getId());
            List<Equipment> equipments = findEquipments(equipmentIds);
            result.put(meeting, equipments);
        }
        return result;
    }
}
