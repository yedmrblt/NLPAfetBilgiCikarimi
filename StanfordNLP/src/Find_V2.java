import java.util.ArrayList;
import java.util.Collection;

import edu.stanford.nlp.trees.TypedDependency;


public class Find_V2 {
	
	private Lemmatizer lem = new Lemmatizer();
	private StanfordDateTime sdt = new StanfordDateTime();
	
	
	public String findInjure(Collection<TypedDependency> td) {
		return findNumber(td, "injure");
	}
	
	public String findDie(Collection<TypedDependency> td) {
		return findNumber(td, "die");
	}
	
	public String findTrap(Collection<TypedDependency> td) {
		return findNumber(td, "trap");
	} 
	
	public String findKill(Collection<TypedDependency> td) {
		return findNumber(td, "kill");
	}
	
	public String findMiss(Collection<TypedDependency> td) {
		return findNumber(td, "miss");
	}
	
	public String findMagnitude(Collection<TypedDependency> td) {
		return find_Magnitude(td);
	}
	
	public String findDateTime(String sentence) {
		return sdt.findDate(sentence);
	}
		
	public String findLocation(Collection<TypedDependency> td) {
		String ret = null;
		
		for (TypedDependency d : td) {
			
			if (d.reln().toString() == "case" && d.dep().word().equals("in") && d.gov().tag().equals("NNP")) {
				ret = d.gov().word();
				
			}
			
			
		}
		
		return ret;
	}
	
	public String findDisaster(Collection<TypedDependency> td) {
		String ret = null;
		ArrayList<String> disasters = new ArrayList<>();
		disasters.add("hurricane");
		disasters.add("earthquake");
		disasters.add("tornado");
		disasters.add("volcanic");
		disasters.add("fire");
		disasters.add("tsunami");
		disasters.add("flood");
		
		for (TypedDependency d  : td) {
			
			if (disasters.contains(d.gov().word())) {
				ret = d.gov().word();
			} else if (disasters.contains(d.dep().word())) {
				ret = d.dep().word();
			}
			
		}
		
		return ret;
		
	}
	
	public String find_Magnitude(Collection<TypedDependency> td) {
		
		String ret = null;
		
		for (TypedDependency d : td) {
			
			if (d.reln().toString() != "root") {
				
				if (d.gov().word().equals("earthquake") && d.dep().tag().equals("CD")) {
					ret = d.dep().word();
					break;
				}
				
			}
			
		}
		
		return ret;
		
	}
	
