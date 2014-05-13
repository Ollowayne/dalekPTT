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
		for(Map.Entry<String, String> entry : params.entrySet()) {
			parameters += entry.getKey() + "=" + entry.getValue() + "&";
		}
		
		if(parameters.length() > 0)
			parameters.substring(0, parameters.length() - 1);
		
		System.out.println(url + "?" + parameters);
		try {
			URL url = new URL(this.url + "?" + parameters);

			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			con.addRequestProperty("User-Agent", "GitAPI-TEST-Java");
			con.addRequestProperty("Accept", "application/vnd.github.v3+json");
			con.setRequestMethod("GET");

			int responseCode = con.getResponseCode();
			String responseMessage = con.getResponseMessage();
			if(responseCode == HttpURLConnection.HTTP_OK && responseMessage.equals("OK"))
				return con.getInputStream();
			else
				//TODO
				System.out.println("Reponse::send - Response Message: " + responseMessage);

		} catch (UnknownHostException e) {
			//TODO
			System.out.println("Unable to connect to " + this.url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
