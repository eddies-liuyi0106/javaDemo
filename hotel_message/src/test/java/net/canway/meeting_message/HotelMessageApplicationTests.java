package net.canway.meeting_message;

import net.canway.meeting_message.mapper.MRoomMapper;
import net.canway.meeting_message.mapper.MeetingMapper;
import net.canway.meeting_message.model.MRoom;
import net.canway.meeting_message.model.Meeting;
import net.canway.meeting_message.model.Result;
import net.canway.meeting_message.service.MeetingRoomService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class HotelMessageApplicationTests {


    @Autowired
    MRoomMapper roomMapper;

    @Test
    public void test10(){
        MRoom room=new MRoom();
        room.setName("梨花厅");
        room.setNum_peop("2000");
        room.setStatus(false);
        roomMapper.insert(room);
    }
    @Test
    public void test11(){
        List<MRoom> all = roomMapper.findAll();
        for (MRoom room : all) {
            System.out.println(room);
        }
    }

    @Test
    public void test12(){
        MRoom one = roomMapper.findOne(8);
        /*one.setName("牡丹厅");
        roomMapper.update(one);*/
        System.out.println(one);
    }

    @Test
    public void test13(){
        List<MRoom> search = roomMapper.search("牡丹");
        for (MRoom room : search) {
            System.out.println(room);
        }
    }

    @Autowired
    MeetingMapper meetingMapper;

    @Test
    public void test14(){
        List<Meeting> all = meetingMapper.findAll();
        for (Meeting meeting : all) {
            System.out.println(meeting);
        }
    }

    @Test
    public void test15(){
        ScheduledExecutorService service = Executors.newScheduledThreadPool(10);
        long initialDelay=1;
        long period=1;
        final int[] i = {0};
        while (true){
            service.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    System.out.println(i[0]++);
                }
            },initialDelay,period, TimeUnit.SECONDS);
        }
    }

    @Autowired
    MeetingRoomService roomService;
    @Test
    public void test16(){
        Date startTime = new Date("2019/12/10 16:46:00");
        Date endTime = new Date("2019/12/10 16:50:00");
        System.out.println(startTime);
        Result result = roomService.updateStatus(startTime, endTime, 2);
        System.out.println(result);
    }

    @Test
    public void test17(){
        roomMapper.updateStatus(2,true);
    }


    @Test
    public void test18(){
        ArrayList<Meeting> all = meetingMapper.findAll();
        for (Meeting meeting : all) {
            System.out.println(meeting);
        }
    }
}
