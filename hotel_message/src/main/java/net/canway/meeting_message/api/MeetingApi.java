package net.canway.meeting_message.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.canway.meeting_message.model.MeetingPackage;
import net.canway.meeting_message.model.Result;


import java.util.Date;

@Api(value = "会议管理接口", description = "会议管理接口")
public interface MeetingApi {

    @ApiOperation("查询所有会议")
    public Result findAll();

    @ApiOperation("查询所有会议，带有模糊查询")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "message", value = "会议名模糊信息", paramType = "path", dataType = "String")
            }
    )
    public Result findAllByMessage(String message);

    @ApiOperation("分页查询")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "size", value = "每页记录数", paramType = "path", dataType = "Integer"),
                    @ApiImplicitParam(name = "page", value = "页码", paramType = "path", dataType = "Integer")

            }
    )
    public Result limitFind(Integer size, Integer page);

    @ApiOperation("分页查询，带有模糊查询")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "size", value = "每页记录数", paramType = "path", dataType = "Integer"),
                    @ApiImplicitParam(name = "page", value = "页码", paramType = "path", dataType = "Integer"),
                    @ApiImplicitParam(name = "message", value = "会议名模糊信息", paramType = "path", dataType = "String")

            }
    )
    public Result limitFindByMessage(Integer size, Integer page, String message);

    @ApiOperation("查询总页数")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "size", value = "每页记录数", paramType = "path", dataType = "Integer")
            }
    )
    public Result findPage(Integer size);

    @ApiOperation("查询总页数")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "size", value = "每页记录数", paramType = "path", dataType = "Integer"),
                    @ApiImplicitParam(name = "message", value = "会议名模糊信息", paramType = "path", dataType = "String")
            }
    )
    public Result findPageByMessage(Integer size, String message);

    @ApiOperation("删除会议")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "meetingId", value = "会议Id", paramType = "path", dataType = "Integer")
            }
    )
    public Result delete(Integer meetingId) throws Exception;

    @ApiOperation("修改会议")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "meetingPackage", value = "包含会议和设备的实体", paramType = "path", dataType = "meetingPackage")
            }
    )
    public Result update(MeetingPackage meetingPackage) throws Exception;

    @ApiOperation("增加会议")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "meetingPackage", value = "包含会议和设备的实体", paramType = "path", dataType = "meetingPackage")
            }
    )
    public Result addMeeting(MeetingPackage meetingPackage) throws Exception;

    @ApiOperation("查询某个会议的详细信息")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "meetingId", value = "会议id", paramType = "path", dataType = "Integer")
            }
    )
    public Result findOne(Integer meetingId);

    @ApiOperation("查询当前时间段内空闲的会议室，修改时用")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "startTime", value = "会议开始时间", paramType = "path", dataType = "Date"),
                    @ApiImplicitParam(name = "endTime", value = "会议结束时间", paramType = "path", dataType = "Date"),
                    @ApiImplicitParam(name = "meetingId", value = "会议id", paramType = "path", dataType = "Integer")
            }
    )
    public Result findFreeMeetingRoom(Date startTime, Date endTime, Integer meetingId);
}
