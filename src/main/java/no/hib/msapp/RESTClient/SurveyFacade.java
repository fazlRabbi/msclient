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

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Leif Arne
 */
public class SurveyFacade extends RestClient {

	private final String ALL_SURVEYS = "http://msservices.eu-gb.mybluemix.net/api/preperation";
	private String ssn;

	public SurveyFacade(String Ssn) {
		ssn = Ssn;
	}

	public List<AppointmentPreperation> findAll() {
		List<AppointmentPreperation> consultationPreperations;
		StringBuilder response = executeGETRequest(ALL_SURVEYS + "/" + ssn + "/all");
		Type type = new TypeToken<List<AppointmentPreperation>>() {
			private static final long serialVersionUID = -9122872410952899753L;
		}.getType();
		consultationPreperations = new Gson().fromJson(response.toString(), type);
		Collections.sort(consultationPreperations);
		return consultationPreperations;
	}

	public AppointmentPreperation findByGuid(String Guid) {
		AppointmentPreperation consultationPreperation;
		StringBuilder response = executeGETRequest(ALL_SURVEYS + "/" + Guid);
		consultationPreperation = new Gson().fromJson(response.toString(), AppointmentPreperation.class);
		return consultationPreperation;
	}

	public AppointmentPreperation findNextSurvey() {
		AppointmentPreperation consultationPreperation;
		StringBuilder response = executeGETRequest(ALL_SURVEYS + "/" + ssn + "/next");
		consultationPreperation = new Gson().fromJson(response.toString(), AppointmentPreperation.class);
		return consultationPreperation;
	}

	public void saveSurvey(AppointmentPreperation survey) {
		survey = removeEmptySymptoms(survey);
		this.executePUTRequest(ALL_SURVEYS + "/" + survey.getUuid(), new Gson().toJson(survey));
	}

	private AppointmentPreperation removeEmptySymptoms(AppointmentPreperation survey) {
		List<Symptom> nonEmpty = new ArrayList<>();
		for (Symptom symptom : survey.getSymptoms()) {
			if (!symptom.getSeverity().isEmpty()) {
				nonEmpty.add(symptom);
			}
		}
		survey.setSymptoms(nonEmpty);
		return survey;
	}

}
