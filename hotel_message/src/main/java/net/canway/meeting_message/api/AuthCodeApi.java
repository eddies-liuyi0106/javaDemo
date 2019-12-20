package net.canway.meeting_message.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.canway.meeting_message.model.Result;
import org.springframework.web.bind.annotation.PathVariable;

@Api(value = "部门管理接口", description = "找回密码模块")
public interface AuthCodeApi {

    @ApiOperation("找回密码")
    public Result findPassWord(String name);

    @ApiOperation("校验验证码")
    public Result auth(String code,String name);
}
