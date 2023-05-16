package service;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import static java.time.temporal.ChronoUnit.MINUTES;

// Class responsible for displaying time remaining until order is complete
public class Days {
    private int dayNumber;
    private String day;
    private LocalTime openFrom;
    private LocalTime openTill;
    private Long workingMinutes;

    public Days(int dayNumber, String day, LocalTime openFrom, LocalTime openTill) {
        this.dayNumber = dayNumber;
        this.day = day;
        this.openFrom = openFrom;
        this.openTill = openTill;
        this.workingMinutes = MINUTES.between(openFrom, openTill);
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

    public Long getWorkingMinutes() {
        return this.workingMinutes;
    }

    public void setWorkingMinutes(Long workingMinutes) {
        this.workingMinutes = workingMinutes;
    }
    
    // Create a day object, used for loading in the csv
    private static Days createDay(String[] metadata) {
        int dayNumber = Integer.parseInt(metadata[0]);
        String name = metadata[1];
        LocalTime openFrom = LocalTime.parse(metadata[2]);
        LocalTime openTill = LocalTime.parse(metadata[3]);

        return new Days(dayNumber, name, openFrom, openTill);
    }

    // Convert a localdate object to string format
    public static String dateToString(LocalDate date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String formattedString = date.format(formatter);
        return formattedString;
    }

    public static String getDateToday(){
        return LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
    }

    // Function for loading the opening hours csv
    public static List<Days> loadDays(){
        List<Days> openingsTimes = new LinkedList<Days>();
        String fileName= "database/PhotoShop_OpeningHours.csv";
        File file= new File(fileName);

        // this gives you a 2-dimensional array of strings
        Scanner inputStream;

        try{
            inputStream = new Scanner(file);
            // Skip the header 
            inputStream.nextLine();

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

    // Prints out the opening time stored in the days
    public void displayOpeningTimes(List<Days> days){
        System.out.println(String.format("%-30s %15s %15s" , "Day", "Open From", "Open Till"));
        for(Days current: days){
            System.out.println(current.toString());
        }
    }

    // Return the day of the week as an int
    public static int calculateDayOfWeek(){
        Calendar c = Calendar.getInstance();
        c.setTime(c.getTime());
        return c.get(Calendar.DAY_OF_WEEK);
    }

    // Function to iterate over day - can be in Days class?
    public static int calculatePickup(Long totalWorkDuration, List<Days> openingTimes){
        LocalTime timeNow = LocalTime.now();
        long workDuration = totalWorkDuration;
        int currentDay = Days.calculateDayOfWeek() - 1;
        int daysTaken = 0;

        // get time left in the day until closing hour
        long timeLeft = timeNow.until(openingTimes.get(currentDay).getOpenTill(), MINUTES);
        
        // subtract time left of today from total working time, go to next day
        workDuration = workDuration - timeLeft;
        daysTaken ++;
        // interation of weekday; could potentially be a function, future refactoring
        if(currentDay < 7){
            currentDay++;
        } else {
            currentDay = 1;
        }

        if (currentDay != 1){
            while(workDuration > 0 && currentDay != 1){
                if (currentDay == 7){
                    if(workDuration - openingTimes.get(6).getWorkingMinutes() < 0){
                        return daysTaken;
                    }
                    workDuration -= openingTimes.get(6).getWorkingMinutes();
                    daysTaken++;
                    currentDay = 1;
                }
                // Start now and cycle through days of the week csv
                for(int i = currentDay; i < openingTimes.size(); i++){
                    if(workDuration - openingTimes.get(i).getWorkingMinutes() < 0){
                        return daysTaken;
                    }
                    workDuration -= openingTimes.get(i).getWorkingMinutes();
                    if(currentDay < 7){
                        currentDay++;
                    } else {
                        currentDay = 1;
                    }
                    daysTaken++;
                }
            }
        }

        while(workDuration > 0){
            // Start now and cycle through days of the week csv
            for(Days day : openingTimes){
                if(workDuration - day.getWorkingMinutes() < 0){
                    return daysTaken;
                }
                workDuration -= day.getWorkingMinutes();
                daysTaken++;
            }
        }
        return daysTaken;
    }
    
    public String toString(){
        return String.format("%-30s %15s %15s" , this.day, this.openFrom, this.openTill );
    }

}
