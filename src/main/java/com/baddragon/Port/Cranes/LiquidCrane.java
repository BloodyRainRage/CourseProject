package com.baddragon.Port.Cranes;

public class LiquidCrane extends Crane{

    public static long lastId;
    private long id;

    public LiquidCrane() {
        super();
        lastId++;
        id = lastId;
    }

    public String takenBy() {
        if (takenBy != null)
            return "Liq  Crane \t#" + id + " taken by " +
                    takenBy.getVesselName() + " \t" + takenBy.getType();

        return "Liquid Crane #" + id + " taken by none";
    }

}
