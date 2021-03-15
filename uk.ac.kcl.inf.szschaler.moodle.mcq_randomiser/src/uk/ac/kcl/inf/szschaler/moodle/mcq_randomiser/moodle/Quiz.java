package uk.ac.kcl.inf.szschaler.moodle.mcq_randomiser.moodle;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Quiz {
	@XmlElement(name = "question")
	public List<Question> questions;
	
	/**
	 * Create a new quiz built by rendering all questions into sets of questions with all subsets of numAnswers answers.
	 * @param numAnswers
	 * @return
	 */
	public Quiz randomise(int numAnswers) {
		Quiz result = new Quiz();
		result.questions = new ArrayList<>();
		
		for (Question question: questions) {
			result.questions.addAll(question.randomise(numAnswers));
		}
		
		return result;
	}
	
}
