/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hib.msapp.RESTClient;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import no.hib.msapp.entities.Symptom;

import java.lang.reflect.Type;
import java.util.List;



/**
 *
 * @author Leif Arne
 */
public class SymptomFacade extends RestClient {
	private final String ALL_SYMPTOMS = "http://msservices.eu-gb.mybluemix.net/api/settings/symptoms";

	public SymptomFacade() {
	}

	public List<Symptom> findAll() {
		List<Symptom> symptoms;
		Type type = new TypeToken<List<Symptom>>() {
			private static final long serialVersionUID = 3677306972143566545L;
		}.getType();
		StringBuilder response = this.executeGETRequest(ALL_SYMPTOMS);
		symptoms = new Gson().fromJson(response.toString(), type);

		return symptoms;
	}
}
