import java.text.*;
import java.util.*;

/**
* TimeTools is a static class that provides static methods 
* that help deal with Unix time and conversions.  
*
* @author: Collin Holmquist
* @version: 1.0
* @since: 12/7/20
*/

public class TimeTools {

    public static String getDate(long unix_time){


        Date date = new Date(unix_time * 1000L);
        SimpleDateFormat temp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String java_date = temp.format(date);

        return java_date;
    }

    /**
    * getCurrentUnixTime() returns the current time in Unix time  
    * @return long the current unix time 
    */
    public static long getCurrentUnixTime() {

        return System.currentTimeMillis() / 1000L;
        
    }

    /**
    * calculateTimeSpan determines the time span between two unix time stamps. 
    * @param time1 
    * @param time2
    * @return String The difference in time, notated in dd:HH:mm:ss
    */
    public static String calculateTimeSpan(long time1, long time2){

        /*
        TODO: calculateTimeSpan should accept two time values in unix time
        and return a String representation of the time difference in the
        dd:HH:mm:ss.  Example:  06d:12h:34m:04s
        */


        return ""; 
    }
}