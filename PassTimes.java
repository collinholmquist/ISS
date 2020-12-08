
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.net.URL; 
import java.net.URLEncoder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.*;
import java.text.*;

/**
* PassTimes uses the Open-Notify API to get an array of times 
* when the ISS will be overhead a provided location.  
* Overhead is defined as 10 degrees above the horizon in any direction. 
*
* @author: Collin Holmquist
* @version: 1.0
* @since: 12-7-20
*/

public class PassTimes {

    int [] durations;
    long [] riseTimes;

    private String response;

    public PassTimes(double lat, double lon){

        //returns an array of when the ISS will be overhead for a lat/lon
        try{
             String apiURL = "http://api.open-notify.org/iss-pass.json?lat=" + lat + "&lon=" + lon;
            response = requestJSON(apiURL); 

        
        //parse the response into sections that students will use
            JSONObject obj = new JSONObject(response);
            JSONArray passTimes = (JSONArray) obj.get("response");

            durations = new int[passTimes.length()];
            riseTimes = new long[passTimes.length()];
            
            for(int i = 0; i < passTimes.length(); i++) {
                durations[i] = passTimes.getJSONObject(i).getInt("duration");
                riseTimes[i] = passTimes.getJSONObject(i).getLong("risetime"); 
            }
            

        } catch(JSONException e) {
            System.out.println("ERROR in JSON processing"); 
        }
    }

    /**
    * requestJSON creates a GET request to the Open-Notify API. 
    * 
    * @param apiURL The url of the api being sent the GET request 
    * @return String A string representation of the JSON response.  
    */
    public String requestJSON(String apiURL){

        try{

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiURL))
                    .GET() // GET is default
                    .header("accept","application/json")
                    .build();

            HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());
            //save response as a string.  
            String temp_json = response.body(); 
           
            return temp_json;

        }

        catch(Exception e){

            return "THERE WAS AN ERROR"; 

        }
    }

    /**
    * getDurations()
    * @return int[] the array containing the duration of each overhead pass in seconds
    */
    public int[] getDurations() {

        return durations;
    }

    /**
    * getRiseTimes()
    * @return long[] the array of rise times in Unix Time, when the ISS
    * will start to be overhead the location.  
    */
    public long[] getRiseTimes() {

        return riseTimes;
    }

    
}