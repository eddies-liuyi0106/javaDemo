package net.canway.meeting_message.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MRoom {
    private Integer id;
    @NotEmpty(message = "会议室名不能为空")
    private String name;
    @NotEmpty(message = "预定人不能为空")
    private String num_peop;
    /**
     * true为空闲
     * false为占用
     */
    private boolean status;
}
