package net.canway.meeting_message.common;

import net.canway.meeting_message.mapper.MRoomMapper;
import net.canway.meeting_message.mapper.MeetingMapper;
import net.canway.meeting_message.model.Meeting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Component
public class initStatus implements ApplicationRunner {

    @Autowired
    private MeetingMapper meetingMapper;

    @Autowired
    private MRoomMapper roomMapper;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Date date = new Date();
        ScheduledExecutorService service = CommonTimer.service;
        ArrayList<Meeting> all = meetingMapper.findAll();
        for (Meeting meeting : all) {
            if(meeting.getStartTime().getTime()>=date.getTime()){
                ScheduledFuture start = service.schedule(new Runnable() {
                    @Override
                    public void run() {
                        roomMapper.updateStatus(meeting.getMeetingRoom().getId(), false);
                    }
                }, meeting.getStartTime().getTime() - date.getTime(), TimeUnit.MILLISECONDS);
                service.schedule(new Runnable() {
                    @Override
                    public void run() {
                        roomMapper.updateStatus(meeting.getMeetingRoom().getId(),true);
                    }
                },meeting.getEndTime().getTime()-date.getTime(),TimeUnit.MILLISECONDS);
            }
        }
    }
}
