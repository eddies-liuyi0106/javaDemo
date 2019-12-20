package net.canway.hotel_message.controller;

import net.canway.hotel_message.model.Result;
import net.canway.hotel_message.service.MeetingRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/MRoom")
public class MRoomController {

    @Autowired
    private MeetingRoomService meetingRoomService;

    @RequestMapping("/findAll")
    public Result findAll(){
        return meetingRoomService.findAll();
    }
}
