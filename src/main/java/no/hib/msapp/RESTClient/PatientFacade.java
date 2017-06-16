/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hib.msapp.RESTClient;

import com.google.gson.Gson;
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
public class PatientFacade {

    private final String ALL_PATIENTS = "http://ms-services.medicloud.eu-gb.mybluemix.net/api/patients";

    public PatientFacade() {
    }

    public Patient findPatientBySsn(String Ssn) {
        Patient patient;
        try {
            URL obj = new URL(ALL_PATIENTS + "/" + Ssn);

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

            patient = new Gson().fromJson(response.toString(), Patient.class);
        } catch (Exception ex) {
            throw new IllegalArgumentException("Exception getting patient with Ssn: " + Ssn, ex);
        }
        return patient;
    }

    public void updatePatient(Patient patient) {
        try {
            URL obj = new URL(ALL_PATIENTS + "/" + patient.getSsn());
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            con.setRequestMethod("PUT");
            con.setRequestProperty("Content-Type", "application/json");

            con.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream(),"UTF-8");
            wr.write(new Gson().toJson(patient));
            wr.flush();
            wr.close();

            int responseCode = con.getResponseCode();
        } catch (Exception ex) {
            throw new IllegalArgumentException("Exception updating patient", ex);
        }
    }
}
