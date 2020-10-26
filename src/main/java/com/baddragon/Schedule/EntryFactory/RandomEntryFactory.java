package com.baddragon.Schedule.EntryFactory;

import com.baddragon.Exceptions.UnknownTypeOfCargoException;
import com.baddragon.Port.Port;
import com.baddragon.Port.Port_INACTIVE;
import com.baddragon.Schedule.Entry;
import com.baddragon.Schedule.Status;
import com.baddragon.Vessel.TypeOfCargo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class RandomEntryFactory implements EntryFactory {

    protected Random random = new Random();

    protected int cargo, liquid, bulk;

    public Entry createEntry() throws UnknownTypeOfCargoException {
        List<TypeOfCargo> types = new ArrayList<>();
        types.add(TypeOfCargo.BULK);
        types.add(TypeOfCargo.CONTAINER);
        types.add(TypeOfCargo.LIQUID);

        Entry entry;

        switch (types.get(random.nextInt(types.size()))) {
            case BULK:
                entry = buildBulk();
                break;
            case CONTAINER:
                entry = buildContainer();
                break;
            case LIQUID:
                entry = buildLiquid();
                break;
            default:
                throw new UnknownTypeOfCargoException("Unknown type of cargo");
        }

        return deviation(entry);
    }

    private Entry buildContainer() {
        cargo++;
        Long willStay = getRandomUnloadTime();
        willStay = willStay + (long) (willStay * 0.1);
        return Entry.builder()
                .type(TypeOfCargo.CONTAINER)
                .vesselName("Cargovessel" + this.cargo)
                .dateOfArrivalAsLong(getRandomDateInRange())
                .weight(getRandomWeight())
                .willStayFor(willStay)
                .status(Status.IN_TRANSIT)
                .build();

    }

    private Entry buildLiquid() {
        liquid++;
        Long willStay = getRandomUnloadTime();
        willStay = willStay + (long) (willStay * 0.3);
        return Entry.builder()
                .type(TypeOfCargo.LIQUID)
                .vesselName("Liquidvessel" + this.liquid)
                .dateOfArrivalAsLong(getRandomDateInRange())
                .weight(getRandomWeight())
                .willStayFor(willStay)
                .status(Status.IN_TRANSIT)
                .build();
    }

    private Entry buildBulk() {
        bulk++;
        Long willStay = getRandomUnloadTime();
        willStay = willStay + (long) (willStay * 0.2);
        return Entry.builder()
                .type(TypeOfCargo.BULK)
                .vesselName("Bulkvessel" + this.bulk)
                .dateOfArrivalAsLong(getRandomDateInRange())
                .weight(getRandomWeight())
                .willStayFor(willStay)
                .status(Status.IN_TRANSIT)
                .build();
    }

    //will arrive earlier or later (in range -7 to 7 days)
    private Entry deviation(Entry entry) {

        long deviation = random.longs(-7, 7)
                                    .findFirst().getAsLong();

        //modulating arrival
        entry.setScheduleDeviation(entry.getDateOfArrivalAsLong() + TimeUnit.DAYS.toMillis(deviation));

        //for how long will be delayed unload (0 to 10 days)
        deviation = random.longs(0, 10).findFirst().getAsLong();
        entry.setDaysWithDelay(entry.getWillStayFor() + deviation);

        return entry;
    }

    private Long getRandomDateInRange() {
        return random.longs(Port.getInstance().getStartDay(),
                Port.getInstance().getEndDay())
                .findFirst()
                .getAsLong();
    }


    //sets a random weight
    private Double getRandomWeight() {
        return random.longs(10000, 50000)
                .findFirst().getAsLong() * 0.1;
    }

    private Long getRandomUnloadTime() {

        return random.longs(11, 16)
                .findFirst().getAsLong();
    }

}
