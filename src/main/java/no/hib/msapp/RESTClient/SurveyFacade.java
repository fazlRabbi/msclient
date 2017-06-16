/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hib.msapp.RESTClient;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import no.hib.msapp.entities.AppointmentPreperation;
import no.hib.msapp.entities.Symptom;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Leif Arne
 */
public class SurveyFacade {

    private final String ALL_SURVEYS = "http://ms-services.medicloud.eu-gb.mybluemix.net/api/preperation";
    private String ssn;

    public SurveyFacade(String Ssn) {
        ssn = Ssn;
    }

    public List<AppointmentPreperation> findAll() {
        List<AppointmentPreperation> consultationPreperations;
        try {
            URL obj = new URL(ALL_SURVEYS + "/" + ssn + "/all");

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

            Type type = new TypeToken<List<AppointmentPreperation>>() {
            }.getType();
            consultationPreperations = new Gson().fromJson(response.toString(), type);
        } catch (Exception ex) {
            throw new IllegalArgumentException("Exception getting all preperations: ", ex);
        }
        Collections.sort(consultationPreperations);
        return consultationPreperations;
    }

    public AppointmentPreperation findByGuid(String Guid) {
        AppointmentPreperation consultationPreperation;
        try {
            URL obj = new URL(ALL_SURVEYS + "/" + Guid);

            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            //add reuqest header
            con.setRequestMethod("GET");

            int responseCode = con.getResponseCode();

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), "UTF-8"));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            consultationPreperation = new Gson().fromJson(response.toString(), AppointmentPreperation.class);
        } catch (Exception ex) {
            throw new IllegalArgumentException("Exception getting preperation with id: " + Guid, ex);
        }
        return consultationPreperation;

    }

    public AppointmentPreperation findNextSurvey() {
        AppointmentPreperation consultationPreperation;
        try {
            URL obj = new URL(ALL_SURVEYS + "/" + ssn + "/next");

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

            consultationPreperation = new Gson().fromJson(response.toString(), AppointmentPreperation.class);
        } catch (Exception ex) {
            return null;
        }
        return consultationPreperation;
    }

    public void saveSurvey(AppointmentPreperation survey) {

        survey = removeEmptySymptoms(survey);
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

    private AppointmentPreperation removeEmptySymptoms(AppointmentPreperation survey) {
        List<Symptom> nonEmpty = new ArrayList<>();
        for(Symptom symptom : survey.getSymptoms()){
            if(!symptom.getSeverity().isEmpty()){
                nonEmpty.add(symptom);
            }
        }
        survey.setSymptoms(nonEmpty);
        return survey;
    }
}
