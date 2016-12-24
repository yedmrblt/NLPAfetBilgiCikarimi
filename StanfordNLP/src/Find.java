import java.util.Collection;

import edu.stanford.nlp.trees.TypedDependency;


public class Find {
	
	private Lemmatizer lem = new Lemmatizer();
	
	
	
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
	
	
	private String findNumber(Collection<TypedDependency> td, String type) {
		
		String ret = null;
		
		for (TypedDependency d : td) {
	    	
	    	if (d.reln().toString() != "root") {
	    		
	    		if (lem.lemmatize(d.gov().word()).get(0).equals(type)){
	    			if (d.dep().tag().equals("CD")) {
	    				String number = find_compound(td, d.dep().word());
	    				if (number != null) {
	    					ret = number + " " + d.dep().word();
	    				} else {
	    					ret =  d.dep().word();
	    				}
	    				
	    				
	    			} else if (d.dep().tag().equals("NN") || d.dep().tag().equals("NNS")) {
	    				String number = find_NN_Nummod(td, d.dep().word());
	    				if (number != null) {
	    					ret = number;
	    					
	    				}
	    				
	    			} else if (d.dep().tag().equals("JJS")) {
	    				String number = find_NN_Nummod(td, d.dep().word());
	    				if (number != null) {
	    					ret = number;
	    					
	    				}
	    			} 
	    			
	    		} else if (lem.lemmatize(d.dep().word()).get(0).equals(type)) {
	    			
	    			if (d.gov().tag().equals("CD")) {
	    				String number = find_compound(td, d.gov().word());
	    				if (number != null) {
	    					ret = number + " " + d.gov().word();
	    				} else {
	    					ret =  d.gov().word();
	    				}
	    				
	    				
	    			} else if (d.gov().tag().equals("NN") || d.gov().tag().equals("NNS")) {
	    				String number = find_NN_Nummod(td, d.gov().word());
	    				if (number != null) {
	    					ret = number;
	    					
	    				}
	    			} else if (d.reln().toString().equals("xcomp")) {
	    				String number = find_xcom_NN(td, d.gov().word());
	    				if (number != null) {
	    					ret = number;
	    				}
	    			}
	    			
    			}
	    	}
	    }
		
		return ret;
		
	}
	
	private String find_compound(Collection<TypedDependency> td, String gov) {
		String ret = null;
		for (TypedDependency d : td) {
			if (d.reln().toString() != "root" && d.gov().word().equals(gov) && d.dep().tag().equals("CD")) {
				ret = d.dep().word();
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
