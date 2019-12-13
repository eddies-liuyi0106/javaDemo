package net.canway.hotel_message.model;


import io.swagger.models.auth.In;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.UniqueElements;
import sun.security.util.Password;

import javax.jws.soap.SOAPBinding;

@Data
@ToString
@NoArgsConstructor
public class User {
    private Integer id;
    private String username;
    private String password;
    private String name;
    private Integer age;
    private Boolean sex;
    private String phone;
    private String email;
    private Integer did;
    private Department department;
}
