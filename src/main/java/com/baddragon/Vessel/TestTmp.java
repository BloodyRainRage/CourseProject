package com.baddragon.Vessel;

import com.baddragon.Exceptions.UnknownTypeOfCargoException;
import com.baddragon.Schedule.Entry;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TestTmp {

    Random random = new Random();

    int cargo, liquid, bulk;

    public Entry randomEntry() throws UnknownTypeOfCargoException {
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

    public Entry buildContainer(){
        cargo++;
        return Entry.builder()
                .type(TypeOfCargo.CONTAINER)
                .vesselName("Cargovessel" + this.cargo)
                .build();

    }

    public Entry buildLiquid(){
        liquid++;
        return Entry.builder()
                .type(TypeOfCargo.LIQUID)
                .vesselName("Liquidvessel" + this.liquid)
                .build();
    }

    public Entry buildBulk(){
        bulk++;
        return Entry.builder()
                .type(TypeOfCargo.BULK)
                .vesselName("Bulkvessel" + this.bulk)
                .build();
    }

}
