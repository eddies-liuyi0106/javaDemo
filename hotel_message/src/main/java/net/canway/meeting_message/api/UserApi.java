package net.canway.meeting_message.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.canway.meeting_message.model.Result;
import net.canway.meeting_message.model.User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.NoSuchAlgorithmException;

@Api(value = "用户管理接口",description = "用户管理接口")
public interface UserApi {

    @ApiOperation("查询全部用户信息")
    public Result findAll();

    @ApiOperation("分页查询页面列表,可以对用户名模糊查询")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "page", value = "页码", paramType = "path", dataType = "int"),
                    @ApiImplicitParam(name = "size", value = "每页记录数", paramType = "path", dataType = "int"),
                    @ApiImplicitParam(name = "username", value = "姓名模糊查询", paramType = "query", dataType = "String",required = false)
            }
    )
    public Result findPage(Integer page,Integer size,String username);

    @ApiOperation("新增用户信息")
    public Result insertUser(User user) throws NoSuchAlgorithmException;

    @ApiOperation("修改用户信息")
    public Result updateUser(User user);

    @ApiOperation("查询用户信息")
    public Result findOne(Integer id);

    @ApiOperation("删除用户")
    public Result deleteUser(Integer id);

    @ApiOperation("修改密码")
    public Result changePasswd(String username,String password);

    @ApiOperation("查询当前用户")
    public Result findMe();
}
