package co.edu.javeriana.bittus.fitt.Utilidades;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import co.edu.javeriana.bittus.fitt.Vista.ParquesActivity;

public class GetNearbyPlaces extends AsyncTask<Object,String,String> {

    Context context;

    public GetNearbyPlaces(Context context){
        this.context = context;
    }


    GoogleMap mMap;
    String url;
    InputStream is;
    BufferedReader bufferedReader;
    StringBuilder stringBuilder;
    String data;

    @Override
    protected String doInBackground(Object... objects) {
        mMap = (GoogleMap) objects[0];
        url = (String) objects[1];
        Log.d("Prubea", "url"+url );

        try {
            URL myurl = new URL(url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) myurl.openConnection();
            httpURLConnection.connect();
            is = httpURLConnection.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(is));

            String line="";
            stringBuilder = new StringBuilder();
            while ((line = bufferedReader.readLine())!=null)
            {
                stringBuilder.append(line);
            }

            data = stringBuilder.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }

        return data;
    }

    @Override
    protected void onPostExecute (String s){

        try {
            JSONObject parentObject = new JSONObject(s);
            JSONArray resultArray = parentObject.getJSONArray("results");

            if (resultArray.length()>0){

                for (int i= 0 ; i < resultArray.length(); i++) {
                    JSONObject jsonObject = resultArray.getJSONObject(i);
                    JSONObject locationObj = jsonObject.getJSONObject("geometry").getJSONObject("location");

                    String latitude = locationObj.getString("lat");
                    String longitude = locationObj.getString("lng");

                    JSONObject nameObject = resultArray.getJSONObject(i);
                    String name_park = nameObject.getString("name");
                    String vicininty = nameObject.getString("vicinity");
 
                    LatLng latLng = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));

                    mMap.addMarker(new MarkerOptions().position(latLng)
                        .snippet(vicininty)
                        .title(name_park)
                        .alpha(0.5f)); //Transparencia

                    Log.d("pruebas", "aiudaaa" + name_park);
                }

            }
            else{
                Toast.makeText(context,"Se supero el limite de consultas", Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
