
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
* ISS uses the Open-Notify API to get the current latitude 
* and longitude of the International Space Station.
*
* @author: Collin Holmquist
* @version: 1.0
* @since: 12-7-20
*/

public class ISS {

    private double currLat;
    private double currLon;
    private String response;

    public ISS(){

        //returns an array of when the ISS will be overhead for a lat/lon
        try{
             String apiURL = "http://api.open-notify.org/iss-now.json";
             response = requestJSON(apiURL); 

        
        //parse the response into sections that students will use
            JSONObject obj = new JSONObject(response);
            currLat = obj.getJSONObject("iss_position").getDouble("latitude");
            currLon = obj.getJSONObject("iss_position").getDouble("longitude");

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
    * getLatitude
    * @return double The current latitude of the ISS. 
    */
    public double getLatitude() {
        return currLat;
    }

    /**
    * getLongitude
    * @return double The current longitude of the ISS. 
    */
    public double getLongitude() {
        return currLon; 
    }

    
    
}