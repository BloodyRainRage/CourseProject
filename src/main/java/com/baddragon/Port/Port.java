package com.baddragon.Port;

import com.baddragon.Port.Cranes.BulkCrane;
import com.baddragon.Port.Cranes.ContainerCrane;
import com.baddragon.Port.Cranes.Crane;
import com.baddragon.Port.Cranes.LiquidCrane;
import com.baddragon.Schedule.Entry;
import com.baddragon.Vessel.TypeOfCargo;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class Port {

    private static Port instance;

    private static int containerCapacity, liquidCapacity, bulkCapacity;

//    private Crane[] containerCranes = new ContainerCrane[3];
//    private Crane[] liquidCranes = new LiquidCrane[4];
//    private Crane[] bulkCranes = new BulkCrane[5];

    private Map<TypeOfCargo, List<Crane>> cranes = new HashMap<>();
    private Map<TypeOfCargo, Set<Entry>> queue = new HashMap<>();

//    private List<Entry> liquidCargoQueue = new LinkedList<>();
//    private List<Entry> bulkCargoQueue = new LinkedList<>();
//    private List<Entry> containerCargoQueue = new LinkedList<>();

    private Long startDay;
    private Long endDay; //30 days after start

    private Port() {
        cranes.put(TypeOfCargo.LIQUID, new LinkedList<>());
        cranes.put(TypeOfCargo.BULK, new LinkedList<>());
        cranes.put(TypeOfCargo.CONTAINER, new LinkedList<>());

        queue.put(TypeOfCargo.LIQUID, new HashSet<>());
        queue.put(TypeOfCargo.BULK, new HashSet<>());
        queue.put(TypeOfCargo.CONTAINER, new HashSet<>());

        if (containerCapacity == 0 || liquidCapacity == 0 || bulkCapacity == 0) {
            containerCapacity = 3;
            liquidCapacity = 4;
            bulkCapacity = 5;
        }

        for (int i = 0; i < containerCapacity; i++) {
            cranes.get(TypeOfCargo.CONTAINER).add(new ContainerCrane());
            //containerCranes[i] = new ContainerCrane();
        }

        for (int i = 0; i < liquidCapacity; i++) {
            cranes.get(TypeOfCargo.LIQUID).add(new LiquidCrane());
            //liquidCranes[i] = new LiquidCrane();
        }

        for (int i = 0; i < bulkCapacity; i++) {
            cranes.get(TypeOfCargo.BULK).add(new BulkCrane());
            //bulkCranes[i] = new BulkCrane();
        }

        startDay = new Date().getTime();
        long aDay = TimeUnit.DAYS.toMillis(1);
        endDay = startDay + aDay * 30;
        //System.out.println(new Date(startDay) + "\n" + new Date(endDay));

    }

    public static Port getInstance() {
        if (instance == null) {
            instance = new Port();
        }
        return instance;
    }

    public static void setCapacity(int containerCapacity, int liquidCapacity, int bulkCapacity) {
        containerCapacity = containerCapacity;
        liquidCapacity = liquidCapacity;
        bulkCapacity = bulkCapacity;
    }

    public void proceedWithUnloading(Entry entry) {
        String noAvailableCranesMessage = "no available cranes for " + entry.getVesselName() +
                " with type " + entry.getType();
        if (takeCrane(entry) == -1) {
            System.out.println(noAvailableCranesMessage);
        } else moveToQueue(entry);
    }

    protected int takeCrane(Entry entry) {
        for (Crane crane : cranes.get(entry.getType())) {
            if (!crane.isTaken()) {
                crane.setTaken(entry);
                System.out.println(crane.takenBy());
                return 0;
            }
        }

        return -1;
    }

    protected void moveToQueue(Entry entry) {

        queue.get(entry.getType()).add(entry);

    }

//    protected int takeLiquidCrane(Entry entry) {
//        for (Crane crane : liquidCranes) {
//            if (!crane.isTaken()) {
//                crane.setTaken(entry);
//                System.out.println(crane.takenBy());
//                return 0;
//            }
//        }
//
//        return -1;
//    }
//
//    protected int takeBulkCrane(Entry entry) {
//        for (Crane crane : bulkCranes) {
//            if (!crane.isTaken()) {
//                crane.setTaken(entry);
//                System.out.println(crane.takenBy());
//                return 0;
//            }
//        }
//
//        return -1;
//    }

    public String getCargoQueue() {
        String str = "Container Cargo queue:\n";
        for (Entry entry : queue.get(TypeOfCargo.CONTAINER)) {
            str = str.concat(entry + "\n\n");
        }
        str = str.concat("\n+-----------------------+\n");
        str = str.concat("Luquid Cargo queue:\n");
        for (Entry entry : queue.get(TypeOfCargo.LIQUID)) {
            str = str.concat(entry + "\n\n");
        }
        str = str.concat("\n+-----------------------+\n");
        str = str.concat("Bulk Cargo queue:\n");
        for (Entry entry : queue.get(TypeOfCargo.BULK)) {
            str = str.concat(entry + "\n\n");
        }

        return str;
    }

    public Long getStartDay() {
        return startDay;
    }

    public Long getEndDay() {
        return endDay;
    }

}