	private String findNumber(Collection<TypedDependency> td, String type) {
		
		String ret = null;
		
		for (TypedDependency d : td) {
	    	
	    	if (d.reln().toString() != "root") {
	    		
	    		if (lem.lemmatize(d.gov().word()).get(0).equals(type)){
	    			if (d.dep().tag().equals("CD")) {
	    				
	    				String modifier = find_modifier(td, d.dep().word());
	    				if (modifier != null) {
	    					ret = modifier + " " + d.dep().word();
	    				} else {
	    					ret = d.dep().word();
	    				}

	    				
	    				
	    			} else if (d.dep().tag().equals("NN") || d.dep().tag().equals("NNS")) {
	    				
	    				String number = find_NN_Nummod(td, d.dep().word());
	    				if (number != null) {
	    					
	    					String modifier = find_modifier(td, number);
	    					
		    				if (modifier != null) {
		    					ret = modifier + " " + number;
		    				} else {
		    					ret = number;
		    				}

	    				}
	    				
	    			} else if (d.dep().tag().equals("JJS")) {
	    				String number = find_NN_Nummod(td, d.dep().word());
	    				if (number != null) {
	    					
	    					String modifier = find_modifier(td, number);
		    				if (modifier != null) {
		    					ret = modifier + " " + number;
		    				} else {
		    					ret =  number;
		    					
		    				}
	    				}
	    			} 
	    			
	    		} else if (lem.lemmatize(d.dep().word()).get(0).equals(type)) {
	    			
	    			if (d.gov().tag().equals("CD")) {
	    				
	    				//System.out.println("no");
	    				String modifier = find_modifier(td, d.gov().word());
	    				if (modifier != null) {
	    					ret = modifier + " " + d.gov().word();
	    				} else {
	    					ret = d.gov().word();
	    				}
	    				
	    				
	    				
	    			} else if (d.gov().tag().equals("NN") || d.gov().tag().equals("NNS")) {
	    				String number = find_NN_Nummod(td, d.gov().word());
	    				if (number != null) {
	    					
	    					String modifier = find_modifier(td, number);
		    				if (modifier != null) {
		    					ret = modifier + " " + number;
		    				} else {
		    					ret =  number;
		    					
		    				}
	    					
	    				}
	    			} else if (d.reln().toString().equals("xcomp")) {
	    				String number = find_xcom_NN(td, d.gov().word());
	    				if (number != null) {
	    					String modifier = find_modifier(td, number);
		    				if (modifier != null) {
		    					ret = modifier + " " + number;
		    				} else {
		    					ret =  number;
		    				}
	    				}
	    			}
	    			
    			}
	    	}
	    }
		
		return ret;
		
	}
	
	
	private String find_modifier(Collection<TypedDependency> td, String number) {
		String result = null;
		String advmod = find_advmod(td, number);
		if (advmod != null) {
			
			result = advmod + " ";
			
		} else {
			
			String nmod_npmod = find_nmod_npmod(td, number);
			if (nmod_npmod != null) {
				
				result = nmod_npmod + " ";
				
			} else {
				
				String nummod = find_nummod(td, number);
				if (nummod != null) {
					
					result = nummod + " ";
					
				}
				
				
			}
			
		}

		return result;

		
		
	}
	
	
	
	
	private String find_nmod_npmod(Collection<TypedDependency> td, String number) {
		
		String ret = null;
		for (TypedDependency d : td) {
			if (d.reln().toString() == "nmod:npmod" && d.gov().word().equals(number)) {
				String _case = find_case(td, d.dep().word());
				if (_case != null) {
					ret = _case + " " + d.dep().word();
				}
			}
		}
		return ret;
		
		
	}
	
	
	private String find_case(Collection<TypedDependency> td, String nmod_npmod_dep) {
		
		String ret = null;
		for (TypedDependency d : td) {
			if (d.reln().toString() == "case" && d.gov().word().equals(nmod_npmod_dep)) {
				ret = d.dep().word();
			}
		}
		return ret;
		
		
	}
	
	
	private String find_advmod(Collection<TypedDependency> td, String number) {
		
		String ret = null;
		for (TypedDependency d : td) {
			if (d.reln().toString() == "advmod" && d.gov().word().equals(number)) {
				String mwe = find_mwe(td, d.dep().word());
				if (mwe != null) {
					ret = d.dep().word() + " " + mwe;
				}
			}
		}
		return ret;
		
		
	}
	
	private String find_mwe(Collection<TypedDependency> td, String advmod_dep) {
		
		String ret = null;
		for (TypedDependency d : td) {
			if (d.reln().toString() == "mwe" && d.gov().word().equals(advmod_dep)) {
				ret = d.dep().word();
			}
		}
		return ret;
		
		
	}
	
	private String find_nummod(Collection<TypedDependency> td, String number) {
		
		String ret = null;
		for (TypedDependency d : td) {
			if (d.reln().toString() == "nummod" && d.dep().word().equals(number)) {
				String _case = find_case(td, d.gov().word());
				if (_case != null) {
					ret = _case + " " + d.gov().word();
					
				}
			}
		}
		return ret;
		
		
	}
	

	private String find_xcom_NN(Collection<TypedDependency> td, String gov) {
		String ret = null;
		for (TypedDependency d : td) {
			if (d.reln().toString() != "root" && d.gov().word().equals(gov) && (d.dep().tag().equals("NN") || d.dep().tag().equals("NNS"))) {
				ret = find_NN_Nummod(td, d.dep().word());
			} else if (d.reln().toString() != "root" && d.dep().word().equals(gov) && d.gov().tag().equals("CD")) {
				ret = d.gov().word();
			}
		}
		return ret;
	}
	
	private String find_NN_Nummod(Collection<TypedDependency> td, String gov) {
		String ret = null;
		for (TypedDependency d : td) {
			if (d.reln().toString().equals("nummod") && d.gov().word().equals(gov)) {
				ret = d.dep().word();
			}
		}
		
		return ret;
		
	} 
	
	
}
