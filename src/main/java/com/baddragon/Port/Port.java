package com.baddragon.Port;

import com.baddragon.Logger.Logger;
import com.baddragon.Port.Cranes.BulkCrane;
import com.baddragon.Port.Cranes.ContainerCrane;
import com.baddragon.Port.Cranes.Crane;
import com.baddragon.Port.Cranes.LiquidCrane;
import com.baddragon.Schedule.Entry;
import com.baddragon.Vessel.TypeOfCargo;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class Port {

    private static Port instance = new Port(1, 1, 1);
    private int containerCapacity, liquidCapacity, bulkCapacity;
    private List<Crane> busyCranes = new LinkedList<>();


    private Map<TypeOfCargo, List<Crane>> cranes = new HashMap<>();
    private Map<TypeOfCargo, List<Entry>> queue = new HashMap<>();
    private List<Entry> schedule;

    static Logger logger = new Logger();

    private Long startDay;
    private Long endDay; //30 days after start
    private Long today;


    private int incrementer;
    private Map<TypeOfCargo, Integer> penaltyMap = new HashMap<>();
    int penalty = 0;
    private Map<TypeOfCargo, Integer> amountOfEachType = new HashMap<>();

    private Port(int containerCapacity,
                 int liquidCapacity,
                 int bulkCapacity) {

        ContainerCrane.lastId = 0;
        LiquidCrane.lastId = 0;
        BulkCrane.lastId = 0;


        this.containerCapacity = containerCapacity;
        this.liquidCapacity = liquidCapacity;
        this.bulkCapacity = bulkCapacity;

        cranes.put(TypeOfCargo.LIQUID, new LinkedList<>());
        cranes.put(TypeOfCargo.BULK, new LinkedList<>());
        cranes.put(TypeOfCargo.CONTAINER, new LinkedList<>());

        queue.put(TypeOfCargo.LIQUID, new LinkedList<>());
        queue.put(TypeOfCargo.BULK, new LinkedList<>());
        queue.put(TypeOfCargo.CONTAINER, new LinkedList<>());


        for (int i = 0; i < containerCapacity; i++) {
            cranes.get(TypeOfCargo.CONTAINER).add(new ContainerCrane());
        }

        for (int i = 0; i < liquidCapacity; i++) {
            cranes.get(TypeOfCargo.LIQUID).add(new LiquidCrane());
        }

        for (int i = 0; i < bulkCapacity; i++) {
            cranes.get(TypeOfCargo.BULK).add(new BulkCrane());
        }

        startDay = new Date().getTime();
        today = new Date().getTime();
        long aDay = TimeUnit.DAYS.toMillis(1);
        endDay = startDay + aDay * 30;

        incrementer = 1;

        penaltyMap.put(TypeOfCargo.LIQUID, 0);
        penaltyMap.put(TypeOfCargo.BULK, 0);
        penaltyMap.put(TypeOfCargo.CONTAINER, 0);
    }

    public static Port getInstance() {
        return instance;
    }

    public void setParameters(int incrementer, int liquidCapacity, int bulkCapacity, int containerCapacity) {
        this.incrementer = incrementer;
        setLiquidCapacity(liquidCapacity);
        setBulkCapacity(bulkCapacity);
        setContainerCapacity(containerCapacity);
    }

    public void start() {

        System.out.println("---START INFO---");
        System.out.println("CONT CAPACITY: " + getContainerCapacity());
        System.out.println("LIQ  CAPACITY: " + getLiquidCapacity());
        System.out.println("BULK CAPACITY: " + getBulkCapacity());
        System.out.println("---------------");
        penalty = 0;
        penaltyMap.put(TypeOfCargo.LIQUID, 0);
        penaltyMap.put(TypeOfCargo.BULK, 0);
        penaltyMap.put(TypeOfCargo.CONTAINER, 0);
        today = startDay;
        while (!schedule.isEmpty() || queueIsNotEmpty() || !busyCranes.isEmpty()) {
            processQueue();
            List<Integer> idToRemove = new LinkedList<>();
            for (int i = 0; i < schedule.size(); i++) {
                if (compareDates(schedule.get(i).getScheduleDeviation(), today)) {
                    int out = takeCrane(schedule.get(i));
                    if (out == -1) queue.get(schedule.get(i).getType())
                            .add(schedule.get(i));
                    if (out == 0 || out == -1) {
                        idToRemove.add(i);
                    }
                }
            }

            removeFromListAt(schedule, idToRemove);

            nextDay();
        }

//        while (queueIsNotEmpty()) {
//            processQueue();
//            nextDay();
//        }
//
//        while (!busyCranes.isEmpty()) {
//            nextDay();
//        }

        System.out.println(penalty);
        logger.writeStatistics(amountOfEachType, penaltyMap, cranes,
                new Date(startDay),
                new Date(today),
                new Date(endDay),
                incrementer);
        logger.writeCraneLog("\n----Next Record----\n");

        System.out.println("---END INFO---");
        System.out.println("CONT PENALTY: " + penaltyMap.get(TypeOfCargo.CONTAINER));
        System.out.println("LIQ  PENALTY: " + penaltyMap.get(TypeOfCargo.LIQUID));
        System.out.println("BULK PENALTY: " + penaltyMap.get(TypeOfCargo.BULK));
        System.out.println("--------------");
        System.out.println();
    }

    private void processQueue() {

//        Set<TypeOfCargo> listOfTypes = queue.keySet();


        if (!queue.get(TypeOfCargo.LIQUID).isEmpty()) {
            List<Integer> idToRemove = new LinkedList<>();
            for (int i = 0; i < queue.get(TypeOfCargo.LIQUID).size(); i++) {
                int out = takeCrane(queue.get(TypeOfCargo.LIQUID).get(i));
                if (out == 0) {
                    idToRemove.add(i);
                }
            }
            removeFromListAt(queue.get(TypeOfCargo.LIQUID), idToRemove);
        }

        if (!queue.get(TypeOfCargo.BULK).isEmpty()) {
            List<Integer> idToRemove = new LinkedList<>();
            for (int i = 0; i < queue.get(TypeOfCargo.BULK).size(); i++) {
                int out = takeCrane(queue.get(TypeOfCargo.BULK).get(i));
                if (out == 0) {
                    idToRemove.add(i);
                }
            }
            removeFromListAt(queue.get(TypeOfCargo.BULK), idToRemove);
        }

        if (!queue.get(TypeOfCargo.CONTAINER).isEmpty()) {
            List<Integer> idToRemove = new LinkedList<>();
            for (int i = 0; i < queue.get(TypeOfCargo.CONTAINER).size(); i++) {
                int out = takeCrane(queue.get(TypeOfCargo.CONTAINER).get(i));
                if (out == 0) {
                    idToRemove.add(i);
                }
            }
            removeFromListAt(queue.get(TypeOfCargo.CONTAINER), idToRemove);
        }

    }

    public void removeFromListAt(List listToRemoveFrom, List<Integer> listOfIds) {
        if (!listOfIds.isEmpty())
            for (Integer integer : listOfIds) {
                listToRemoveFrom.remove(integer.intValue());
                moveIndexes(integer, listOfIds);
            }
    }

    public void moveIndexes(Integer val, List<Integer> indexes) {
        for (int i = 0; i < indexes.size(); i++) {
            if (indexes.get(i) > val)
                indexes.set(i, indexes.get(i) - 1);
        }
    }

    private boolean queueIsNotEmpty() {

        return !queue.get(TypeOfCargo.LIQUID).isEmpty() ||
                !queue.get(TypeOfCargo.BULK).isEmpty() ||
                !queue.get(TypeOfCargo.CONTAINER).isEmpty();
    }

    private void nextDay() {
        List<Integer> idToRemove = new ArrayList<>();
        for (int i = 0; i < busyCranes.size(); ++i) {
            if (busyCranes.get(i).getEntry() == null) continue; //&!&!&!&
            busyCranes.get(i).completeDay(incrementer);
            if (busyCranes.get(i).getDaysPassed() > busyCranes.get(i).getEntry().getWillStayFor()) {
                this.penalty += 1000;

                int penalty = penaltyMap.get(busyCranes.get(i).getEntry().getType()) + 1000;
                penaltyMap.put(busyCranes.get(i).getEntry().getType(), penalty);

            }
            if (busyCranes.get(i).getDaysPassed() >= busyCranes.get(i).getEntry().getDaysWithDelay()) {
                idToRemove.add(i);
                Crane crane = busyCranes.get(i);
                logger.writeCraneLog("[REL]  " + crane.takenBy() + " was released on "
                        + Entry.dateFormatter.format(new Date(today)) + " days w/o delay " + crane.getEntry().getWillStayFor() +
                        " days with delay " + crane.getEntry().getDaysWithDelay() +
                        " days passed " + crane.getDaysPassed());
                busyCranes.get(i).setFree();
            }
        }

        for (TypeOfCargo type : Arrays.asList(TypeOfCargo.values())) {
            for (Entry entry : queue.get(type)) {
                entry.setDaysPassed(entry.getDaysPassed() + incrementer);
            }
        }

        removeFromListAt(busyCranes, idToRemove);

        today += TimeUnit.DAYS.toMillis(incrementer);
    }

    //returns true if date1 less of eq date2
    public boolean compareDates(Long date1, Long date2) {
        Date dt1 = new Date(date1);
        Date dt2 = new Date(date2);

        return !dt1.after(dt2);
    }

    protected int takeCrane(Entry entry) {

        if (!compareDates(entry.getScheduleDeviation(), today)) return -2;

        for (Crane crane : cranes.get(entry.getType())) {
            if (!crane.isTaken()) {
                crane.setTaken(entry);
                if (crane.getDaysPassed() != 0) {
                    if (crane.getDaysPassed() > entry.getWillStayFor()) {
                        long localPenalty = (crane.getDaysPassed() - entry.getWillStayFor()) * 1000;
                        this.penalty += localPenalty;

                        int penalty = penaltyMap.get(entry.getType()) + (int) localPenalty;
                        penaltyMap.put(entry.getType(), penalty);
                    }
                }
                System.out.println(crane.takenBy() + "\t on " + Entry.dateFormatter.format(new Date(today)));
                logger.writeCraneLog("[TAKE] " + crane.takenBy() + " on " + Entry.dateFormatter.format(new Date(today)));
                busyCranes.add(crane);
                return 0;
            }
        }

        return -1;
    }


    public void setBulkCapacity(int capacity) {
        BulkCrane.lastId = 0;
        cranes.put(TypeOfCargo.BULK, new LinkedList<>());
        for (int i = 0; i < capacity; i++) {
            cranes.get(TypeOfCargo.BULK).add(new BulkCrane());
        }
    }

    public void setContainerCapacity(int capacity) {
        ContainerCrane.lastId = 0;
        cranes.put(TypeOfCargo.CONTAINER, new LinkedList<>());
        for (int i = 0; i < capacity; i++) {
            cranes.get(TypeOfCargo.CONTAINER).add(new BulkCrane());
        }
    }

    public void setLiquidCapacity(int capacity) {
        LiquidCrane.lastId = 0;
        cranes.put(TypeOfCargo.LIQUID, new LinkedList<>());
        for (int i = 0; i < capacity; i++) {
            cranes.get(TypeOfCargo.LIQUID).add(new LiquidCrane());
        }
    }


    public Integer getPenalty() {
        return penaltyMap.get(TypeOfCargo.LIQUID) +
                penaltyMap.get(TypeOfCargo.CONTAINER) +
                penaltyMap.get(TypeOfCargo.BULK);
    }

    public void setSchedule(List<Entry> schedule) {
        this.schedule = new LinkedList<>();
        try {
            for (Entry entry : schedule) {
                this.schedule.add((Entry) entry.clone());
            }
        } catch (CloneNotSupportedException ex){
            ex.printStackTrace();
        }

        logger.writeSchedule(schedule);
        for (TypeOfCargo type : TypeOfCargo.values()) {
            amountOfEachType.put(type, 0);
        }
        for (Entry entry : schedule) {
            int val = amountOfEachType.get(entry.getType());
            amountOfEachType.put(entry.getType(), val + 1);
        }
    }


    public static void reinstantiate(int containerCapacity,
                                     int liquidCapacity,
                                     int bulkCapacity) {

        Port.instance = new Port(containerCapacity, liquidCapacity, bulkCapacity);

    }

    public Map<TypeOfCargo, Integer> getPenaltyMap() {
        return penaltyMap;
    }

    public Long getStartDay() {
        return startDay;
    }

    public void setStartDay(Long startDay) {
        this.startDay = startDay;
    }

    public Long getEndDay() {
        return endDay;
    }

    public void setEndDay(Long endDay) {
        this.endDay = endDay;
    }

    public Long getToday() {
        return today;
    }

    public void setToday(Long today) {
        this.today = today;
    }

    public void setIncrementer(int incrementer) {
        this.incrementer = incrementer;
    }

    public int getContainerCapacity() {
        return containerCapacity;
    }

    public int getLiquidCapacity() {
        return liquidCapacity;
    }

    public int getBulkCapacity() {
        return bulkCapacity;
    }
}
