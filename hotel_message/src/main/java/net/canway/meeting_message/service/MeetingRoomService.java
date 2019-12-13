package net.canway.meeting_message.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import net.canway.meeting_message.common.CommonTimer;
import net.canway.meeting_message.mapper.MRoomMapper;
import net.canway.meeting_message.model.MRoom;
import net.canway.meeting_message.model.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class MeetingRoomService {

    @Autowired
    private MRoomMapper roomMapper;

    public Result findAll() {
        List<MRoom> all = roomMapper.findAll();
        return new Result("查询成功", "200", all);
    }

    public Result findPage(Integer page, Integer size) {
        if (page <= 0 || page == null) {
            page = 1;
        }
        /*page = page - 1;*/
        if (size <= 0 || size == null) {
            size = 5;
        }
        PageHelper.startPage(page, size);
        List<MRoom> findPage = roomMapper.findAll();
        PageInfo pageInfo = new PageInfo(findPage);
        return new Result("查询成功", "200", pageInfo);
    }

    public Result insert(MRoom room) {
        roomMapper.insert(room);
        return new Result("添加成功", "200", null);
    }

    public Result update(MRoom room) {
        MRoom one = roomMapper.findOne(room.getId());
        if (one == null)
            return new Result("没有相关会议室信息", "404", null);
        roomMapper.update(room);
        return new Result("修改成功", "200", null);
    }

    public Result delete(Integer id) {
        MRoom one = roomMapper.findOne(id);
        if (one == null)
            return new Result("没有相关会议室信息", "404", null);
        roomMapper.delete(id);
        return new Result("删除成功", "200", null);
    }

    public Result search(String name, Integer page, Integer size) {
        if (page <= 0 || page == null) {
            page = 1;
        }
        /*page = page - 1;*/
        if (size <= 0 || size == null) {
            size = 5;
        }
        PageHelper.startPage(page, size);
        List<MRoom> search = roomMapper.search(name);
        if (search == null)
            return new Result("没有找到符合条件的信息", "404", null);
        PageInfo pageInfo = new PageInfo(search);
        return new Result("查询成功", "200", pageInfo);
    }

    public Result findOne(Integer id) {
        MRoom one = roomMapper.findOne(id);
        if (one == null)
            return new Result("没有相关会议室信息", "404", null);
        return new Result("查询成功", "200", one);
    }


    public Result updateStatus(Date startTime, Date endTime, Integer id) {
        Date date = new Date();
        long sTime = startTime.getTime() - date.getTime();
        long eTime = endTime.getTime() - date.getTime();
        if (sTime < 0) {
            return new Result("选择时间错误", "500", null);
        }
        if (endTime.getTime() - startTime.getTime() < 0) {
            return new Result("结束时间必须大于开始时间", "500", null);
        }
        ScheduledExecutorService service = CommonTimer.service;
        service.schedule(new Runnable() {
            @Override
            public void run() {
                roomMapper.updateStatus(id, false);
            }
        }, sTime, TimeUnit.MILLISECONDS);
        service.schedule(new Runnable() {
            @Override
            public void run() {
                roomMapper.updateStatus(id, true);
            }
        }, eTime, TimeUnit.MILLISECONDS);
        return new Result("状态将在指定时间改变", "200", null);
    }
}
