package net.canway.meeting_message.controller;

import net.canway.meeting_message.api.MeetingApi;
import net.canway.meeting_message.model.MeetingPackage;
import net.canway.meeting_message.model.Result;
import net.canway.meeting_message.service.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;


/**
 * 会议管理的Controller类
 */
@RestController
@RequestMapping("/meeting")
public class MeetingController implements MeetingApi {

    @Autowired
    private MeetingService meetingService;

    @ResponseBody
    @GetMapping("/findAll")
    public Result findAll() {
        return meetingService.findAll(null);
    }

    @ResponseBody
    @GetMapping("/findAll/{message}")
    public Result findAllByMessage(@PathVariable(name = "message") String message) {
        return meetingService.findAll(message);
    }

    @ResponseBody
    @GetMapping("/limitFind/{size}/{page}")
    public Result limitFind(@PathVariable(name = "size") Integer size, @PathVariable(name = "page") Integer page) {
        return meetingService.limitFind(size, page, null);
    }

    @ResponseBody
    @GetMapping("/limitFind/{size}/{page}/{message}")
    public Result limitFindByMessage(@PathVariable(name = "size") Integer size, @PathVariable(name = "page") Integer page, @PathVariable(name = "message") String message) {
        return meetingService.limitFind(size, page, message);
    }

    @ResponseBody
    @GetMapping("/findPage/{size}")
    public Result findPage(@PathVariable(name = "size") Integer size) {
        return meetingService.findPage(size, null);
    }

    @ResponseBody
    @GetMapping("/findPage/{size}/{message}")
    public Result findPageByMessage(@PathVariable(name = "size") Integer size, @PathVariable(name = "message") String message) {
        return meetingService.findPage(size, message);
    }

    @ResponseBody
    @GetMapping("/delete/{meetingId}")
    public Result delete(@PathVariable(name = "meetingId") Integer meetingId) throws Exception {
        meetingService.delete(meetingId);
        return new Result("删除成功", "200", null);
    }

    @ResponseBody
    @PostMapping("/update")
    public Result update(@Validated @RequestBody MeetingPackage meetingPackage) throws Exception {
        return meetingService.update(meetingPackage.getMeeting(), meetingPackage.getEquipmentIds());
    }

    @ResponseBody
    @PostMapping("/add")
    public Result addMeeting(@Validated @RequestBody MeetingPackage meetingPackage) throws Exception {
        return meetingService.add(meetingPackage.getMeeting(), meetingPackage.getEquipmentIds());
    }

    @ResponseBody
    @GetMapping("/findOne/{meetingId}")
    public Result findOne(@PathVariable(name = "meetingId") Integer meetingId) {
        return meetingService.findOne(meetingId);
    }

    @ResponseBody
    @GetMapping("/findFreeMeetingRoom/{startTime}/{endTime}/{meetingId}")
    public Result findFreeMeetingRoom(@PathVariable(name = "startTime") Date startTime, @PathVariable(name = "endTime") Date endTime, @PathVariable(name = "meetingId") Integer meetingId) {
        return meetingService.findFreeMeetingRoom(startTime, endTime, meetingId);
    }
}
