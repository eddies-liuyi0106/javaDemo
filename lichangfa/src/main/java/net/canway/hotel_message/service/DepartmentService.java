package net.canway.hotel_message.service;

import com.github.pagehelper.PageHelper;
import net.canway.hotel_message.dao.DepartmentMapper;
import net.canway.hotel_message.model.Department;
import net.canway.hotel_message.model.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;

    public Result findAll(){
        List<Department> departments = departmentMapper.findAll();
        return new Result("查询成功","200",departments);
    }

    public Result findPage(Integer page,Integer size){
        PageHelper.startPage(page,size);
        List<Department> departments = departmentMapper.findAll();
        return new Result("查询成功","200",departments);
    }

    public Result insert(Department department){
        departmentMapper.insert(department);
        return new Result("添加成功","200",null);
    }

    public String findOne(Integer id) {
        String name = departmentMapper.findOne(id);
        return name;
    }

    public Result delete(Integer id){
        String oneDepartment =findOne(id);
        if(oneDepartment == null){
            new Result("未查到该部门的信息","404",oneDepartment);
        }
        departmentMapper.delete(id);
        return new Result("删除成功","200",null);
    }

    public Result selectById(Integer id){
        Department selectOne = departmentMapper.findById(id);
        if(selectOne == null){
            return new Result("未找到该部门信息","404",selectOne);
        }
        return new Result("查询成功","200",selectOne);
    }
}
