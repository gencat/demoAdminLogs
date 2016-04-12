package cat.gencat.demoadminlogs.util;

/**
 * 
 * @author cscanigo
 *
 */
public class PaginationUtils {

	/**
	 * 
	 * @param r records
	 * @param rpp records per page
	 * @return
	 */
	public static int calculateNumberOfPages(long r, int rpp) {
		float nrOfPages = (float) r / rpp;
		return (int) (((nrOfPages > (int) nrOfPages) || nrOfPages == 0.0) ? nrOfPages + 1
				: nrOfPages);
	}
	
}
