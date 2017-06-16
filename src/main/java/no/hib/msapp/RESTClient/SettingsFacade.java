/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hib.msapp.RESTClient;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 *
 * @author Leif Arne
 */
public class SettingsFacade {
    private final String MAX_WEEKS = "http://ms-services.medicloud.eu-gb.mybluemix.net/api/settings/preperationStart";

    public SettingsFacade() {
    }

       public int getMaxWeeks() {
        int weeks = 0;
        try {
            URL obj = new URL(MAX_WEEKS);

            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            //add reuqest header
            con.setRequestMethod("GET");

            int responseCode = con.getResponseCode();

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream(),"UTF-8"));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            Type type = new TypeToken<Integer>() {}.getType();
            weeks = new Gson().fromJson(response.toString(), type);
        } catch (Exception ex) {
            throw new IllegalArgumentException("Exception getting all symptoms: ", ex);
        }


        return weeks;
    }
}
