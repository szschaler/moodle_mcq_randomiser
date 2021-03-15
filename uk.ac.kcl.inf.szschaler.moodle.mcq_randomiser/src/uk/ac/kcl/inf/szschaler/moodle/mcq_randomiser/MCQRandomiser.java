package uk.ac.kcl.inf.szschaler.moodle.mcq_randomiser;

import java.io.File;

import org.glassfish.jaxb.core.marshaller.CharacterEscapeHandler;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import uk.ac.kcl.inf.szschaler.moodle.mcq_randomiser.cdata.CdataCharacterEscapeHandler;
import uk.ac.kcl.inf.szschaler.moodle.mcq_randomiser.moodle.Quiz;

/**
 * Randomise an exported MCQ question by producing all combinations of answer
 * subsets of size n.
 * 
 * @author k1074611
 *
 */
public class MCQRandomiser {

	public static void main(String[] args) {
		String importFileName = args[0];
		int numAnswers = Integer.valueOf(args[1]);
		String outputFileName = args[2];

		try {
			File inputFile = new File(importFileName);
			JAXBContext jaxbContext = JAXBContext.newInstance(Quiz.class);

			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			Quiz quiz = (Quiz) jaxbUnmarshaller.unmarshal(inputFile);

			Quiz randomisedQuiz = quiz.randomise(numAnswers);
			
			File outputFile = new File(outputFileName);
			
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(
					"org.glassfish.jaxb.characterEscapeHandler",
				    new CdataCharacterEscapeHandler());
	        // output pretty printed
	        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

	        jaxbMarshaller.marshal(randomisedQuiz, outputFile);

		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}
}
