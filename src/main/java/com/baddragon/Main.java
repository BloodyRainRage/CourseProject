package com.baddragon;

import com.baddragon.Exceptions.UnknownTypeOfCargoException;
import com.baddragon.Port.Port;
import com.baddragon.Schedule.Entry;
import com.baddragon.Schedule.EntryFactory.RandomEntryFactory;
import com.baddragon.Vessel.TypeOfCargo;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        List<Entry> schedule = new LinkedList<>();
        RandomEntryFactory randomEntry = new RandomEntryFactory();

        for (int i = 0; i < 20; i++) {
            try {
                schedule.add(randomEntry.createEntry());
            } catch (UnknownTypeOfCargoException e) {
                e.printStackTrace();
            }
        }

        //Port.setCapacity(5, 5,5 );

//        System.out.println(new Date(Port_INACTIVE.getInstance().getStartDay()) + "\n" +
//                           new Date(Port_INACTIVE.getInstance().getToday()) + "\n" +
//                           new Date(Port_INACTIVE.getInstance().getEndDay()) + "\n" );

        Collections.sort(schedule);
        System.out.println("Random entry");
        for (Entry entry : schedule) {
            System.out.println(entry.toString());
            System.out.println("--------------");
        }
        System.out.println("------------");

        Port.getInstance().setSchedule(schedule);
        Port.getInstance().start();
        int newPenalty = -1;
        int lastPenalty = Port.getInstance().getPenalty();
        while (newPenalty != lastPenalty){
            lastPenalty = newPenalty;
            Map<TypeOfCargo, Integer> penalties = Port.getInstance().getPenaltyMap();
            if(penalties.get(TypeOfCargo.LIQUID) != 0) {
                Port.getInstance().setLiquidCapacity(Port.getInstance().getLiquidCapacity() + 1);
            }
            if(penalties.get(TypeOfCargo.BULK) != 0) {
                Port.getInstance().setBulkCapacity(Port.getInstance().getBulkCapacity() + 1);
            }
            if(penalties.get(TypeOfCargo.CONTAINER) != 0) {
                Port.getInstance().setContainerCapacity(Port.getInstance().getContainerCapacity() + 1);
            }

            Port.getInstance().setSchedule(schedule);
            Port.getInstance().start();
            newPenalty = Port.getInstance().getPenalty();
        }


//        System.out.println(Port_INACTIVE.getInstance().getCargoQueue());

//        Long startDay = new Date().getTime();
//        System.out.println(new Date(startDay));

    }

}
