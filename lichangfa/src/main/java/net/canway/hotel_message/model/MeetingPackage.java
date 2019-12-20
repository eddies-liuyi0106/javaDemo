package net.canway.hotel_message.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.Valid;
import java.util.ArrayList;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MeetingPackage {
    @Valid
    private Meeting meeting;
    private ArrayList<Integer> equipmentIds;
}
