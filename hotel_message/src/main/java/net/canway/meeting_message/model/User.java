package net.canway.meeting_message.model;


import io.swagger.models.auth.In;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.UniqueElements;
import sun.security.util.Password;

import javax.jws.soap.SOAPBinding;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@ToString
@NoArgsConstructor
public class User {
    private Integer id;
    @NotNull
    @NotBlank
    private String username;
    private String password;
    @NotNull
    @NotBlank
    private String name;
    @NotNull
//    @NotBlank
    private Integer age;
    @NotNull
//    @NotBlank
    private Boolean sex;
    @Pattern(regexp = "^1([3456789])\\d{9}$",message = "请输入正确的手机格式")
    private String phone;
    @Pattern(regexp = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$",message = "请输入正确的邮箱格式")
    private String email;
    @NotNull
//    @NotBlank
    private Integer did;
    private Department department;
}
