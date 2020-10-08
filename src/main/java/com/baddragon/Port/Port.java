package com.baddragon.Port;

import com.baddragon.Port.Cranes.*;
import com.baddragon.Schedule.Entry;
import com.baddragon.Vessel.TypeOfCargo;

public class Port {

    private static Port instance;

    private Crane[] containerCranes = new ContainerCrane[3];
    private Crane[] liquidCranes = new LiquidCrane[4];
    private Crane[] bulkCranes = new BulkCrane[5];

    private Port() {

        for (int i = 0; i < containerCranes.length; i++) {
            containerCranes[i] = new ContainerCrane();
        }

        for (int i = 0; i < liquidCranes.length; i++) {
            liquidCranes[i] = new LiquidCrane();
        }

        for (int i = 0; i < bulkCranes.length; i++) {
            bulkCranes[i] = new BulkCrane();
        }
    }

    public static Port getInstance() {
        if (instance == null) {
            instance = new Port();
        }
        return instance;
    }

    public void proceedWithUnloading(Entry entry) {
        String noAvailableCranesMessage = "no available cranes for " + entry.getVesselName() +
                                            " with type " + entry.getType();
        if (entry.getType() == TypeOfCargo.BULK) {
            if (takeBulkCrane(entry) == -1) System.out.println(noAvailableCranesMessage);
        } else if (entry.getType() == TypeOfCargo.CONTAINER) {
            if (takeContainerCrane(entry) == -1) System.out.println(noAvailableCranesMessage);
        } else if (entry.getType() == TypeOfCargo.LIQUID) {
            if (takeLiquidCrane(entry) == -1) System.out.println(noAvailableCranesMessage);
        }

    }

    protected int takeContainerCrane(Entry entry) {
        for (Crane crane : containerCranes) {
            if (!crane.isTaken()) {
                crane.setTaken(entry);
                System.out.println(crane.takenBy());
                return 0;
            }
        }

        return -1;
    }

    protected int takeLiquidCrane(Entry entry) {
        for (Crane crane : liquidCranes) {
            if (!crane.isTaken()) {
                crane.setTaken(entry);
                System.out.println(crane.takenBy());
                return 0;
            }
        }

        return -1;
    }

    protected int takeBulkCrane(Entry entry) {
        for (Crane crane : bulkCranes) {
            if (!crane.isTaken()) {
                crane.setTaken(entry);
                System.out.println(crane.takenBy());
                return 0;
            }
        }

        return -1;
    }


}
