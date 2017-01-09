import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.SwingWorker;

public class FindValuableTweets extends SwingWorker<String, String>{
	
	public static Map<String, Double> tweetMap = new HashMap<String, Double>();
	public static String tweets;
	public static String fileName;
	
	
	public void TweetCosineRule(String line, String valuableWords) {
				
		
		CalculateCosineSimilarity calculateCosine = new CalculateCosineSimilarity();
		String cleartext = line.replaceAll("https?://\\S+\\s?", " ");
		Double cosineScor = calculateCosine.CalculateSimilarity(cleartext, valuableWords);
		
		Double formattedScor = Math.floor(cosineScor * 100);
		
		if (tweetMap.containsKey(cleartext)) {
			double value = tweetMap.get(cleartext);
			tweetMap.replace(cleartext, formattedScor);
		} else {
			tweetMap.put(cleartext, formattedScor);
		}

	}
	

	public void createDisasters() {
		FindStatic.disasters.add("hurricane");
		FindStatic.disasters.add("earthquake");
		FindStatic.disasters.add("tornado");
		FindStatic.disasters.add("volcanic");
		FindStatic.disasters.add("fire");
		FindStatic.disasters.add("tsunami");
		FindStatic.disasters.add("flood");
	}

	public void createMonths() {
		FindStatic.months.add("jan");
		FindStatic.months.add("feb");
		FindStatic.months.add("mar");
		FindStatic.months.add("apr");
		FindStatic.months.add("may");
		FindStatic.months.add("june");
		FindStatic.months.add("july");
		FindStatic.months.add("aug");
		FindStatic.months.add("sept");
		FindStatic.months.add("oct");
		FindStatic.months.add("nov");
		FindStatic.months.add("dec");
	}
	
	public Map.Entry<String, Double> findMostValuableTweet(Map<String, Double> map) {
		
		

		Map.Entry<String, Double> maxEntry = null;

		for (Map.Entry<String, Double> entry : map.entrySet()) {
			
			if(!Double.isNaN(entry.getValue())) {
				if (maxEntry == null || entry.getValue().compareTo(((Entry<String, Double>) maxEntry).getValue()) > 0) {
					maxEntry = entry;
				}
			}

		}

		return maxEntry;
		
	}


	@Override
	protected String doInBackground() throws Exception {
		// TODO Auto-generated method stub
		tweetMap.clear();
		createDisasters();
		createMonths();
		File file = null;
		String summarize = "";
		String dataSet = FindStatic.fileName.toString();
		String valuableWords = "die died death injure injured injuring people trap trapped destroyed rubble";

		MainWindow.sumText.setText("");
		int nepalCount = 186939;
		int naganoCount = 2291;
		int progressTotal = 0;
		
		if(dataSet.equals("Nagano")) {
			file = new File("data/nagano.txt");
			progressTotal = naganoCount;
		}else {
		    file = new File("data/nepal_etiketli.txt");
		    progressTotal = nepalCount;
		}
		
		MainWindow.progressCosine.setValue(0);
		int progressValue = 0;
		

		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line;
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
				String[] words = line.split(" ");
				ArrayList<String> wordList = new ArrayList<String>(Arrays.asList(words));

				TweetCosineRule(line, valuableWords);


			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String topTen = "";
		for(int i = 1; i< 11 ; i++ ) {
			Map.Entry<String, Double> maxObject = findMostValuableTweet(tweetMap);
			topTen = topTen + i + " =) " + maxObject.getKey().toString() + "\n";
			tweetMap.replace(maxObject.getKey(),0.0);
		}
		
		 MainWindow.progressCosine.setValue(100);
		 FindValuableTweets.tweets= topTen;
		
		return null;
	}

	@Override
	protected void done() {
		// TODO Auto-generated method stub
		MainWindow.cosineResult.setText(FindValuableTweets.tweets);
		MainWindow.cosineResult.repaint();
		super.done();
	}
	
}
