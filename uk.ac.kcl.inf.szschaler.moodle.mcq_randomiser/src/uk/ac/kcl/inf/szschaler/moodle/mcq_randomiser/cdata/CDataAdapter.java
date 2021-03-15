package uk.ac.kcl.inf.szschaler.moodle.mcq_randomiser.cdata;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Based on https://coderleaf.wordpress.com/2016/11/14/handling-cdata-with-jaxb/
 *
 */
public class CDataAdapter extends XmlAdapter<String, String> {

	private static final String CDATA_START = "<![CDATA[";
	private static final String CDATA_STOP = "]]>";

	/**
	 * Check whether a string is a CDATA string
	 * 
	 * @param s the string to check
	 * @return
	 */
	public static boolean isCdata(String s) {
		s = s.trim();
		boolean test = (s.startsWith(CDATA_START) && s.endsWith(CDATA_STOP));
		return test;
	}

	/**
	 * Parse a CDATA String.<br />
	 * If is a CDATA, removes leading and trailing string<br />
	 * Otherwise does nothing
	 * 
	 * @param s the string to parse
	 * @return the parsed string
	 */
	public static String parse(String s) {

		StringBuilder sb = null;
		s = s.trim();

		if (isCdata(s)) {
			sb = new StringBuilder(s);
			sb.replace(0, CDATA_START.length(), "");

			s = sb.toString();
			sb = new StringBuilder(s);
			sb.replace(s.lastIndexOf(CDATA_STOP), s.lastIndexOf(CDATA_STOP) + CDATA_STOP.length(), "");
			s = sb.toString();
		}
		return s;
	}

	/**
	 * Add CDATA leading and trailing to a string if not already a CDATA
	 * 
	 * @param s
	 * @return
	 */
	public static String print(String s) {
		if (isCdata(s)) {
			return s;
		} else {
			if (s.isEmpty()) {
				return "";
			} else {
				return CDATA_START + s + CDATA_STOP;				
			}
		}
	}

	@Override
	public String unmarshal(String value) {
		return parse(value);
	}

	@Override
	public String marshal(String value) {
		return print(value);
	}
}