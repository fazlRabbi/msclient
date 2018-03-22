/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hib.msapp.RESTClient;

import com.google.gson.Gson;
import no.hib.msapp.entities.AppointmentPreperation;
import no.hib.msapp.entities.Patient;



/**
 *
 * @author Leif Arne
 */
public class AppointmentFacade extends RestClient {

    private final String ALL_SURVEYS = "preperation";

    public AppointmentFacade(Patient p) {
    }

    public AppointmentPreperation findSurvey(int id) {
        AppointmentPreperation preperation;
        StringBuilder response = executeGETRequest(ALL_SURVEYS + "/" + id);
        preperation = new Gson().fromJson(response.toString(), AppointmentPreperation.class);
        return preperation;
    }

    public AppointmentPreperation findNextSurvey() {
        AppointmentPreperation preperation;
        StringBuilder response = executeGETRequest(ALL_SURVEYS + "/next");
        preperation = new Gson().fromJson(response.toString(), AppointmentPreperation.class);
        return preperation;
    }

    public void saveSurvey(AppointmentPreperation survey) {
    		this.executePUTRequest(ALL_SURVEYS + "/" + survey.getUuid(), new Gson().toJson(survey));
    }

}
