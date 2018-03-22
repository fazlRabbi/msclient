package no.hib.msapp.RESTClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public abstract class RestClient {
	
	
	private static final int DEFAULT_RETRY_TIMEOUT = 1000;
	private static final int DEFAULT_RETRIES = 5;
	private static final String SERVICE_URL = "http://localhost:8080/msservice/api/";

	public StringBuilder executeGETRequest(String url) {
		return executeGETWithRetry(DEFAULT_RETRIES, url);
	}
	
	public void executePUTRequest(String url, String payload) {
		this.executePUTWithRetry(DEFAULT_RETRIES, url, payload);
	}
	
	private void executePUTWithRetry(int defaultRetries, String url, String payload) {

		url = SERVICE_URL + url;

		if (defaultRetries == 0) {
			throw new RuntimeException("Could not connecto to " + url + " after several retries");
		}
		HttpURLConnection con = null;
		try {
			URL obj = new URL(url);
			con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("PUT");
			con.setRequestProperty("Content-Type", "application/json");
			con.setDoOutput(true);
			OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream(), "UTF-8");
			wr.write(payload);
			wr.flush();
			wr.close();

			int responseCode = con.getResponseCode();
			if (responseCode == 200) {
				return;
			} else {
				con.disconnect();
				timout();
				System.out.println("PUT request failed => RETRY");
				executePUTWithRetry(defaultRetries - 1, url, payload);
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			if (con != null) {
				con.disconnect();
			}
		}
	}

	private StringBuilder executeGETWithRetry(int numberRetries, String url) {
		
		url = SERVICE_URL + url;
		
		if (numberRetries == 0) {
			throw new RuntimeException("Could not connect to " + url + " after several retries");
		}
		StringBuilder response = new StringBuilder();
		HttpURLConnection con = null;
		try {
			URL obj = new URL(url);
			con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");
			int responseCode = con.getResponseCode();
			if (responseCode == 200) {
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
				String inputLine;
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
				return response;

			} else {
				con.disconnect();
				timout();
				System.out.println("GET request to " + url + " failed; resp-code: " + responseCode + " => RETRY");
				return executeGETWithRetry(numberRetries - 1, url);
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			if (con != null) {
			}
			con.disconnect();
		}
	}

	private void timout() {
		synchronized (this) {
			try {
				this.wait(DEFAULT_RETRY_TIMEOUT);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
}
