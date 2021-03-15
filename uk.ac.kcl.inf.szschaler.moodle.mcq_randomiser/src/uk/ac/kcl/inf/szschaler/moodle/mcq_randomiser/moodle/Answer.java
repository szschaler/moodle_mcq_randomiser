package uk.ac.kcl.inf.szschaler.moodle.mcq_randomiser.moodle;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Answer extends TextContainer {	
	@XmlAttribute
	public String fraction;
	
	@XmlElement
	public TextContainer feedback;
	
	public Answer() {}
	
	public Answer(Answer source) {
		super(source);
		this.fraction = source.fraction;
		this.feedback = source.feedback;
	}
}
