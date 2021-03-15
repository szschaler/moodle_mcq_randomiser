package uk.ac.kcl.inf.szschaler.moodle.mcq_randomiser.moodle;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import uk.ac.kcl.inf.szschaler.moodle.mcq_randomiser.cdata.CDataAdapter;

@XmlRootElement
public class TextContainer {
	@XmlAttribute(required = false)
	public String format;
	
	@XmlElement
	@XmlJavaTypeAdapter(CDataAdapter.class)
	public String text;
	
	public TextContainer() {}
	
	public TextContainer(TextContainer source) {
		this.format = source.format;
		this.text = source.text;
	}
}
