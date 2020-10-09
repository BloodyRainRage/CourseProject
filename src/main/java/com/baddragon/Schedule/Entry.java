package com.baddragon.Schedule;

import com.baddragon.Vessel.TypeOfCargo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.Timer;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Entry {

    public static DateFormat dateFormatter = new SimpleDateFormat("dd:MM:YYYY");
    public static DateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss");

    protected Long        dateOfArrivalAsLong;
    protected Timer       timeOfArrival;
    protected String      vesselName;

    protected TypeOfCargo type;
    protected Double      weight;

    protected Long        willStayFor;

    public Date getDateOfArrival(){
        return new Date(dateOfArrivalAsLong);
    }


    @Override
    public String toString(){
//        Date date = new Date(getDateOfArrivalAsLong());
//        DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        //timeFormatter.setTimeZone(TimeZone.getTimeZone("Europe/Moscow"));
        return "Date of arrival:       " + dateFormatter.format(getDateOfArrival()) + "\n" +
                    "Time of arrival:       " + timeFormatter.format(getDateOfArrival()) + "\n" +
                    "Vessel name:           " + getVesselName() + "\n" +
                    "Type of cargo:         " + getType() + "\n" +
                    "Weight of cargo:       " + getWeight() + " kg" + "\n" +
                    "Planned day to unload: " + getWillStayFor();

    }
}
