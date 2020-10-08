package com.baddragon.Port.Cranes;

import com.baddragon.Schedule.Entry;

public abstract class Crane {

    protected boolean taken = false;
    protected Entry takenBy;
    protected Long id;
    public boolean isTaken(){

        return taken;

    }
    public void setTaken(Entry entry){
        taken = true;
        takenBy = entry;
    }

    public void setFree(){
        taken = false;
        takenBy = null;
    }

    public abstract String takenBy();

}
