package ptt.dalek.main;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

public class Request {

	private String url;
	private HashMap<String, String> params;

	public Request(String url) {
		this.url = url;
		this.params = new HashMap<String, String>();
	}

	public Request(String url, HashMap<String, String> params) {
		this.url = url;
		this.params = params;
	}

	public void setParameters(HashMap<String, String> params) {
		this.params = params;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public InputStream send() {
		String parameters = "";
		params.put("access_token", "bdf25b8b25bdc54e4eda6edbfa22c61c1f6560b8");

		for (Map.Entry<String, String> entry : params.entrySet()) {
			parameters += entry.getKey() + "=" + entry.getValue() + "&";
		}

		if (parameters.length() > 0)
			parameters.substring(0, parameters.length() - 1);

		try {
			URL url = new URL(this.url + "?" + parameters);

			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.addRequestProperty("User-Agent", "GitObservTest");
			con.addRequestProperty("Accept", "application/vnd.github.v3+json");
			con.setRequestMethod("GET");

			int responseCode = con.getResponseCode();
			String responseMessage = con.getResponseMessage();
			

			System.out.println("Reponse::send - Response Code: " + responseCode + ", Response Message: "
					+ responseMessage);

			if (responseCode == HttpURLConnection.HTTP_OK
					&& responseMessage.equals("OK"))
				return con.getInputStream();

		} catch (UnknownHostException e) {
			// TODO
			System.out.println("Unable to connect to " + this.url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
