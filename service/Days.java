package service;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Days {
    // Class responsible for displaying time remaining until order is complete

    private int dayNumber;
    private String day;
    private LocalTime openFrom;
    private LocalTime openTill;

    public Days(int dayNumber, String day, LocalTime openFrom, LocalTime openTill) {
        this.dayNumber = dayNumber;
        this.day = day;
        this.openFrom = openFrom;
        this.openTill = openTill;
    }

    public static void displayLocalTime(){
        Calendar c = Calendar.getInstance();
        System.out.println("The Current LocalTime is:" + c.getTime());
    }

    private static Days createDay(String[] metadata) {
        int dayNumber = Integer.parseInt(metadata[0]);
        String name = metadata[1];
        LocalTime openFrom = LocalTime.parse(metadata[2]);
        LocalTime openTill = LocalTime.parse(metadata[3]);

        return new Days(dayNumber, name, openFrom, openTill);
    }

    public static List<Days> loadDays(){
        List<Days> openingsTimes = new LinkedList<Days>();
        String fileName= "database/PhotoShop_OpeningHours.csv";
        File file= new File(fileName);

        // this gives you a 2-dimensional array of strings
        Scanner inputStream;

        try{
            inputStream = new Scanner(file);
            String header = inputStream.nextLine();

            while(inputStream.hasNext()){
                String line = inputStream.nextLine();
                String[] values = line.split(";");
                // this adds the currently parsed line to the 2-dimensional string array
                Days day = createDay(values);
                openingsTimes.add(day);
            }

            inputStream.close();
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return openingsTimes;
    }

    public static void displayOpeningTimes(List<Days> days){
        for(Days current: days){
            System.out.println(current.toString());
        }
    }

    public int getDayNumber() {
        return this.dayNumber;
    }

    public void setDayNumber(int dayNumber) {
        this.dayNumber = dayNumber;
    }

    public String getDay() {
        return this.day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public LocalTime getOpenFrom() {
        return this.openFrom;
    }

    public void setOpenFrom(LocalTime openFrom) {
        this.openFrom = openFrom;
    }

    public LocalTime getOpenTill() {
        return this.openTill;
    }

    public void setOpenTill(LocalTime openTill) {
        this.openTill = openTill;
    }

}
