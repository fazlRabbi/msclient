package no.hib.msapp.RESTClient;

import com.google.gson.Gson;
import no.hib.msapp.entities.BankIdUser;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginFacade {

    private final String Url = "http://ms-services.medicloud.eu-gb.mybluemix.net/api/login";

    public LoginFacade() {
    }

    public boolean validPatient(BankIdUser user) {
        try {
            URL obj = new URL(Url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");

            con.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream(),"UTF-8");
            wr.write(new Gson().toJson(user));
            wr.flush();
            wr.close();

            int responseCode = con.getResponseCode();
            return responseCode == 200;
        } catch (Exception ex) {
            throw new IllegalArgumentException("Exception validating patient", ex);
        }
    }
}
