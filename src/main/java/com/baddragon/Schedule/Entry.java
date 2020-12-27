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
public class Entry implements Comparable<Entry>, Cloneable {

    public static DateFormat dateFormatter = new SimpleDateFormat("dd:MM:YYYY");
    public static DateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss");

    protected Long        dateOfArrivalAsLong; //planned date of arrival
    protected Timer       timeOfArrival;       //time of arrival
    protected Long        scheduleDeviation;    //deviation from schedule (arrived earlier or delayed)

    protected String      vesselName;

    protected TypeOfCargo type;
    protected Double      weight;

    protected Long        willStayFor; //planned amount of days until unload w/0 delay

    protected int         daysPassed; //days of unload passed

    protected Long        daysWithDelay; //how much the vessel will stay for unload after delay simulation
    protected Status      status;


    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public Date getDateOfArrival(){
        return new Date(dateOfArrivalAsLong);
    }

    @Override
    public int compareTo(Entry e){
        return this.getDateOfArrivalAsLong().compareTo(e.getDateOfArrivalAsLong());
    }

    @Override
    public String toString(){
//        Date date = new Date(getDateOfArrivalAsLong());
//        DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        //timeFormatter.setTimeZone(TimeZone.getTimeZone("Europe/Moscow"));
        return "Date of arrival:          " + dateFormatter.format(getDateOfArrival()) + "\n" +
               "Deviated date of arrival: " + dateFormatter.format(getScheduleDeviation()) + "\n" +
               "Time of arrival:          " + timeFormatter.format(getDateOfArrival()) + "\n" +
               "Vessel name:              " + getVesselName() + "\n" +
               "Status:                   " + getStatus() + "\n" +
               "Type of cargo:            " + getType() + "\n" +
               "Weight of cargo:          " + getWeight() + " kg" + "\n" +
               "Planned days to unload:   " + getWillStayFor() + "\n" +
               "Actual days to unload:    " + getDaysWithDelay();

    }
}
