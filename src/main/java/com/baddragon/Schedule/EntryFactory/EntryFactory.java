package com.baddragon.Schedule.EntryFactory;

import com.baddragon.Exceptions.UnknownTypeOfCargoException;
import com.baddragon.Schedule.Entry;

public interface EntryFactory {

    public Entry createEntry() throws UnknownTypeOfCargoException;

}
