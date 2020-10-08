package com.baddragon.Port.Cranes;

public class BulkCrane extends Crane{

    private static long lastId;
    private long id;

    public BulkCrane() {
        super();
        lastId++;
        id = lastId;
    }

    public String takenBy() {
        if (takenBy != null)
            return "Bulk Crane #" + id + " taken by " +
                    takenBy.getVesselName() + " " + takenBy.getType();

        return "Bulk Crane #" + id + " taken by none";
    }

}
