import java.io.IOException;
import java.io.StringReader;
import java.util.List;


import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.ling.Word;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
 
public class DemoTagger {

	public static void main(String[] args) throws IOException,
    ClassNotFoundException {
		
		// Initialize the tagger
        MaxentTagger tagger = new MaxentTagger("taggers/left3words-distsim-wsj-0-18.tagger");
        
        
        /* Start of Basic Tagger */
        
        // The sample string
        String sample = "6.8 earthquake hits Nagano in Japan, injuring 13 and destroying five houses";
        
        String tagged = tagger.tagString(sample);
        
        // Output the result
        System.out.println(tagged);
        
        /* End of Basic Tagger */
        
        
        
        
        
        
        /* First Tokenize String and Tagging each word */
        
        List<Word> sent = PTBTokenizer.factory().getTokenizer(new StringReader("6.8 earthquake hits Nagano in Japan, injuring 13 and destroying five houses")).tokenize();
        
        
        List<TaggedWord> taggedSent = tagger.tagSentence(sent);
        for (TaggedWord tw : taggedSent) {
          
          System.out.println("Word = " + tw.word() + " Tag = " + tw.tag());
        }

	}

}
