package com.baddragon.Port.Cranes;

public class LiquidCrane extends Crane{

    private static long lastId;
    private long id;

    public LiquidCrane() {
        super();
        lastId++;
        id = lastId;
    }

    public String takenBy() {
        if (takenBy != null)
            return "Liquid Crane #" + id + " taken by " +
                    takenBy.getVesselName() + " " + takenBy.getType();

        return "Liquid Crane #" + id + " taken by none";
    }

}
