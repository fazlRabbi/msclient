package no.hib.msapp.RESTClient;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import no.hib.msapp.entities.OtherSubject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class OtherSubjectFacade extends RestClient {
    private final String ALL_SUBJECTS = "settings/othersubjects";

    public OtherSubjectFacade() {
    }

    public List<OtherSubject> findAll() {
		List<OtherSubject> symptoms = new ArrayList<>();
		StringBuilder response = executeGETRequest(ALL_SUBJECTS);
		Type type = new TypeToken<List<OtherSubject>>() {
			private static final long serialVersionUID = -2863166235047217641L;
		}.getType();
		symptoms = new Gson().fromJson(response.toString(), type);
		return symptoms == null ? new ArrayList<OtherSubject>() : symptoms;
    }
}