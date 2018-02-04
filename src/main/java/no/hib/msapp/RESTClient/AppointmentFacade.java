/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hib.msapp.RESTClient;

import com.google.gson.Gson;
import no.hib.msapp.entities.AppointmentPreperation;
import no.hib.msapp.entities.Patient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 *
 * @author Leif Arne
 */
public class AppointmentFacade {

    private final String ALL_SURVEYS = "http://msservices.eu-gb.mybluemix.net/api/preperation";

    public AppointmentFacade(Patient p) {
    }

    public AppointmentPreperation findSurvey(int id) {
        AppointmentPreperation preperation;
        try {
            URL obj = new URL(ALL_SURVEYS + "/" + id);

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

            preperation = new Gson().fromJson(response.toString(), AppointmentPreperation.class);
        } catch (Exception ex) {
            throw new IllegalArgumentException("Exception getting preperation with id: " + id, ex);
        }
        return preperation;
    }

    public AppointmentPreperation findNextSurvey() {
        AppointmentPreperation preperation;
        try {
            URL obj = new URL(ALL_SURVEYS + "/next");

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

            preperation = new Gson().fromJson(response.toString(), AppointmentPreperation.class);
        } catch (Exception ex) {
            throw new IllegalArgumentException("Exception getting preperation", ex);
        }
        return preperation;

    }

    public void saveSurvey(AppointmentPreperation survey) {
        try {
            URL obj = new URL(ALL_SURVEYS + "/" + survey.getUuid());
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            con.setRequestMethod("PUT");
            con.setRequestProperty("Content-Type", "application/json");

            con.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream(),"UTF-8");
            wr.write(new Gson().toJson(survey));
            wr.flush();
            wr.close();

            int responseCode = con.getResponseCode();
        } catch (Exception ex) {
            throw new IllegalArgumentException("Exception updating survey", ex);
        }

    }

}
