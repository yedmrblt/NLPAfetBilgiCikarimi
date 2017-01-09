import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.SwingWorker;

import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.ling.Word;
import edu.stanford.nlp.parser.nndep.DependencyParser;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import edu.stanford.nlp.trees.GrammaticalStructure;
import edu.stanford.nlp.trees.TypedDependency;

public class FindStanford extends SwingWorker<String, String>{
	
	private MaxentTagger tagger = new MaxentTagger("taggers/english-left3words-distsim.tagger");
    private DependencyParser parser = DependencyParser.loadFromModelFile(DependencyParser.DEFAULT_MODEL);
	private static Find_V2 finder = new Find_V2();
	public static String sum;
	
	public static Map<String, Integer> whereMap = new HashMap<String, Integer>();
	public static Map<String, Integer> whatMap = new HashMap<String, Integer>();
	public static Map<String, Integer> diedMap = new HashMap<String, Integer>();
	public static Map<String, Integer> injureMap = new HashMap<String, Integer>();
	public static Map<String, Integer> trapMap = new HashMap<String, Integer>();
	public static Map<String, Integer> killMap = new HashMap<String, Integer>();
	public static Map<String, Integer> missMap = new HashMap<String, Integer>();
	public static Map<String, Integer> magMap = new HashMap<String, Integer>();
	public static Map<String, Integer> dateTimeMap = new HashMap<String, Integer>();
	
	private String findMaxFromMap(Map<String, Integer> map) {
		
		if (map.size() > 0) {
			Map.Entry<String, Integer> maxEntry = null;
			
			for (Map.Entry<String, Integer> entry : map.entrySet()) {
				
				if (maxEntry == null || entry.getValue().compareTo(((Entry<String, Integer>) maxEntry).getValue()) > 0) {
					maxEntry = entry;
				}
			}

			return maxEntry.getKey();
		} else {
			return null;
		}
		
		
	}
	
	private void injure(Collection<TypedDependency> td) {
		
		String result = finder.findInjure(td);
		if (result != null) {
			
			if (injureMap.containsKey(result)) {
				int value = injureMap.get(result);
				value = value + 1;
				injureMap.replace(result, value);
			} else {
				injureMap.put(result, 1);
			}
		}
		
	}
	
	private void die(Collection<TypedDependency> td) {
		
		String result = finder.findDie(td);
		if (result != null) {
			
			if (diedMap.containsKey(result)) {
				int value = diedMap.get(result);
				value = value + 1;
				diedMap.replace(result, value);
			} else {
				diedMap.put(result, 1);
			}
		}
		
	}
	
	private void trap(Collection<TypedDependency> td) {
		
		String result = finder.findTrap(td);
		if (result != null) {
			
			if (trapMap.containsKey(result)) {
				int value = trapMap.get(result);
				value = value + 1;
				trapMap.replace(result, value);
			} else {
				trapMap.put(result, 1);
			}
		}
		
	}
	
	
	private void kill(Collection<TypedDependency> td) {
		
		String result = finder.findKill(td);
		if (result != null) {
			
			if (diedMap.containsKey(result)) {
				int value = diedMap.get(result);
				value = value + 1;
				diedMap.replace(result, value);
			} else {
				diedMap.put(result, 1);
			}
		}
		
	}
	
	private void miss(Collection<TypedDependency> td) {
		
		String result = finder.findMiss(td);
		if (result != null) {
			
			if (missMap.containsKey(result)) {
				int value = missMap.get(result);
				value = value + 1;
				missMap.replace(result, value);
			} else {
				missMap.put(result, 1);
			}
		}
		
	}
	
	private void what(Collection<TypedDependency> td) {
		
		String result = finder.findDisaster(td);
		if (result != null) {
			
			if (whatMap.containsKey(result)) {
				int value = whatMap.get(result);
				value = value + 1;
				whatMap.replace(result, value);
			} else {
				whatMap.put(result, 1);
			}
		}
		
	}
	
	private void where(Collection<TypedDependency> td) {
		
		String result = finder.findLocation(td);
		if (result != null) {
			
			if (whereMap.containsKey(result)) {
				int value = whereMap.get(result);
				value = value + 1;
				whereMap.replace(result, value);
			} else {
				whereMap.put(result, 1);
			}
		}
		
	}
	
