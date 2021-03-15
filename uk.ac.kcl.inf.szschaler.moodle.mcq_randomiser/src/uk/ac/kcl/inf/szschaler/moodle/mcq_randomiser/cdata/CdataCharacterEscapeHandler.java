package uk.ac.kcl.inf.szschaler.moodle.mcq_randomiser.cdata;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import org.glassfish.jaxb.core.marshaller.CharacterEscapeHandler;

/**
 * Based on https://coderleaf.wordpress.com/2016/11/14/handling-cdata-with-jaxb/
 *
 */
public class CdataCharacterEscapeHandler implements CharacterEscapeHandler {

	public CdataCharacterEscapeHandler() {
		super();
	}

	/**
	 * @param ch       The array of characters.
	 * @param start    The starting position.
	 * @param length   The number of characters to use.
	 * @param isAttVal true if this is an attribute value literal.
	 */
	public void escape(char[] ch, int start, int length, boolean isAttVal, Writer writer) throws IOException {

		if (CDataAdapter.isCdata(new String(ch))) {
			writer.write(ch, start, length);
		} else {
			StringWriter buffer = new StringWriter();

			for (int i = start; i < start + length; i++) {
				buffer.write(ch[i]);
			}

			String st = buffer.toString();

			st = buffer.toString().replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;")
					.replace("'", "&apos;").replace("\"", "&quot;");

			writer.write(st);
		}
	}
}