package com.baddragon;

import com.baddragon.Exceptions.UnknownTypeOfCargoException;
import com.baddragon.Logger.Logger;
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
            int cont = Port.getInstance().getContainerCapacity();
            int liq  = Port.getInstance().getLiquidCapacity();
            int bulk = Port.getInstance().getBulkCapacity();
            if(penalties.get(TypeOfCargo.LIQUID) != 0) {
                liq++;
            }
            if(penalties.get(TypeOfCargo.BULK) != 0) {
                bulk++;
            }
            if(penalties.get(TypeOfCargo.CONTAINER) != 0) {
                cont++;
            }
            Port.reinstantiate(cont, liq, bulk);
            Port.getInstance().setSchedule(schedule);
            Port.getInstance().start();
            newPenalty = Port.getInstance().getPenalty();

        }


    }

}
