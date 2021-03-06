package function;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JFrame;

import notification.InvalidOperandDialog;

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
	 * @throws SQLException 
	 */
	public static ArrayList<ResultSet> search(String query, JCheckBox partial, 
			ArrayList<JCheckBox> searchTypeList, ArrayList<JCheckBox> objectTypeList, 
			Connection con, Statement stmt) throws SQLException{
		//false if there is a single search criterion
		//true otherwise
		boolean multiple = false;
		ArrayList<Character> newList = new ArrayList<Character>();
		ArrayList<String> operandArray = new ArrayList<String>();
		ArrayList<String> queryArray = new ArrayList<String>();
		
		////////////////BEGIN PARSING AND PREPARATION FOR SEARCH////////////////
		
		query = query.trim();			//strip leading and trailing whitespace
		
		if(query.toCharArray()[0] == '\"'){
			multiple = true;
			
			// Create new arraylist and put all search terms between quotes in list
			 
			queryArray = Util.parse(query);
			for(String s : queryArray)
				s = s.replaceAll("^\"|\"$", "");
			
			/*
			 * Only perform the rest of the operation if more than one
			 * item was included in quotes
			 */ 
			if(queryArray.size() > 1){
				
				 // Add all characters to list
				 
				for(char c : query.toCharArray()) {
					newList.add(c);
				}
				
				
				 // copy list over to new list
				 
				ArrayList<Character> otherList = new ArrayList<Character>();
				for(int i = 0; i<newList.size(); i++){
					otherList.add(newList.get(i));
				}
				
				
				/*
				 * take out the first element (double quotation marks) and convert
				 * the result to a string
				 */
				
				newList.remove(0);
				String newQuery = Util.getStringRepresentation(newList);
				
				// We should now have an arraylist containing all of the operands
				operandArray = Util.parse(newQuery);

				for(int i = 0 ; i < operandArray.size() ; i++){
					String j = operandArray.get(i);
					j = j.replaceAll("^\"|\"$", "");
					j = j.trim();
					operandArray.set(i, j);
				}	
				 
				for(int i = 0 ; i < queryArray.size() ; i++){
					String j = queryArray.get(i);
					j = j.replaceAll("^\"|\"$", "");
					j = j.trim();
					queryArray.set(i, j);
				}
				
				for(int i=0; i<operandArray.size(); i++){
				 // Confirm that all operands are spelled correctly.
					String s = operandArray.get(i);
					
					if(!s.equalsIgnoreCase("and") && !s.equalsIgnoreCase("or")){
						InvalidOperandDialog badSearch = new InvalidOperandDialog();
						badSearch.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
						badSearch.setVisible(true);
						return null;
					}
				}
				
				/*
				 * We should have n search terms and n-1 operands
				 * For example, 6 queries should have 5 operands
				 * Otherwise, throw an error window and abort the search
				 * 
				 * The one exception to this rule is if there is a single
				 * search query contained in quotes with no operands.
				 * This will be ignored and treated as a single search
				 * term.
				 */
		
				int ratioCheck = queryArray.size() - operandArray.size();
				if((ratioCheck!=1) || (queryArray.size() == 1 && operandArray.size() == 0)) {
					InvalidOperandDialog badSearch = new InvalidOperandDialog();
					badSearch.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					badSearch.setVisible(true);
					return null;
				}
			}
			////////////////END PARSING AND PREPARATION FOR ACTUAL SEARCH////////////////
			
		}
		
		Query q;
		ArrayList<ResultSet> result = new ArrayList<ResultSet>();
		
		
		if(multiple){
			q = new Query(objectTypeList, searchTypeList, 
					queryArray, operandArray, query);
			result = q.doQueryMultiple(con, stmt, partial);
		}
		else{
			q = new Query(objectTypeList, searchTypeList, query);
			result = q.doQuery(con, stmt, partial);
		}
		
		return result;
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