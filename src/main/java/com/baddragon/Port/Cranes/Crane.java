package com.baddragon.Port.Cranes;

import com.baddragon.Schedule.Entry;

public abstract class Crane {

    protected boolean taken = false;
    protected Entry takenBy;
    protected Long id;

    protected int daysPassed = 0;

    public boolean isTaken() {

        return taken;

    }

    public void setTaken(Entry entry) {
        if(entry.getDaysPassed() != 0)
            this.daysPassed = entry.getDaysPassed();
        taken = true;
        takenBy = entry;
    }

    public void setFree() {
        taken = false;
        takenBy = null;
        daysPassed = 0;
    }

    public void completeDay(int incrementer) {
        if (this.takenBy == null) throw new NullPointerException();
        daysPassed += incrementer;
    }

    public Entry getEntry() {
        return takenBy;
    }

    public Long getId() {
        return id;
    }

    public int getDaysPassed() {
        return daysPassed;
    }

    public abstract String takenBy();

}
