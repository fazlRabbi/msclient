package no.hib.msapp.RESTClient;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import no.hib.msapp.entities.OtherSubject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class OtherSubjectFacade {
    private final String ALL_SUBJECTS = "http://msservices.eu-gb.mybluemix.net/api/settings/othersubjects";

    public OtherSubjectFacade() {
    }

    public List<OtherSubject> findAll() {
        List<OtherSubject> symptoms;
        try {
            URL obj = new URL(ALL_SUBJECTS);

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

            Type type = new TypeToken<List<OtherSubject>>() {}.getType();
            symptoms = new Gson().fromJson(response.toString(), type);
        } catch (Exception ex) {
            throw new IllegalArgumentException("Exception getting all subjects: ", ex);
        }


        return symptoms;
    }
}