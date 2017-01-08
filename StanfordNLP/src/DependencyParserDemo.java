
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
	    
	    String sentence_1 = "At least 30 injured in Mt. Everest avalanche following Nepal earthquake";
	    String sentence_2 = "6.8 earthquake hits Nagano in Japan, injuring 13 and destroying five houses";
	    String sentence_3 = "Magnitude 6.7 earthquake hits Nagano in Japan, injuring at least 39 and destroying 10 houses";
	    String sentence_4 = "Nagano quake leaves 39 injured as houses collapse";
	    String sentence_5 = "The earthquake in central Japan near the city of Nagano injuring at least 40 people, damaged dozens of building";
	    String sentence_6 = "Magnitude 6.7 earthquake hits Nagano in Japan, injuring at least 39 and destroying 10 houses";
	    
	    String sentence_7 = "More than a 1000 people die in Nepal and people are upset about what the Joker looks like this is a cool website.";
	    
	    List<Word> sent = PTBTokenizer.factory().getTokenizer(new StringReader(sentence_6)).tokenize();
	   
	    Lemmatizer lem = new Lemmatizer();
	    System.out.println(lem.lemmatize(sentence_7));
        
        List<TaggedWord> taggedSent = tagger.tagSentence(sent);	

	    GrammaticalStructure gs = parser.predict(taggedSent);
	    
	    Collection<TypedDependency> td = gs.typedDependencies();
	    
	    /*
	    for(TypedDependency b : td) {
	    	System.out.println(b.dep().word() + " " + b.gov().tag() + " " + b.reln());
	    } */
	    
	    //System.out.println("\n"); 
	    log.info(gs);
	    
	    
	    
	
	    
	    for (TypedDependency d : td) {
	    	
	    	if (d.reln().toString() != "root") {
	    		if (lem.lemmatize(d.gov().word()).get(0).equals("injure")){
	    		//if (d.gov().word().equals("injuring")) {
	    			if (d.dep().tag().equals("CD")) {
	    				System.out.println("Yarali Sayisi: " + d.dep().word());
	    				break;
	    			} else if (d.dep().tag().equals("NN") || d.dep().tag().equals("NNS")) {
	    				String injure_num = find_NN_Nummod(td, d.dep().word());
	    				if (injure_num != "") {
	    					System.out.println("Yarali Sayisi: " + injure_num);
	    					break;
	    				}
	    				
	    			} else if (d.dep().tag().equals("JJS")) {
	    				String injure_num = find_NN_Nummod(td, d.dep().word());
	    				if (injure_num != "") {
	    					System.out.println("Yarali Sayisi: " + injure_num);
	    					break;
	    				}
	    			}
	    		} else if (lem.lemmatize(d.dep().word()).get(0).equals("injure")) {
	    		//else if (d.dep().word().equals("injuring")) {
	    			if (d.gov().tag().equals("CD")) {
	    				System.out.println("Yarali Sayisi: " + d.gov().word());
	    				break;
	    			} else if (d.gov().tag().equals("NN") || d.gov().tag().equals("NNS")) {
	    				String injure_num = find_NN_Nummod(td, d.gov().word());
	    				if (injure_num != "") {
	    					System.out.println("Yarali Sayisi: " + injure_num);
	    					break;
	    				}
	    			}
    			}
	    	}
	    	
	    	
	    }
	    
	    
	}
	
	public static String find_NN_Nummod(Collection<TypedDependency> td, String gov) {
		String ret = "";
		for (TypedDependency d : td) {
			if (d.reln().toString().equals("nummod") && d.gov().word().equals(gov)) {
				ret = d.dep().word();
			}
		}
		
		return ret;
		
	} 

}
