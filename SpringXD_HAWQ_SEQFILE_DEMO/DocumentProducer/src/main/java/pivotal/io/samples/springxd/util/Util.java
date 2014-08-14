package pivotal.io.samples.springxd.util;
/**
 * @author palads1
 *
 */
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Util {

	public static String getDateWithTime(){
		SimpleDateFormat fmt = new SimpleDateFormat("MMM-dd-yyyy HH:mm:ss");
		String dateStr = fmt.format(Calendar.getInstance().getTime());
		return dateStr;
	}
	public static String getDateWithTime1(){
		SimpleDateFormat fmt = new SimpleDateFormat("MMM-dd-yyyy'T'HH-mm-ss");
		String dateStr = fmt.format(Calendar.getInstance().getTime());
		return dateStr;
	}
	public static String getDate(){
		SimpleDateFormat fmt = new SimpleDateFormat("MMM-dd-yyyy");
		String dateStr = fmt.format(Calendar.getInstance().getTime());
		return dateStr;
	}
}
