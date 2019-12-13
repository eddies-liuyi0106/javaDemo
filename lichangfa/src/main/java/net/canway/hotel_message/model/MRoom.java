package net.canway.hotel_message.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MRoom {
    private Integer id;
    private String name;
    private String num_peop;
    private boolean status;
}
