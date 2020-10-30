package com.baddragon.Logger;

import com.baddragon.Port.Cranes.Crane;
import com.baddragon.Schedule.Entry;
import com.baddragon.Vessel.TypeOfCargo;

import java.io.*;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Logger {

    File craneLog = new File("logs\\cranesLog.txt");
    File scheduleLog = new File("logs\\schedule.txt");
    File statisticsLog = new File("logs\\statistics.txt");

    public Logger() {
        try {
            FileWriter craneFileWriter = new FileWriter(craneLog);
            FileWriter scheduleFileWriter = new FileWriter(scheduleLog);
            FileWriter statFileWriter = new FileWriter(statisticsLog);

            craneFileWriter.write("");
            scheduleFileWriter.write("");
            statFileWriter.write("");

            craneFileWriter.close();
            scheduleFileWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeCraneLog(String str) {
        try {
            FileWriter fileWriter = new FileWriter(craneLog, true);

            fileWriter.write(str + "\n");
            fileWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void writeSchedule(List<Entry> schedule) {

        try (PrintWriter printWriter = new PrintWriter(scheduleLog)) {
            for (Entry entry : schedule) {
                printWriter.write(entry.toString() + "\n\n");
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void writeStatistics(Map<TypeOfCargo, Integer> amountOfEach,
                                Map<TypeOfCargo, Integer> penalties,
                                Map<TypeOfCargo, List<Crane>> cranes,
                                Date startDate, Date today, Date endDate,
                                int incrementer) {

        try {
            FileWriter fileWriter = new FileWriter(statisticsLog, true);
            TypeOfCargo[] types = TypeOfCargo.values();
            fileWriter.write("Simulation statistics:\n");
            fileWriter.write("Simulation start date:        \t" + startDate + "\n");
            fileWriter.write("Simulation expected end date: \t" + endDate + "\n");
            fileWriter.write("Simulation end date:          \t" + today + "\n");
            fileWriter.write("Incrementer " + incrementer + "\n");
            fileWriter.write("Amount of vehicles of each type:\n");
            for (TypeOfCargo type : types) {
                fileWriter.write(type + " \t" + amountOfEach.get(type) + "\n");
            }

            fileWriter.write("Penalties:\n");
            int sum = 0;
            for (TypeOfCargo type : types) {
                fileWriter.write(type + " \t" + penalties.get(type) + "\n");
                sum += penalties.get(type);
            }
            fileWriter.write("Sum penalty " + sum + "\n");

            fileWriter.write("Amount of cranes:\n");
            for (TypeOfCargo type : types) {
                fileWriter.write(type + " \t" + cranes.get(type).size() + "\n");
            }

            fileWriter.write("----End of record----\n\n");


            fileWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
