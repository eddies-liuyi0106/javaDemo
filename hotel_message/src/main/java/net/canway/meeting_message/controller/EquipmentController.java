package net.canway.meeting_message.controller;

import net.canway.meeting_message.api.EquipmentApi;
import net.canway.meeting_message.model.Equipment;
import net.canway.meeting_message.model.Result;
import net.canway.meeting_message.service.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/equipment")
public class EquipmentController implements EquipmentApi {

    @Autowired
    private EquipmentService equipmentService;

    @GetMapping("/findAll")
    public Result findAll() {
        return equipmentService.findAll();
    }

    @GetMapping("/findPage/{page}/{size}")
    public Result findPage(@PathVariable("page")Integer page,@PathVariable("size")Integer size) {
        return equipmentService.findPage(page, size);
    }

    @Override
    @PostMapping("/insert")
    public Result insert(@RequestBody Equipment equipment) {
        return equipmentService.insert(equipment);
    }

    @GetMapping("/delete/{id}")
    public Result delete(@PathVariable("id") Integer id) {
        return equipmentService.delete(id);
    }

    @GetMapping("/findById/{id}")
    public Result findById(@PathVariable("id") Integer id) {
        return equipmentService.findById(id);
    }
}
