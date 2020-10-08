package com.baddragon;

import com.baddragon.Exceptions.UnknownTypeOfCargoException;
import com.baddragon.Port.Port;
import com.baddragon.Schedule.Entry;
import com.baddragon.Vessel.TestTmp;
import com.baddragon.Vessel.TypeOfCargo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Main {

    public static void main(String[] args) {

        List<Entry> schedule = new LinkedList<Entry>();
        TestTmp test = new TestTmp();
        for (int i = 0; i < 20; i++){
            try {
                schedule.add(test.randomEntry());
            } catch (UnknownTypeOfCargoException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Random entry");
        for (Entry entry: schedule) {
            System.out.println(entry);
        }
        System.out.println("------------");

        for (Entry entry: schedule) {
            Port.getInstance().proceedWithUnloading(entry);
        }

    }

//    public Entry randomEntry(){
//        List<TypeOfCargo> types = new ArrayList<TypeOfCargo>();
//        types.add(TypeOfCargo.BULK);
//        types.add(TypeOfCargo.CONTAINER);
//        types.add(TypeOfCargo.LIQUID);
//
//        Random random = new Random();
//
//        switch (types.get(random.nextInt(types.size()))){
//            case BULK: return buildBulk();
//            case CONTAINER: return buildContainer();
//            case LIQUID: return buildLiquid();
//            default:
//                try {
//                    throw new Exception("Unknown");
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//        }
//
//    }
//
//    public Entry buildContainer(){
//        cargo++;
//        return Entry.builder()
//                    .type(TypeOfCargo.CONTAINER)
//                    .vesselName("Cargovessel" + this.cargo)
//                    .build();
//
//    }
//
//    public Entry buildLiquid(){
//        liquid++;
//        return Entry.builder()
//                .type(TypeOfCargo.CONTAINER)
//                .vesselName("Liquidvessel" + this.liquid)
//                .build();
//    }
//
//    public Entry buildBulk(){
//        bulk++;
//        return Entry.builder()
//                .type(TypeOfCargo.CONTAINER)
//                .vesselName("Bulkvessel" + this.bulk)
//                .build();
//    }

}
