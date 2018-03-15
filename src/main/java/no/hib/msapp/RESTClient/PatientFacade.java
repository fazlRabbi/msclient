/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hib.msapp.RESTClient;

import com.google.gson.Gson;
import no.hib.msapp.entities.Patient;

/**
 *
 * @author Leif Arne
 */
public class PatientFacade extends RestClient {

    private final String ALL_PATIENTS = "http://msservices.eu-gb.mybluemix.net/api/patients";

    public PatientFacade() {
    }

    public Patient findPatientBySsn(String Ssn) {
        Patient patient;
        StringBuilder response = executeGETRequest(ALL_PATIENTS + "/" + Ssn);
        patient = new Gson().fromJson(response.toString(), Patient.class);
        return patient;
    }

    public void updatePatient(Patient patient) {
    		this.executePUTRequest(ALL_PATIENTS + "/" + patient.getSsn(), new Gson().toJson(patient));
    }
}
