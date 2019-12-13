package net.canway.meeting_message.controller;

import net.canway.meeting_message.api.MeetingRoomApi;
import net.canway.meeting_message.model.MRoom;
import net.canway.meeting_message.model.Result;
import net.canway.meeting_message.service.MeetingRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mroom")
public class MeetingRoomController implements MeetingRoomApi {

    @Autowired
    private MeetingRoomService roomService;

    @Override
    @GetMapping("/findAll")
    public Result findAll() {
        return roomService.findAll();
    }

    @Override
    @GetMapping("/findPage/{page}/{size}")
    public Result findPage(@PathVariable("page") Integer page,@PathVariable("size") Integer size) {
        return roomService.findPage(page,size);
    }

    @Override
    @PostMapping("/insert")
    public Result insert(MRoom room) {
        return roomService.insert(room);
    }

    @Override
    @PutMapping("/update")
    public Result update(MRoom room) {
        return roomService.update(room);
    }

    @Override
    @GetMapping("/delete/{id}")
    public Result delete(@PathVariable("id") Integer id) {
        return roomService.delete(id);
    }

    @Override
    @GetMapping("/search/{name}/{page}/{size}")
    public Result search(@PathVariable("name") String name,@PathVariable("page") Integer page,@PathVariable("size") Integer size) {
        return roomService.search(name,page,size);
    }

    @Override
    @GetMapping("/findOne/{id}")
    public Result findOne(@PathVariable("id") Integer id) {
        return roomService.findOne(id);
    }
}
