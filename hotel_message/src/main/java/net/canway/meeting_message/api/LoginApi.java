package net.canway.meeting_message.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.canway.meeting_message.model.Result;

@Api(value = "登录功能接口")
public interface LoginApi {

    @ApiOperation("权限登录")
    Result doLogin(String username, String password);


}
