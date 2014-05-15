package ptt.dalek.main;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PagedRequest extends Request implements Iterator<InputStream> {

	private static final String PAGE_PATTERN = ".*page=(\\d+).*";
	private static final String DELIMITER = ",";
	private static final String LINK_HEADER = "Link";
	
	private int currentPage;
	private int lastPage;
	private boolean next;

	public PagedRequest(String url) {
		super(url);
		
		this.currentPage = this.lastPage = 1;
		next = true;
	}
	
	@Override
	public boolean hasNext() {
		return next;
	}

	@Override
	public InputStream next() {
		return send();
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException("PagedRequest does not support 'remove'.");
	}
	
	@Override
	public InputStream send() {
		this.parameterMap.put("page", Integer.toString(currentPage));

		//Prepare parameters
		prepare();

		try {
			HttpURLConnection con = prepareConnection(new URL(String.format(URL_FORMAT, this.url, this.parameters)));

			String linkHeader = con.getHeaderField(LINK_HEADER);
			if(linkHeader != null) {
				for(String link : linkHeader.split(DELIMITER)) {
					if(link.contains("rel=\"next\"") || link.contains("rel=\"last\"")) {
						Matcher matcher = Pattern.compile(PAGE_PATTERN).matcher(link);
						matcher.matches();

						if(link.contains("rel=\"next\"")) {
							currentPage = Integer.parseInt(matcher.group(1));
						} else {
							lastPage = Integer.parseInt(matcher.group(1));
						}
					}
				}
			} else {
				next = false;
			}

			if(currentPage >= lastPage)
				next = false;

			int responseCode = con.getResponseCode();
			String responseMessage = con.getResponseMessage();
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
