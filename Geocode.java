/**
*GeoCode provides an interface to the OpenStreetMaps and Nominatim API 
*and can be used to resolve a street address or place name 
*to a coordinate location on the Earth's surface.
* @author: Collin Holmquist (via Dr. Brian Dorn)
* @version: 1.0
*/

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List; 

import java.net.URL; 
import java.net.URLEncoder;

import org.json.JSONArray;
import org.json.JSONObject;

public class Geocode{

    //latitude and longitude that are returned from address request
    private double latitude; 
    private double longitude;
    //formatted address that is returned from address search
    private String formattedAddy;    

    public Geocode(String address) {

        formattedAddy = null; 

        try
        {
            address = URLEncoder.encode(address, "UTF-8");
            String apiUrl = "https://nominatim.openstreetmap.org/search.php?q=" + address +"&polygon_geojson=1&format=jsonv2";

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl))
                    .GET() // GET is default
                    .header("accept","application/json")
                    .build();

            HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());
            //save response as a string.  
            String temp_json = response.body(); 

            //parse response into JSON if the response isn't empty. 
            if(!temp_json.equals("[ ]")){
                String json = temp_json.substring(1, temp_json.length() - 1); 
                JSONObject obj = new JSONObject(json);

                latitude = Double.parseDouble(obj.getString("lat"));
                longitude = Double.parseDouble(obj.getString("lon"));
                formattedAddy = obj.getString("display_name");
            }
            else {
                //something was incorrect and there was no address returned.  
                formattedAddy = null; 
            }
        }
        catch(Exception e) {
            System.out.println("An I/O error occured while accessing information.  Please try again.");
            formattedAddy = null; 
        }
    }

    

    public String getAddress() {
        /**
        getAddress retrieves a normalized address corresponding to the location given via the constructor. 
        @return the resolved and normalized address.  
        */
        return formattedAddy; 

    }

    public double getDistanceFrom(double otherLat, double otherLong){
        /**
        Computes the distance between this object's location and a specified latitude and longitude. 
        @param latitude and longitude of the other location
        @return the distance between the position in miles. 
        */
        double phi1 = Math.toRadians(this.latitude); 
        double phi2 = Math.toRadians(otherLat); 

        double dPhi = Math.toRadians(otherLat - this.latitude);
        double dLambda = Math.toRadians(otherLong - this.longitude); 

        double a = Math.pow(Math.sin(dPhi / 2), 2) + Math.cos(phi1) * Math.cos(phi2) * Math.pow(Math.sin(dLambda / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        double d = 3956 * c;

        return d; 
    }

    public double getLatitude() {
        /**
        Obtains the latitude that OpenStreetMaps finds for the address given to the Geocode constructor. 
        @return the latitude
        */
        return latitude; 
    }

    public double getLongitude() {
        /**
        Obtains the longitude that OpenStreetMaps finds for the address given to the Geocode constructor. 
        @return the longitude
        */
        return longitude; 
    }

    public boolean isValid() {
        /**
        Provides an indication of whether this instance contains valid data or not.  That is, it indicates whether the data was successfuly retrieved for the given location.
        @return true if valid data is available, false otherwise
        */
        return formattedAddy != null; 
    }

}
