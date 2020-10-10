package com.baddragon.Schedule.EntryFactory;

import com.baddragon.Exceptions.UnknownTypeOfCargoException;
import com.baddragon.Port.Port;
import com.baddragon.Schedule.Entry;
import com.baddragon.Vessel.TypeOfCargo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomEntryFactory implements EntryFactory{

    protected Random random = new Random();

    protected int cargo, liquid, bulk;

    public Entry createEntry() throws UnknownTypeOfCargoException {
        List<TypeOfCargo> types = new ArrayList<>();
        types.add(TypeOfCargo.BULK);
        types.add(TypeOfCargo.CONTAINER);
        types.add(TypeOfCargo.LIQUID);


        switch (types.get(random.nextInt(types.size()))){
            case BULK: return buildBulk();
            case CONTAINER: return buildContainer();
            case LIQUID: return buildLiquid();
            default:
                throw new UnknownTypeOfCargoException("Unknown type of cargo");
        }
    }

    private Entry buildContainer(){
        cargo++;
        return Entry.builder()
                    .type(TypeOfCargo.CONTAINER)
                    .vesselName("Cargovessel" + this.cargo)
                    .dateOfArrivalAsLong(getRandomDateInRange())
                    .weight(getRandomWeight())
                    .willStayFor(getRandomUnloadTime())
                .build();

    }

    private Entry buildLiquid(){
        liquid++;
        return Entry.builder()
                    .type(TypeOfCargo.LIQUID)
                    .vesselName("Liquidvessel" + this.liquid)
                    .dateOfArrivalAsLong(getRandomDateInRange())
                    .weight(getRandomWeight())
                    .willStayFor(getRandomUnloadTime())
                .build();
    }

    private Entry buildBulk(){
        bulk++;
        return Entry.builder()
                    .type(TypeOfCargo.BULK)
                    .vesselName("Bulkvessel" + this.bulk)
                    .dateOfArrivalAsLong(getRandomDateInRange())
                    .weight(getRandomWeight())
                    .willStayFor(getRandomUnloadTime())
                .build();
    }

    private Long getRandomDateInRange(){
        return random.longs(Port.getInstance().getStartDay(),
                            Port.getInstance().getEndDay())
                                              .findFirst()
                                              .getAsLong();
    }

    private Double getRandomWeight(){
        return random.longs(10000, 50000)
                        .findFirst().getAsLong() * 0.1;
    }

    private Long getRandomUnloadTime(){

        return random.longs(15, 23)
                        .findFirst().getAsLong();
    }

}
