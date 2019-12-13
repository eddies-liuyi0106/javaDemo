package net.canway.meeting_message.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import net.canway.meeting_message.mapper.EquipmentMapper;
import net.canway.meeting_message.model.Equipment;
import net.canway.meeting_message.model.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipmentService {

    @Autowired
    private EquipmentMapper equipmentDao;

    public Result findAll(){
        List<Equipment> equipments = equipmentDao.findAll();
        return new Result("查询成功","200",equipments);
    }

    public Result findPage(Integer page,Integer size){
        PageHelper.startPage(page,size);
        List<Equipment> equipments = equipmentDao.findAll();
        PageInfo pageInfo = new PageInfo(equipments);
        return new Result("查询成功","200",pageInfo);
    }

    public Result insert(Equipment equipment){
        equipmentDao.insert(equipment);
        return new Result("添加成功","200",null);
    }

    public String findOne(Integer id) {
        String name = equipmentDao.findOne(id);
        return name;
    }

    public Result delete(Integer id){
        String oneEquipment =findOne(id);
        if(oneEquipment == null){
            new Result("未查到该部门的信息","404",oneEquipment);
        }
        equipmentDao.delete(id);
        return new Result("删除成功","200",null);
    }

    public Result findById(Integer id){
        Equipment selectOne = equipmentDao.findById(id);
        if(selectOne == null){
            return new Result("未找到该部门信息","404",selectOne);
        }
        return new Result("查询成功","200",selectOne);
    }
}
