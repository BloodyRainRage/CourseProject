package com.baddragon;

import com.baddragon.Exceptions.UnknownTypeOfCargoException;
import com.baddragon.Port.Port;
import com.baddragon.Schedule.Entry;
import com.baddragon.Schedule.EntryFactory.RandomEntryFactory;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        List<Entry> schedule = new LinkedList<>();
        RandomEntryFactory randomEntry = new RandomEntryFactory();
        for (int i = 0; i < 20; i++){
            try {
                schedule.add(randomEntry.createEntry());
            } catch (UnknownTypeOfCargoException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Random entry");
        for (Entry entry: schedule) {
            System.out.println(entry.toString());
            System.out.println("--------------");
        }
        System.out.println("------------");

        for (Entry entry: schedule) {
            Port.getInstance().proceedWithUnloading(entry);
        }

        System.out.println(Port.getInstance().getCargoQueue());

        Long startDay = new Date().getTime();
        System.out.println(new Date(startDay));

    }

}
