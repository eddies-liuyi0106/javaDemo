package net.canway.meeting_message.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.canway.meeting_message.model.Department;
import net.canway.meeting_message.model.Result;
import org.springframework.web.bind.annotation.PathVariable;

@Api(value = "部门管理接口", description = "部门管理接口")
public interface DepartmentApi {

    @ApiOperation("查询全部部门信息")
    Result findAll();

    @ApiOperation("分页查询页面列表")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "page", value = "页码", paramType = "path", dataType = "Integer"),
                    @ApiImplicitParam(name = "size", value = "每页记录数", paramType = "path", dataType = "Integer")
            }
    )
    Result findPage(Integer page, Integer size);

    @ApiOperation("添加部门")
    Result insert(Department department);

    @ApiOperation("删除部门")
    Result delete(Integer id);

    @ApiOperation("根据id查找部门")
    Result findById(Integer id);

    @ApiOperation("查询部门人数")
    public Result findUsers(Integer id);
}
