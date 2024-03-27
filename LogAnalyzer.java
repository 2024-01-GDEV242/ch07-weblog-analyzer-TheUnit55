/**
 * Read web server data and analyse hourly access patterns.
 * 
 * @author Juan Jimenez
 * @version 2024-03-25
 */
public class LogAnalyzer
{
    // Where to calculate the hourly access counts.
    private int[] hourCounts;
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
        while(reader.hasNext()) {
            LogEntry entry = reader.next();
            int hour = entry.getHour();
            hourCounts[hour]++;
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
}
