package br.com.tracker;

import android.os.AsyncTask;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by danilo.nascimento on 02/02/2016.
 */
public class UserLocations {

    public ArrayList<Location> getLocals() {
        return locals;
    }

    private ArrayList<Location> locals;

    private UserLocations() {
    }

    public UserLocations(int userId) {
        locals = BuildLocals(userId);
    }

    private ArrayList<Location> BuildLocals(int userId) {

        try {

            AsyncTask task = new LocationRequestTask().execute(userId);

            Object object = task.get();

            return (ArrayList<Location>) object;
        } catch (Exception ex) {
            return null;
        }
    }


    private class LocationRequestTask extends AsyncTask {

        private String sendGet(int userId) throws Exception {
            String url = "http://52.25.64.161/tracker/api/location/" + userId;

            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            // optional default is GET
            con.setRequestMethod("GET");

            //add request header
            con.setRequestProperty("User-Agent", "Mozilla/5.0");

            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'GET' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            //print result
            return response.toString();
        }

        @Override
        protected Object doInBackground(Object[] params) {
            String json = null;

            try {
                json = sendGet(Integer.parseInt(params[0].toString()));


                GsonBuilder builder = new GsonBuilder();

                builder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");

                Type token = new TypeToken<ArrayList<Location>>() {
                }.getType();

                ArrayList<Location> array = builder.create().fromJson(json, token);

                return array;
            } catch (Exception e) {
                return null;
            }
        }
    }
}
