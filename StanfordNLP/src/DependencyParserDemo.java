
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.ling.Word;
import edu.stanford.nlp.parser.nndep.DependencyParser;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import edu.stanford.nlp.trees.GrammaticalStructure;
import edu.stanford.nlp.trees.TypedDependency;
import edu.stanford.nlp.util.logging.Redwood;

import java.io.StringReader;
import java.util.Collection;
import java.util.List;

public class DependencyParserDemo {
	
	private static Redwood.RedwoodChannels log = Redwood.channels(DependencyParserDemo.class);
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		MaxentTagger tagger = new MaxentTagger("taggers/english-left3words-distsim.tagger");
	    DependencyParser parser = DependencyParser.loadFromModelFile(DependencyParser.DEFAULT_MODEL);
	    
	    String sentence_1 = "Magnitude 6.7 earthquake hits Nagano in Japan, injuring at least 39";
	    String sentence_2 = "6.8 earthquake hits Nagano in Japan, injuring 13 and destroying five houses";
	    String sentence_3 = "Magnitude 6.7 earthquake hits Nagano in Japan, injuring at least 39 and destroying 10 houses";
	    String sentence_4 = "Death toll rises to 123 in Mexico following Tropical Storm Ingrid";
	    String sentence_5 = "Hurricane Sandy cancels many flights at Orlando Airport";
	    
	    List<Word> sent = PTBTokenizer.factory().getTokenizer(new StringReader(sentence_1)).tokenize();
        
        List<TaggedWord> taggedSent = tagger.tagSentence(sent);	

	    GrammaticalStructure gs = parser.predict(taggedSent);
	    
	    Collection<TypedDependency> td = gs.typedDependencies();
	    
	    for(TypedDependency b : td) {
	    	System.out.println(b.dep().word() + " " + b.gov().word() + " " + b.reln());
	    }
	    
	    //System.out.println("\n"); 
	    log.info(gs);
	    
	
	    
	    
	    
	    
	}

}
