package Main;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


public class Request {
	
	private String m_url;
	private HashMap<String, String> m_params;

	public Request(String url, HashMap<String, String> params) {
		m_url = url;
		m_params = params;
	}
	
	public Request(String url) {
		m_url = url;
		m_params = new HashMap<String, String>();
	}
	
	public void setParameters(HashMap<String, String> params) {
		m_params = params;
	}
	
	public void setUrl(String url) {
		m_url = url;
	}
	

	
	public InputStream send() throws IOException {
		String parameters = "";
		for(Map.Entry<String, String> entry : m_params.entrySet()) {
			parameters += entry.getKey() + "=" + entry.getValue() + "&";
		}
		
		if(parameters.length() > 0)
			parameters.substring(0, parameters.length() - 1);
		
		System.out.println(m_url + "?" + parameters);
		URL url = new URL(m_url + "?" + parameters);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		
		con.addRequestProperty("User-Agent", "GitAPI-TEST-Java");
		con.addRequestProperty("Accept", "application/vnd.github.v3+json");
		
		con.setRequestMethod("GET");
		
		
		InputStream input = con.getInputStream();
		//System.out.println(Util.convertStreamToString(input).replace(",", ",\n"));
		
		return input;
	}
}
