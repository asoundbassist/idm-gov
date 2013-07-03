package function;

import java.util.ArrayList;

import javax.swing.JCheckBox;

/**
 * 
 * @author David Garner
 * @version 0.1
 * 
 * Search algorithm and supporting functions for search window program
 */

public class Search {
	
	private boolean hasResults;
	private String query;
	
	public Search(boolean hasResults, String query){
		this.hasResults = hasResults;
		this.query = query;
	}
	
	/**
	 * Takes a search query, parses it for whitespace and various operands
	 * and scours an SQL database, looking for relevant data
	 * 
	 * @param query 
	 * 		The unparsed search string
	 * @param isPartial
	 * 		States whether a partial or exact match was requested
	 * @param checkArr
	 * 		ArrayList of checkboxes to determine the type of query
	 * 			to be sent to the server
	 * 
	 * @return 
	 * 		??
	 */
	public static void search(String query, boolean isPartial, ArrayList<JCheckBox> checkArr,
			ArrayList<JCheckBox> searchTypeList, ArrayList<JCheckBox> objectTypeList){	//TODO determine return type (if any)
		//false if there is a single search criterion
		//true otherwise
		//TODO re-initialize boolean
		//boolean multiple = false;
		
		
		//If LOV value is selected, we look at the LOV value table
		if(checkArr.get(0).isSelected()) {
		} else{
			//GUID search type
			if(checkArr.get(1).isSelected()) {
			}
			//Name search type
			if(checkArr.get(2).isSelected()) {
			}
		}
		
		////////////////BEGIN PARSING AND PREPARATION FOR SEARCH////////////////
		
		query = query.trim();			//strip leading and trailing whitespace
		
		/*
		 * Temporarily disabled multiple search functionality
		 *
		 */
		//TODO Fix multiple search functionality
		if(query.toCharArray()[0] == '\"')
			return;
/*		if(query.toCharArray()[0] == '\"'){
			multiple = true;
			
			 * Create new arraylist and put all search terms between quotes in list
			 
			ArrayList<Character> newList = new ArrayList<Character>();
			ArrayList<String> tokenArr = new ArrayList<String>();
			ArrayList<String> newTokenArr = new ArrayList<String>();
			
			tokenArr = Util.parse1(query);
			
			
			 * Only perform the rest of the operation if more than one
			 * item was included in quotes
			 
			if(tokenArr.size() > 1){
				
				 * Add all characters to list
				 
				for(char c : query.toCharArray()) {
					newList.add(c);
				}
				
				
				 * copy list over to new list
				 
				ArrayList<Character> otherList = new ArrayList<Character>();
				for(int i = 0; i<newList.size(); i++){
					otherList.add(newList.get(i));
				}
				
				
				
				 * take out the first element (double quotation marks) and convert
				 * the resulting product into a string
				 
				newList.remove(0);
				String newQuery = Util.getStringRepresentation(newList);
				
				// We should now have an arraylist containing all of the operands
				newTokenArr = Util.parse1(newQuery);
				
				//Ignore leading and trailing whitespace
				for(String s : newTokenArr)
					s.trim();
				
				for(String s : tokenArr)
					s.trim();
				
				
				 * Confirm that all operands are spelled correctly.
				 
				for(String s : newTokenArr){
					if(!s.equalsIgnoreCase("and") && !s.equalsIgnoreCase("or")){
						InvalidOperandDialog badSearch = new InvalidOperandDialog();
						badSearch.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
						badSearch.setVisible(true);
						return;
					}
				}
				
				
				 * We should have n search terms and n-1 operands
				 * For example, 6 queries should have 5 operands
				 * Otherwise, throw an error window and abort the search
				 
				int ratioCheck = tokenArr.size() - newTokenArr.size();
				if(ratioCheck!=1) {
					InvalidOperandDialog badSearch = new InvalidOperandDialog();
					badSearch.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					badSearch.setVisible(true);
					return;
				}
			}
			////////////////END PARSING AND PREPARATION FOR ACTUAL SEARCH////////////////
			
		}*/
		
		if(isPartial){
			return;
			//TODO Partial search modifications
		}else{
			//TODO Exact search modifications
		}
		
	}
	
	////////////////Getters and Setters////////////////
	
	public String getQuery(){
		return this.query;
	}
	
	public void setQuery(String query){
		this.query = query;
	}
	
	public boolean getHasResults(){
		return this.hasResults;
	}
	
	public void setHasResults(boolean hasResults){
		this.hasResults = hasResults;
	}
	
////////////////End Getters and Setters////////////////
}