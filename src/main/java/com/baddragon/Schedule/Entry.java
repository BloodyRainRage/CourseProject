package com.baddragon.Schedule;

import com.baddragon.Vessel.TypeOfCargo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Timer;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Entry {

    protected Date        dateOfArrival;
    protected Timer       timeOfArrival;
    protected String      vesselName;

    protected TypeOfCargo type;
    protected Double      weight;

    protected Long        willStayFor;


}
