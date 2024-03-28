/**
 * Analyzes web server log data to find access patterns across hourly, daily, and monthly
 * intervals. Uses a LogfileReader to process log files, calculates for busiest and 
 * quietest hours, days, months, total monthly accesses, and average monthly accesses.
 * 
 * @author Juan Jimenez
 * @version 2024-03-25
 */
public class LogAnalyzer
{
    // Where to calculate the hourly access counts.
    private int[] hourCounts;
    private int[] dayCounts;
    private int[] monthCounts;
    // Use a LogfileReader to access the data.
    private LogfileReader reader;

    /**
     * Create an object to analyze hourly web accesses.
     */
    public LogAnalyzer()
    { 
        // Create the array object to hold the hourly
        // access counts.
        hourCounts = new int[24];
        dayCounts = new int[28];
        monthCounts = new int[12];
        // Create the reader to obtain the data.
        reader = new LogfileReader("demo.log");
    }

    /**
     * Create an object to analyze hourly web accesses from a specific log file.
     * @param filename The name of the log file to analyze.
     */
    public LogAnalyzer(String filename)
    { 
        hourCounts = new int[24];
        reader = new LogfileReader(filename);
    }
    
    /**
     * Analyze the hourly access data from the log file.
     */
    public void analyzeHourlyData()
    {
        reader.reset();
        while(reader.hasNext()) {
            LogEntry entry = reader.next();
            int hour = entry.getHour();
            hourCounts[hour]++;
        }
    }

    /**
     * Analyze the daily access data from the log file
     */
    public void analyzeDailyData() {
        reader.reset();
        while (reader.hasNext()) {
            LogEntry entry = reader.next();
            if (entry != null) {
                int day = entry.getDay() - 1; 
                if (day >= 0 && day < dayCounts.length) {
                    dayCounts[day]++;
                }
            }
        }
    }
    
    /**
     * Analyze the monthly access data from the log file
     */
    public void analyzeMonthlyData() {
        reader.reset();
        while (reader.hasNext()) {
            LogEntry entry = reader.next();
            if (entry != null) {
            int month = entry.getMonth() - 1;
            if (month >= 0 && month < monthCounts.length) {
                monthCounts[month]++;
                }
            }
        }
    }

    /**
     * Print the hourly counts.
     * These should have been set with a prior
     * call to analyzeHourlyData.
     */
    public void printHourlyCounts()
    {
        System.out.println("Hr: Count");
        for(int hour = 0; hour < hourCounts.length; hour++) {
            System.out.println(hour + ": " + hourCounts[hour]);
        }
    }
    
    /**
     * Print the lines of data read by the LogfileReader
     */
    public void printData()
    {
        reader.printData();
    }
    
    /**
     * Calculates the total number of accesses recorded in the log file.
     * This method totals up the number of accesses for each hour stored in the 
     * hourCounts, which the data is filled by a call to the analyzeHourlyData method.
     *
     * @return The total number of accesses in the log file.
     */
    public int numberOfAccesses()
    {
        int total = 0;
        for(int hour = 0; hour < hourCounts.length; hour++) {
            total += hourCounts[hour];
        }
        return total;
    }
    
    /**
     * Finds the busiest hour in hourCounts array.
     * The hour with the highest number of accesses.
     * 
     * @return The index of the busiest hour
     */
    public int busiestHour()
    {
        int busiestHourIndex = 0;
        int allAccesses = hourCounts[0];

        for(int hour = 1; hour < hourCounts.length; hour++) {
            if(hourCounts[hour] > allAccesses) {
                busiestHourIndex = hour;
                allAccesses = hourCounts[hour];
            }
        }
        return busiestHourIndex;
    }
    
    /**
     * Finds the hour with the fewest number of accesses in the log file.
     * This method iterates through the hourCounts array to find the index with the 
     * lowest count, shows the quietest hour access.
     *
     * @return The index of the quietest hour
     */

    public int quietestHour()
    {
        int quietestHour = 0;
        int allAccesses = hourCounts[0];

        for(int hour = 1; hour < hourCounts.length; hour++) {
            if(hourCounts[hour] > hourCounts[quietestHour]) {
                quietestHour = hour;
            }
        }
        return quietestHour;
    }
    
    /**
     * Finds the start hour of the busiest two-hour period in the log file.
     * This method totals the number of accesses for each two-hour period
     * and finds which period has the highest total number of accesses.
     *
     * @return The start hour of the busiest two-hour period.
     */
    public int busiestTwoHour()
    {
        int busiestPeriod = 0;
        int busiestCount = 0;

        for(int hour = 0; hour < hourCounts.length - 1; hour++) 
        {
            int periodCount = hourCounts[hour] + hourCounts[hour+1];
            if(periodCount > busiestCount) 
            {
                busiestPeriod = hour;
                busiestCount = periodCount;
            }
        }
        return busiestPeriod;
    }
    
    /**
     * Finds the day with the least number of accesses in the log file.
     * This method iterates through the dayCounts array to find the day with the 
     * least amount of access count.
     * 
     * @return The index of the quietest day
     */
    public int quietestDay() {
        int quietestDay = 0;

        for(int day = 0; day < dayCounts.length; day++) {
            if(dayCounts[day] < dayCounts[quietestDay]) {
                quietestDay = day;
            }
        }
        return quietestDay;
    }

    /**
     * Finds the day with the highest number of accesses in the log file.
     * This method iterates through the dayCounts array to find the day with the 
     * most amount of access count.
     * 
     * @return The index of the busiest day
     */
    public int busiestDay() {
        int busiestDay = 0;

        for(int day = 0; day < dayCounts.length; day++) {
            if(dayCounts[day] > dayCounts[busiestDay]) {
                busiestDay = day;
            }
        }
        return busiestDay;
    }

    /**
     * Totals the total number of accesses for all months in the log file.
     * This method totals up the number of accesses for each month stored in the 
     * monthCounts array.
     * 
     * @return The total number of accesses for all months
     */
    public int totalAccessesPerMonth() {
        int totalAccesses = 0;

        for(int monthCount : monthCounts) {
            totalAccesses += monthCount;
        }
        return totalAccesses;
    }

    /**
     * Finds the month with the fewest number of accesses in the log file.
     * This method iterates through the monthCounts array to find the month with the 
     * least amount of access count.
     * 
     * @return The index of the quietest month
     */
    public int quietestMonth() {
        int quietestMonth = 0;

        for(int month = 0; month < monthCounts.length; month++) {
            if(monthCounts[month] < monthCounts[quietestMonth]) {
                quietestMonth = month;
            }
        }
        return quietestMonth;
    }

    /**
     * Finds the month with the highest number of accesses in the log file.
     * This method iterates through the monthCounts array to find the month with the 
     * highest access count.
     * 
     * @return The index of the busiest month
     */
    public int busiestMonth() {
        int busiestMonth = 0;

        for(int month = 0; month < monthCounts.length; month++) {
            if(monthCounts[month] > monthCounts[busiestMonth]) {
                busiestMonth = month;
            }
        }
        return busiestMonth;
    }

    /**
     * Totals the average number of accesses per month.
     * This method first totals all the accesses across months stored in the monthCounts 
     * array and then divides by the number of months to find the average.
     * 
     * @return The average number of accesses per month
     */
    public int averageAccessesPerMonth() {
        int totalAverage = 0;

        for(int monthCount : monthCounts) {
            totalAverage += monthCount;
        }
        int average = totalAverage / monthCounts.length;
        return average;
    }
}
