package net.canway.meeting_message.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.canway.meeting_message.model.MRoom;
import net.canway.meeting_message.model.Result;

@Api(value = "会议室接口",description = "会议室接口")
public interface MeetingRoomApi {

    @ApiOperation("查询全部会议室信息")
    public Result findAll();

    @ApiOperation("分页查询页面列表")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "page", value = "页码", paramType = "path", dataType = "Integer"),
                    @ApiImplicitParam(name = "size", value = "每页记录数", paramType = "path", dataType = "Integer")
            }
    )
    public Result findPage(Integer page, Integer size);

    @ApiOperation("新增会议室")
    public Result insert(MRoom room);

    @ApiOperation("更新会议室信息")
    public Result update(MRoom room);

    @ApiOperation("删除会议室信息")
    public Result delete(Integer id);

    @ApiOperation("模糊查询会议室信息")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "name", value = "会议室名称", paramType = "path", dataType = "String"),
                    @ApiImplicitParam(name = "page", value = "页码", paramType = "path", dataType = "Integer"),
                    @ApiImplicitParam(name = "size", value = "每页记录数", paramType = "path", dataType = "Integer")
            }
    )
    public Result search(String name,Integer page,Integer size);

    @ApiOperation("根据页面Id查询会议室信息")
    public Result findOne(Integer id);
}
