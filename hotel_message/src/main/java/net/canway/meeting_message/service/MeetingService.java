package net.canway.meeting_message.service;

import net.canway.meeting_message.mapper.EquipmentMapper;
import net.canway.meeting_message.mapper.MRoomMapper;
import net.canway.meeting_message.mapper.MeetingMapper;
import net.canway.meeting_message.mapper.UserMapper;
import net.canway.meeting_message.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MeetingService {

    @Autowired
    private MeetingMapper meetingDao;


    @Autowired
    private EquipmentMapper equipmentDao;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MRoomMapper mRoomMapper;

    @Autowired
    private MeetingRoomService meetingRoomService;

    public Result findAll(String message) {
        ArrayList<Meeting> meetings = null;
        if (message == null || "".equals(message)) {
            meetings = meetingDao.findAll();
        } else {
            meetings = meetingDao.findAllByMessage(message);
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
            meetings = meetingDao.limitFind(over, size);
        } else {
            meetings = meetingDao.limitFindByMessage(over, size, message);
        }
        return new Result("查询成功", "200", addEquipment(meetings));
    }

    public Result findPage(Integer size, String message) {
        Integer count = null;
        if (message == null || "".equals(message)) {
            count = meetingDao.findCount();

        } else {
            count = meetingDao.findCountByMessage(message);
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
        Meeting meeting = meetingDao.findOne(meetingId);
        ArrayList<Integer> equipmentIds = meetingDao.findEquipmentIds(meetingId);
        List<Equipment> equipments = findEquipments(equipmentIds);
        Map<Meeting, List<Equipment>> resultMapping = new HashMap<>();
        resultMapping.put(meeting, equipments);
        return new Result("查询成功", "200", resultMapping);
    }

    public Result delete(Integer meetingId) {
        boolean success = meetingDao.delete(meetingId);
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
        Meeting beChanged = meetingDao.findOne(meeting.getId());
        if (beChanged.getStartTime().getTime() != meeting.getStartTime().getTime() || beChanged.getEndTime().getTime() != meeting.getEndTime().getTime()) {
            Result result = meetingRoomService.updateStatus(meeting.getStartTime(), meeting.getEndTime(), meeting.getMeetingRoom().getId());
            if (result.getCode() != "200") {
                return result;
            }
        }
        meetingDao.update(meeting);
        meetingDao.deleteMeeting_equ(meeting.getId());
        if (equipmentIds != null && equipmentIds.size() != 0) {
            for (Integer equipmentId : equipmentIds) {

                meetingDao.addMeeting_equ(equipmentId, meeting.getId());
            }
        }
        return new Result("修改成功", "200", null);
    }

    public Result add(Meeting meeting, ArrayList<Integer> equipmentIds) {
        Result chooseResult = checkMeeting(meeting, equipmentIds);
        if (chooseResult.getCode().equals("500")) {
            return chooseResult;
        }
        Result result = meetingRoomService.updateStatus(meeting.getStartTime(), meeting.getEndTime(), meeting.getMeetingRoom().getId());
        if (result.getCode() != "200") {
            return result;
        }
        meetingDao.add(meeting);
        if (equipmentIds != null && equipmentIds.size() != 0) {
            for (Integer equipmentId : equipmentIds) {
                meetingDao.addMeeting_equ(equipmentId, meetingDao.findMaxId());
            }
        }
        return new Result("添加成功", "200", null);
    }

    private boolean checkEquipment(Integer equipmentId) {
        Equipment equipment = equipmentDao.findById(equipmentId);
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

    public Result checkMeeting(Meeting meeting, ArrayList<Integer> equipmentIds) {
        if (!checkDate(meeting.getStartTime(), meeting.getEndTime())) {
            return new Result("不合法的时间", "500", null);
        }

        if (equipmentIds != null && equipmentIds.size() != 0) {
            for (Integer i : equipmentIds) {
                if (!checkEquipment(i)) {
                    return new Result("不存在的设备", "500", null);
                }
                ;
            }
        }
        ArrayList<Integer> intersection = meetingDao.findBusinessMeetingRoom(meeting.getStartTime(), meeting.getEndTime(), meeting.getId());
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
            equipments.add(equipmentDao.findById(i));
        }
        return equipments;
    }

    public Result findFreeMeetingRoom(Date startTime, Date endTime) {
        ArrayList<Integer> businessMeetingRoomIds = meetingDao.findBusinessMeetingRoom(startTime, endTime, -1);
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
            ArrayList<Integer> equipmentIds = meetingDao.findEquipmentIds(meeting.getId());
            List<Equipment> equipments = findEquipments(equipmentIds);
            result.put(meeting, equipments);
        }
        return result;
    }
}
