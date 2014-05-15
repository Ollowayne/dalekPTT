package ptt.dalek.main;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.*;

public class Request {

	protected static final String URL_FORMAT = "%s?%s";
	
	protected String url;
	protected HashMap<String, String> parameterMap;
	protected String parameters;

	public Request(String url) {
		this.url = url;
		this.parameterMap = new HashMap<String, String>();
		this.parameters = "";
	}

	public Request(String url, HashMap<String, String> params) {
		this.url = url;
		this.parameterMap = params;
	}

	public void setParameters(HashMap<String, String> params) {
		this.parameterMap = params;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	protected void prepare() {
		//Set access token to increase X-RateLimit
		this.parameterMap.put("access_token", "bdf25b8b25bdc54e4eda6edbfa22c61c1f6560b8");
		this.parameterMap.put("per_page", "100");

		String parameters = "";
		for (Map.Entry<String, String> entry : parameterMap.entrySet()) {
			parameters += entry.getKey() + "=" + entry.getValue() + "&";
		}

		if (parameters.length() > 0)
			parameters.substring(0, parameters.length() - 1);

		this.parameters = parameters;
	}
	
	protected HttpURLConnection prepareConnection(URL url) throws IOException {
		HttpURLConnection con = (HttpURLConnection)url.openConnection();
		con.addRequestProperty("User-Agent", "GitObservTest");
		con.addRequestProperty("Accept", "application/vnd.github.v3+json");
		con.setRequestMethod("GET");
		return con;
	}

	public InputStream send() {
		//Prepare parameters
		prepare();

		try {
			
			HttpURLConnection con = prepareConnection(new URL(String.format(URL_FORMAT, this.url, this.parameters)));
			
			int responseCode = con.getResponseCode();
			String responseMessage = con.getResponseMessage();
		    System.out.println("RateLimit: " + con.getHeaderField("X-RateLimit-Remaining") + "/" + con.getHeaderField("X-RateLimit-Limit"));
			//System.out.println("Reponse::send - Response Code: " + responseCode + ", Response Message: "
			//		+ responseMessage);

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
