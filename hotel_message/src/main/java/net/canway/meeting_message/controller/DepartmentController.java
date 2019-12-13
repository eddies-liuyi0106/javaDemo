package net.canway.meeting_message.controller;

import net.canway.meeting_message.api.DepartmentApi;
import net.canway.meeting_message.model.Department;
import net.canway.meeting_message.model.Result;
import net.canway.meeting_message.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/department")
public class DepartmentController implements DepartmentApi {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping("/findAll")
    public Result findAll(){
        return departmentService.findAll();
    }

    @GetMapping("/findPage/{page}/{size}")
    public Result findPage(@PathVariable("page")Integer page,@PathVariable("size")Integer size){
        return departmentService.findPage(page,size);
    }

    @Override
    @PostMapping("/insert")
    public Result insert(Department department){
        return departmentService.insert(department);
    }

    @GetMapping("/delete")
    public Result delete(Integer id){
        return departmentService.delete(id);
    }

    @GetMapping("/findById")
    public Result findById(Integer id){
        return departmentService.findById(id);
    }
}
