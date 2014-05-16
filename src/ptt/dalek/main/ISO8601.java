package ptt.dalek.main;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class ISO8601 {
	private static int INDEX_YEAR_FIRST = 0;
	private static int INDEX_YEAR_LAST = 4;
	private static int INDEX_MONTH_FIRST = 5;
	private static int INDEX_MONTH_LAST = 7;
	private static int INDEX_DAY_FIRST = 8;
	private static int INDEX_DAY_LAST = 10;

	private static int INDEX_HOUR_FIRST = 11;
	private static int INDEX_HOUR_LAST = 13;
	private static int INDEX_MINUTE_FIRST = 14;
	private static int INDEX_MINUTE_LAST = 16;
	private static int INDEX_SECOND_FIRST = 17;
	private static int INDEX_SECOND_LAST = 19;

	public static long toUnix(String isoDate) {
		if(isoDate.length() < INDEX_SECOND_LAST)
			return -1;
		
		GregorianCalendar g = new GregorianCalendar(
					Integer.parseInt(isoDate.substring(INDEX_YEAR_FIRST, INDEX_YEAR_LAST)), 
					Integer.parseInt(isoDate.substring(INDEX_MONTH_FIRST, INDEX_MONTH_LAST)) - 1,
					Integer.parseInt(isoDate.substring(INDEX_DAY_FIRST, INDEX_DAY_LAST)),
					Integer.parseInt(isoDate.substring(INDEX_HOUR_FIRST, INDEX_HOUR_LAST)),
					Integer.parseInt(isoDate.substring(INDEX_MINUTE_FIRST, INDEX_MINUTE_LAST)),
					Integer.parseInt(isoDate.substring(INDEX_SECOND_FIRST, INDEX_SECOND_LAST))
				);

		return g.getTimeInMillis() / 1000L;
	}

	public static String fromUnix(long unix) {		
		Date date = new Date(unix * 1000L);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

		return dateFormat.format(date);
	}
}
