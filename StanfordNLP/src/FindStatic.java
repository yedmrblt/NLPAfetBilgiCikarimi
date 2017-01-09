import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.SwingWorker;

public class FindStatic  extends SwingWorker<String, String>{

	public static ArrayList<String> disasters = new ArrayList<>();
	public static ArrayList<String> months = new ArrayList<>();
	public static Map<String, Integer> whenMap = new HashMap<String, Integer>();
	public static Map<String, Integer> whereMap = new HashMap<String, Integer>();
	public static Map<String, Integer> whatMap = new HashMap<String, Integer>();
	public static Map<String, Integer> diedMap = new HashMap<String, Integer>();
	public static Map<String, Integer> injureMap = new HashMap<String, Integer>();
	public static Map<String, Integer> trapMap = new HashMap<String, Integer>();
	public static Map<String, Integer> yearMap = new HashMap<String, Integer>();
	public static Map<String, Integer> dayMap = new HashMap<String, Integer>();
	public static String sum;
	protected static String fileName;




	public String findMaxFromMap(Map<String, Integer> map) {

		Map.Entry<String, Integer> maxEntry = null;

		for (Map.Entry<String, Integer> entry : map.entrySet()) {

			if (maxEntry == null || entry.getValue().compareTo(((Entry<String, Integer>) maxEntry).getValue()) > 0) {
				maxEntry = entry;
			}
		}

		return maxEntry.getKey();
	}
	
	public void clearMaps() {
		
		whenMap.clear();
		whereMap.clear();
		whatMap.clear();
		diedMap.clear();
		injureMap.clear();
		trapMap.clear();
		yearMap.clear();
		dayMap.clear();
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

	public void dateRules(ArrayList<String> wordList) {

		for (String item : months) {
			if (wordList.contains(item)) {
				if (whenMap.containsKey(item)) {
					int value = whenMap.get(item);
					value = value + 1;
					whenMap.replace(item, value);
				} else {
					whenMap.put(item, 1);
				}

				int index = wordList.indexOf(item);
				int dayIndex = index - 1;
				int yearIndex = index + 1;

				if ((dayIndex > 0) && (wordList.get(dayIndex).matches(".*\\d+.*"))) {
					String day = wordList.get(dayIndex);
					if (dayMap.containsKey(day)) {
						int value = dayMap.get(day);
						value = value + 1;
						dayMap.replace(day, value);
					} else {
						dayMap.put(day, 1);
					}
				}

				if ((wordList.size() > yearIndex)) {
					if ((wordList.get(yearIndex).matches(".*\\d{4}.*"))) {
						String year = wordList.get(yearIndex);
						if (yearMap.containsKey(year)) {
							int value = yearMap.get(year);
							value = value + 1;
							yearMap.replace(year, value);
						} else {
							yearMap.put(year, 1);
						}
					}
				}

			}

		}

	}

	public void disasterRule(ArrayList<String> wordList) {
		for (String item : disasters) {
			if (wordList.contains(item)) {
				if (whatMap.containsKey(item)) {
					int value = whatMap.get(item);
					value = value + 1;
					whatMap.replace(item, value);
				} else {
					whatMap.put(item, 1);
				}
			}
		}
	}

	public void whereRule(ArrayList<String> wordList) {
		if (wordList.contains("in")) {
			int inIndex = wordList.indexOf("in");
			inIndex = inIndex + 1;
			if (wordList.size() > inIndex) {

				String where = wordList.get(inIndex);
				if (whereMap.containsKey(where)) {
					int value = whereMap.get(where);
					value = value + 1;
					whereMap.replace(where, value);
				} else {
					whereMap.put(where, 1);
				}
			}
		}
	}

	public void deadRule(String line) {
				
		 String[] sentences = line.split("(?<=[.!?:])\\s* ");
		    for (String s : sentences) {
		        if(s.contains("die")) {
		        	String cleartext = s.replaceAll("https?://\\S+\\s?", " ");
					if (diedMap.containsKey(cleartext)) {
						int value = diedMap.get(cleartext);
						value = value + 1;
						diedMap.replace(cleartext, value);
					} else {
						diedMap.put(cleartext, 1);
					}
		        }
		    }
	}

	public void injureRule(String line) {
		
		 String[] sentences = line.split("(?<=[.!?:])\\s* ");
		    for (String s : sentences) {
		        if(s.contains("injure")) {
		        	String cleartext = s.replaceAll("https?://\\S+\\s?", " ");
					if (injureMap.containsKey(cleartext)) {
						int value = injureMap.get(cleartext);
						value = value + 1;
						injureMap.replace(cleartext, value);
					} else {
						injureMap.put(cleartext, 1);
					}
		        }
		    }
	}
	
	public void trapRule(String line) {
		
		 String[] sentences = line.split("(?<=[.!?:])\\s* ");
		    for (String s : sentences) {
		        if(s.contains("trap")) {
		        	String cleartext = s.replaceAll("https?://\\S+\\s?", " ");
					if (trapMap.containsKey(cleartext)) {
						int value = trapMap.get(cleartext);
						value = value + 1;
						trapMap.replace(cleartext, value);
					} else {
						trapMap.put(cleartext, 1);
					}
		        }
		    }
	}

	@Override
	protected String doInBackground() throws Exception {

		clearMaps();
		createDisasters();
		createMonths();
		File file = null;
		int nepalCount = 186939;
		int naganoCount = 2291;
		int progressTotal = 0;

		String dataSet = MainWindow.dataSet.getSelectedItem().toString();
		if(dataSet == "Nepal") {
		    file = new File("data/nepal_etiketli.txt");
		    progressTotal = nepalCount;
		}else if(dataSet == "Nagano") {
			file = new File("data/nagano.txt");	
			progressTotal = naganoCount;
		}
		
		

		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line;
			MainWindow.progressFreq.setValue(0);
			int progressValue = 0;
			
			
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

				disasterRule(wordList);
				dateRules(wordList);
				whereRule(wordList);
				deadRule(line);
				injureRule(line);
				trapRule(line);

			}

			String what = findMaxFromMap(whatMap);
			String where = findMaxFromMap(whereMap);
			String when = findMaxFromMap(whenMap);
			String day = findMaxFromMap(dayMap);
			String year = findMaxFromMap(yearMap);
			String die = findMaxFromMap(diedMap);
			String injure = findMaxFromMap(injureMap);
			String trap = findMaxFromMap(trapMap);
			
			MainWindow.progressFreq.setValue(100);
			FindStatic.sum = "This is " + what.toUpperCase() + " in " + where.toUpperCase() + " at " + day + "/"
					+ when.toUpperCase() + "/" + year + "\n" + die + "\n" + injure + "\n" + trap;


		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return sum;
	}

	@Override
	protected void done() {
		// TODO Auto-generated method stub
		MainWindow.sumText.setText(FindStatic.sum);
		super.done();
	}
	
	

}