	private void magnitude(Collection<TypedDependency> td) {
		String result = finder.findMagnitude(td);
		if (result != null) {
			
			if (magMap.containsKey(result)) {
				int value = magMap.get(result);
				value = value + 1;
				magMap.replace(result, value);
			} else {
				magMap.put(result, 1);
			}
		}
	}
	
	private void dateTime(String sentence) {
		String result = finder.findDateTime(sentence);
		if (result != null && !result.contains("P") && !result.contains("X") && !result.equals("TNI")) {
			
			if (dateTimeMap.containsKey(result)) {
				int value = dateTimeMap.get(result);
				value = value + 1;
				dateTimeMap.replace(result, value);
			} else {
				dateTimeMap.put(result, 1);
			}
		}
	}
	
	
	@Override
	protected String doInBackground() throws Exception {
		
		File file = null;
		MainWindow.stanfordSumText.setText("");
		String dataSet = MainWindow.dataSet.getSelectedItem().toString();
		int nepalCount = 186939;
		int naganoCount = 2291;
		int progressTotal = 0;
		
		if(dataSet == "Nepal") {
		    file = new File("data/nepal_etiketli.txt");
		    progressTotal = nepalCount;
		}else if(dataSet == "Nagano") {
			file = new File("data/nagano.txt");
			progressTotal = naganoCount;
		}
		
		MainWindow.progressStanford.setValue(0);
		int progressValue = 0;
		
		
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line;
			int i = 1;
		
			while ((line = br.readLine()) != null) {
				
				progressValue = progressValue + 1;
				
				if(progressValue == progressTotal/4) {
					MainWindow.progressStanford.setValue(20);
				} else if (progressValue == progressTotal/2) {
					MainWindow.progressStanford.setValue(50);
				} else if(progressValue == progressTotal*3/4) {
					MainWindow.progressStanford.setValue(75);
				}
				
				
				line = line.toLowerCase();
				i++;
				System.out.println(i);
				List<Word> sent = PTBTokenizer.factory().getTokenizer(new StringReader(line)).tokenize();
		        List<TaggedWord> taggedSent = tagger.tagSentence(sent);	
			    GrammaticalStructure gs = parser.predict(taggedSent);	    
			    Collection<TypedDependency> td = gs.typedDependencies();
			    
			    
			    this.injure(td);
			    this.kill(td);
			    this.die(td);
			    this.trap(td);
			    this.miss(td);
			    this.where(td);
			    this.what(td);
			    this.magnitude(td);
			    this.dateTime(line);
			    
			    
			}
			
			
			String what = findMaxFromMap(whatMap);
			String where = findMaxFromMap(whereMap);
			String die = findMaxFromMap(diedMap);
			String injure = findMaxFromMap(injureMap);
			String trap = findMaxFromMap(trapMap);
			//String kill = findMaxFromMap(killMap);
			String miss = findMaxFromMap(missMap);
			String magnitude = findMaxFromMap(magMap);
			String dateTime = findMaxFromMap(dateTimeMap);
			
			if (die == null) {
				die = "";
			} else {
				die = die + " people died, ";
			}
			
			if (injure == null) {
				injure = "";
			} else {
				injure = injure + " people injured, ";
			}
			
			if (trap == null) {
				trap = "";
			} else {
				trap = trap + " people trapped, ";
			}
			/*
			if (kill == null) {
				kill = "";
			} else {
				kill = kill + " people killed, ";
			}*/
			
			if (miss == null) {
				miss = "";
			} else {
				miss = miss + " people missing.";
			}
			
			if (magnitude == null) {
				magnitude = "";
			} else {
				magnitude = " with " + magnitude + " magnitude";
			}
			
			if (dateTime == null) {
				dateTime = "";
			}
			
			MainWindow.progressStanford.setValue(100);
			
			FindStanford.sum = "This is " + what.toUpperCase() + magnitude + " in " + where.toUpperCase() + " at " + dateTime + "\n" + die + injure + trap + miss;
			System.out.println(FindStanford.sum);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
		
		return FindStanford.sum;
	}
	
	
	
	
	
	
	
	@Override
	protected void done() {
		// TODO Auto-generated method stub
		MainWindow.stanfordSumText.setText(FindStanford.sum);
		super.done();
	}

}
