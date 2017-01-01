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

public class Main {
	
	private static Redwood.RedwoodChannels log = Redwood.channels(DependencyParserDemo.class);
	private static Find finder = new Find();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	/*	MaxentTagger tagger = new MaxentTagger("taggers/english-left3words-distsim.tagger");
	    DependencyParser parser = DependencyParser.loadFromModelFile(DependencyParser.DEFAULT_MODEL);
	    
	    //String sentence_1 = "At least 30 injured in Mt. Everest avalanche following Nepal earthquake";
	    //String sentence_2 = "6.8 earthquake hits Nagano in Japan, injuring 13 and destroying five houses";
	    //String sentence_3 = "Magnitude 6.7 earthquake hits Nagano in Japan, injuring at least 39 and destroying 10 houses";
	    String sentence_4 = "Nagano quake leaves 2180 injured as houses collapse";
	    String sentence_5 = "The earthquake in central Japan near the city of Nagano injuring at least 40 people, damaged dozens of building";
	    //String sentence_6 = "Magnitude 6.7 earthquake hits Nagano in Japan, injuring at least 39 and destroying 10 houses";
	    String sentence_7 = "Shocking:  9 die in India, 30 in Nepal #Earthquake";
	    
	    
	    
	    List<Word> sent = PTBTokenizer.factory().getTokenizer(new StringReader(sentence_7)).tokenize();
        List<TaggedWord> taggedSent = tagger.tagSentence(sent);	
	    GrammaticalStructure gs = parser.predict(taggedSent);	    
	    Collection<TypedDependency> td = gs.typedDependencies();
	    
	    log.info(gs);
	    
	    System.out.println("Injure = " + finder.findInjure(td));
	    System.out.println("Die = " + finder.findDie(td));
	    System.out.println("Trap = " + finder.findTrap(td));
	    System.out.println("Kill = " + finder.findKill(td));
	    System.out.println("Miss = " + finder.findMiss(td)); */
	    
	    

	}

}
