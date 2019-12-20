package net.canway.meeting_message.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Meeting {
    private Integer id;
    @NotNull(message = "会议名不能为空")
    private String name;
    @Valid
    private User applicant;
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private Date startTime;
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private Date endTime;
    @Valid
    private MRoom meetingRoom;
}
