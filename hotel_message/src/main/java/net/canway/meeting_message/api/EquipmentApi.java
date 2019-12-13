package net.canway.meeting_message.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.canway.meeting_message.model.Equipment;
import net.canway.meeting_message.model.Result;

@Api(value = "设备管理接口", description = "设备管理接口")
public interface EquipmentApi {

    @ApiOperation("查询全部部门信息")
    Result findAll();

    @ApiOperation("分页查询分页列表")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "page",value = "页码",paramType = "path",dataType = "Integer"),
                    @ApiImplicitParam(name = "size", value = "每页记录数", paramType = "path", dataType = "Integer")
            }
    )
    Result findPage(Integer page, Integer size);

    @ApiOperation("添加设备")
    Result insert(Equipment equipment);

    @ApiOperation("删除某个设备")
    Result delete(Integer id);

    @ApiOperation("通过id查找设备")
    Result findById(Integer id);


}
