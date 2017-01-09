import java.util.List;
import java.util.Properties;

import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.time.*;
import edu.stanford.nlp.util.CoreMap;

public class StanfordDateTime {


	private static AnnotationPipeline pipeline = new AnnotationPipeline();
	
	
	public StanfordDateTime() {
		Properties props = new Properties();
		pipeline.addAnnotator(new TokenizerAnnotator(false));
	    pipeline.addAnnotator(new WordsToSentencesAnnotator(false));
	    pipeline.addAnnotator(new POSTaggerAnnotator(false));
	    pipeline.addAnnotator(new TimeAnnotator("sutime", props));
	}
	
	public String findDate(String sentence) {
	  	String ret = null;
	  	
	  	Annotation annotation = new Annotation(sentence);	      
	    pipeline.annotate(annotation);

	    List<CoreMap> timexAnnsAll = annotation.get(TimeAnnotations.TimexAnnotations.class);
	    for (CoreMap cm : timexAnnsAll) {
	      ret = cm.get(TimeExpression.Annotation.class).getTemporal().getTimexValue();
	    }
	    
	    return ret;
	  }
   
 }