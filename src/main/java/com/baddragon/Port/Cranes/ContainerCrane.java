package com.baddragon.Port.Cranes;

public class ContainerCrane extends Crane {

    private static long lastId;
    private long id;

    public ContainerCrane() {
        super();
        lastId++;
        id = lastId;
    }

    public String takenBy() {
        if (takenBy != null)
            return "Cargo Crane #" + id + " taken by " +
                    takenBy.getVesselName() + " " + takenBy.getType();

        return "Cargo Crane #" + id + " taken by none";
    }
}