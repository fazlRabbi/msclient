/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hib.msapp.RESTClient;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 *
 * @author Leif Arne
 */
public class SettingsFacade extends RestClient {
    private final String MAX_WEEKS = "http://msservices.eu-gb.mybluemix.net/api/settings/preperationStart";

    public SettingsFacade() {
    }

	public int getMaxWeeks() {
		int weeks = 0;
		StringBuilder response = executeGETRequest(MAX_WEEKS);
		Type type = new TypeToken<Integer>() {
			private static final long serialVersionUID = 8580103486067868583L;
		}.getType();
		weeks = new Gson().fromJson(response.toString(), type);
		return weeks;
	}
}
