package net.canway.hotel_message.service;

import com.github.pagehelper.PageHelper;
import net.canway.hotel_message.dao.EquipmentMapper;
import net.canway.hotel_message.model.Equipment;
import net.canway.hotel_message.model.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipmentService {

    @Autowired
    private EquipmentMapper equipmentMapper;

    public Result findAll(){
        List<Equipment> equipments = equipmentMapper.findAll();
        return new Result("查询成功","200",equipments);
    }

    public Result findPage(Integer page,Integer size){
        PageHelper.startPage(page,size);
        List<Equipment> equipments = equipmentMapper.findAll();
        return new Result("查询成功","200",equipments);
    }

    public Result insert(Equipment equipment){
        equipmentMapper.insert(equipment);
        return new Result("添加成功","200",null);
    }

    public String findOne(Integer id) {
        String name = equipmentMapper.findOne(id);
        return name;
    }

    public Result delete(Integer id){
        String oneEquipment =findOne(id);
        if(oneEquipment == null){
            new Result("未查到该部门的信息","404",oneEquipment);
        }
        equipmentMapper.delete(id);
        return new Result("删除成功","200",null);
    }

    public Result findById(Integer id){
        Equipment selectOne = equipmentMapper.findById(id);
        if(selectOne == null){
            return new Result("未找到该部门信息","404",selectOne);
        }
        return new Result("查询成功","200",selectOne);
    }
}
