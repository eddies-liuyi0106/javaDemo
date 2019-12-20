package net.canway.meeting_message.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MRoom {
    private Integer id;
    @NotEmpty(message = "会议室名不能为空 ")
    private String name;
    @NotEmpty(message = "容纳人数不能为空 ")
    private String num_peop;
    /**
     * true为空闲 1
     * false为占用 0
     */
    private boolean status;
}
