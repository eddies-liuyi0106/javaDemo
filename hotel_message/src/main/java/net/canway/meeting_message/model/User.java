package net.canway.meeting_message.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@ToString
@NoArgsConstructor
public class User {
    private Integer id;
    @NotEmpty(message = "用户名不能为空")
    private String username;
    private String password;

    private String name;
    @NotNull(message = "年龄不能为空")
    private Integer age;
    @NotNull(message = "性别不能为空")
    private Boolean sex;
    @Pattern(regexp = "^1([3456789])\\d{9}$",message = "请输入正确的手机格式")
    private String phone;
    @Pattern(regexp = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$",message = "请输入正确的邮箱格式")
    private String email;
    @NotNull(message = "部门不能为空")
    private Integer did;
    private Department department;
    private String salt;
}
