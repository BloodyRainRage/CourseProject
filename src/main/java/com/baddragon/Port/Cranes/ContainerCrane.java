package com.baddragon.Port.Cranes;

public class ContainerCrane extends Crane {

    public static long lastId;
    private long id;

    public ContainerCrane() {
        super();
        lastId++;
        id = lastId;
    }

    public String takenBy() {
        if (takenBy != null)
            return "Cont Crane \t#" + id + " taken by " +
                    takenBy.getVesselName() + " \t" + takenBy.getType();

        return "Cont Crane #" + id + " taken by none";
    }

    public static void dropLastId(){
        lastId=0;
    }
}
