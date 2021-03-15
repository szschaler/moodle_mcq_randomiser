package uk.ac.kcl.inf.szschaler.moodle.mcq_randomiser.moodle;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "question")
public class Question {
	@XmlAttribute
	public String type;

	@XmlElement
	public TextContainer name;

	@XmlElement
	public TextContainer questiontext;

	public TextContainer generalfeedback;

	@XmlElement
	public int defaultgrade;

	@XmlElement
	public float penalty;

	@XmlElement
	public int hidden;

	@XmlElement
	public long idnumber;

	@XmlElement
	public boolean single;

	@XmlElement
	public boolean shuffleanswers;

	@XmlElement
	public String answernumbering;

	@XmlElement
	public int showstandardinstructions;

	@XmlElement
	public TextContainer correctfeedback;

	@XmlElement
	public TextContainer partiallycorrectfeedback;

	@XmlElement
	public TextContainer incorrectfeedback;

	@XmlElement
	public String shownumcorrect;

	@XmlElement(name = "answer")
	public List<Answer> answers;

	public Question() {}
	
	public Question(Question source, List<Answer> answers) {
		this.type = source.type;
		this.name= source.name;
		this.questiontext = source.questiontext;
		this.generalfeedback = source.generalfeedback;
		this.defaultgrade = source.defaultgrade;
		this.penalty = source.penalty;
		this.hidden = source.hidden;
		this.idnumber = source.idnumber;
		this.single = source.single;
		this.shuffleanswers = source.shuffleanswers;
		this.answernumbering = source.answernumbering;
		this.showstandardinstructions = source.showstandardinstructions;
		this.correctfeedback = source.correctfeedback;
		this.partiallycorrectfeedback = source.partiallycorrectfeedback;
		this.incorrectfeedback = source.incorrectfeedback;
		this.shownumcorrect = source.shownumcorrect;

		this.answers = new ArrayList<>(answers.size());
		for(Answer answer: answers) {
			this.answers.add(new Answer(answer));
		}
			
		// Fix marking
		int numCorrect = 0;
		int numIncorrect = 0;
		for (Answer answer: this.answers) {
			if (answer.fraction.startsWith("-")) {
				numIncorrect++;
			} else {
				numCorrect++;
			}
		}
		
		String correctMark = String.valueOf(Math.round((100.0d/numCorrect)*100000)/100000.0d);
		if (correctMark.endsWith(".0")) {
			correctMark = correctMark.substring(0, correctMark.length() - 2);
		}
		String incorrectMark = "-" + String.valueOf(Math.round((100.0d/numIncorrect)*100000)/100000.0d);
		if (incorrectMark.endsWith(".0")) {
			incorrectMark = incorrectMark.substring(0, incorrectMark.length() - 2);
		}
		for (Answer answer: this.answers) {
			if (answer.fraction.startsWith("-")) {
				answer.fraction = incorrectMark;
			} else {
				answer.fraction = correctMark;
			}
		}
	}
	
	/**
	 * Produce copies of this question with all subsets of numAnswers answers.
	 * 
	 * @param numAnswers
	 * @return
	 */
	public List<Question> randomise(int numAnswers) {
		if (numAnswers > answers.size()) {
			throw new IllegalArgumentException("numAnswers must be lower than the actual number of answers.");
		}
		
		List<Question> result = new ArrayList<>();
		
		List<List<Answer>> answerCombinations = compileAnswerSets(answers, numAnswers, new ArrayList<Answer>());
		
		for (List<Answer> answerSet : answerCombinations) {
			result.add(new Question(this, answerSet));
		}
		
		return result;
	}
	
	private List<List<Answer>> compileAnswerSets(List<Answer> answersToChooseFrom, int numAnswers, List<Answer> collectedSoFar) {
		List<List<Answer>> result = new ArrayList<>();

		if (answersToChooseFrom.isEmpty()) {
			return result;
		}
		
		for (int i= 0; i < answersToChooseFrom.size(); i++) {
			collectedSoFar.add(answersToChooseFrom.get(i));

			if (numAnswers > 1) {
				result.addAll(compileAnswerSets(answersToChooseFrom.subList(i + 1, answersToChooseFrom.size()), numAnswers - 1, collectedSoFar));
			} else {
				// Found a solution
				result.add(new ArrayList<Answer>(collectedSoFar));				
			}
			
			collectedSoFar.remove(answersToChooseFrom.get(i));
		}
		
		return result;
	}
}
