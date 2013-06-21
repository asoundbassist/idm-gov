package function;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JCheckBox;

public class Query {
	private ArrayList<JCheckBox> objectTypeList = new ArrayList<JCheckBox>();
	private ArrayList<JCheckBox> searchTypeList = new ArrayList<JCheckBox>();
	private ArrayList<String> searchQueryList = new ArrayList<String>();
	private ArrayList<String> operandList = new ArrayList<String>();
	private String search;
	
	/**
	 * Constructor for a query object.
	 * 
	 * @param objectTypeList
	 * 		A list of selected object types indicated by the user
	 * @param searchTypeList
	 * 		A list of selected search types indicated by the user
	 * @param searchQuerylist
	 * 		A list of search queries indicated by the user in the search box
	 * @param operandList
	 * 		A list of operands to dictate the nature of the search. This parameter is optional.
	 */
	public Query(ArrayList<JCheckBox> objectTypeList, ArrayList<JCheckBox> searchTypeList,
					ArrayList<String> searchQueryList, ArrayList<String> operandList,
					String search){
		this.objectTypeList = objectTypeList;
		this.searchTypeList = searchTypeList;
		this.searchQueryList = searchQueryList;
		this.operandList = operandList;
		this.search = search;
	}
	
	
	/**
	 * Alternate constructor for a query object.
	 * 
	 * @param objectTypeList
	 * 		A list of selected object types indicated by the user
	 * @param searchTypeList
	 * 		A list of selected search types indicated by the user
	 * @param searchQuerylist
	 * 		A list of search queries indicated by the user in the search box
	 */
	public Query(ArrayList<JCheckBox> objectTypeList, ArrayList<JCheckBox> searchTypeList,
			String search){
		this.objectTypeList = objectTypeList;
		this.searchTypeList = searchTypeList;
		this.search = search;
	}
	
	/**
	 * Performs a set of SQL queries and collects the result sets in an ArrayList
	 * @param con
	 * 		The connection to the database
	 * @param stmt
	 * 		The statement by which the query is to be executed
	 * @return
	 * 		An ArrayList of ResultSets containing all the data queried from the database
	 */
	public ArrayList<ResultSet> doQuery(Connection con, Statement stmt){
		ArrayList<ResultSet> resultList = new ArrayList<ResultSet>();
		ResultSet rs = null;
		
		JCheckBox lovVal = getSearchTypeList().get(0);
		JCheckBox guid = getSearchTypeList().get(1);
		JCheckBox name = getSearchTypeList().get(2);
		
		ArrayList<JCheckBox> searchList = new ArrayList<JCheckBox>();
		searchList.add(guid);
		searchList.add(name);
		
		JCheckBox colNode = getObjectTypeList().get(0);
		JCheckBox attribute = getObjectTypeList().get(1);
		JCheckBox attribGroup = getObjectTypeList().get(3);
		JCheckBox viewGroup = getObjectTypeList().get(4);
		
		ArrayList<JCheckBox> objectList = new ArrayList<JCheckBox>();
		objectList.add(colNode);
		objectList.add(attribute);
		objectList.add(attribGroup);
		objectList.add(viewGroup);
		
        String sql = "";
        
		/*
		 * Function for LOV Value search type
		 */
        try{
		if(lovVal.isSelected()){
			
				stmt = con.createStatement();
	            sql = "SELECT L.[LOVName], "
	            		+ "L.[LOVID], "
	            		+ "LVal.[Value] "
	            		+ "FROM [IDM_GOVERNANCE].dbo.[IDM_LOV_VALUES] LVal " +
	            		"INNER JOIN [IDM_GOVERNANCE].dbo.[IDM_LOV] L " +
	            		"ON LVal.[LOVStepID] = L.[StepID] " +
	            		"WHERE LVal.[Value] = \'" + getSearch() + "\'";
	            
	            rs = stmt.executeQuery(sql);
	            
	            if(rs != null){
	            	resultList.add(rs);
	            }else ;
	        
		}

		if(name.isSelected()){
			for(JCheckBox box : objectList){	
	        	stmt = con.createStatement();
	            //TODO # sub nodes, parent node name/GUID
	            if(box.equals(colNode) && colNode.isSelected()){
	            	sql = "SELECT MAPS.[AttributeName], " +
	            			"MAPS.[Attribute_GUID], " +
	            			"MAPS.[SubNodeType], " +
	            			"MAPS.[CollectionPath] " +
	            			"FROM [IDM_GOVERNANCE].[dbo].[20130328_NODE_ATTRIBUTE_MAPS] MAPS " +
	            			"WHERE MAPS.[AttributeName] = \'" + getSearch() + "\'";
	            }
	            
	            //TODO display group, help text, and names/guids where attribute is locally linked/inherited
	            else if(box.equals(attribute) && attribute.isSelected()){
	            	sql = "SELECT A.[AttributeName], " +
	            			"A.[AttributeID], " +
	            			"A.[validator], " +
	            			"B.[ATTR_DESC] " +
	            			"FROM [IDM_GOVERNANCE].[dbo].[ATTR_LIST] B " +
	            			"INNER JOIN [IDM_GOVERNANCE].[dbo].[IDM_ATTRIBUTES] A " +
	            			"ON A.[AttributeID] = B.[ATTR_GUID] " +
	            			"WHERE A.[AttributeName] = \'" + getSearch() + "\'";
	            }
	            
	            else if(box.equals(attribGroup) && attribGroup.isSelected()){
	            	sql = "SELECT G.[NAME], " +
	            			"L.[ATTRIBUTEGROUPID] " +
	            			"FROM [IDM_GOVERNANCE].dbo.[IDM_ATTRIBUTEGROUP_V] G " +
	            			"INNER JOIN [IDM_GOVERNANCE].dbo.[IDM_ATTRIBUTEGROUPLINK_V] L " +
	            			"ON G.[ID] = L.[ATTRIBUTEID] " +
	            			"WHERE G.[NAME] = \'" + getSearch() + "\'";
	            }
	            
	            else if(box.equals(viewGroup) && viewGroup.isSelected()){
	            	sql = "SELECT";
	            }
	            
	            if(sql == "")
	            	continue;
	            else{
		            rs = stmt.executeQuery(sql);
		            resultList.add(rs);
		            continue;
	            }
			}
		}
        
        if(guid.isSelected()){
        	for(JCheckBox box : objectList){
	            //TODO # sub nodes, parent node name/GUID
	            if(box.equals(colNode) && colNode.isSelected()){
	            	sql = "SELECT MAPS.[AttributeName], " +
	            			"MAPS.[Attribute_GUID], " +
	            			"MAPS.[SubNodeType], " +
	            			"MAPS.[CollectionPath] " +
	            			"FROM [IDM_GOVERNANCE].[dbo].[20130328_NODE_ATTRIBUTE_MAPS] MAPS " +
	            			"WHERE MAPS.[Attribute_GUID] = " + getSearch();
	            }
	            
	            //TODO display group, help text, and names/guids where attribute is locally linked/inherited
	            if(box.equals(attribute) && attribute.isSelected()){
	            	sql = "SELECT A.[AttributeName], " +
	            			"A.[AttributeID], " +
	            			"A.[validator], " +
	            			"B.[ATTR_DESC] " +
	            			"FROM [IDM_GOVERNANCE].[dbo].[ATTR_LIST] B " +
	            			"INNER JOIN [IDM_GOVERNANCE].[dbo].[IDM_ATTRIBUTES] A " +
	            			"ON A.[AttributeID] = B.[ATTR_GUID] " +
	            			"WHERE A.[AttributeID] = " + getSearch();
	            }
	            
	            if(box.equals(attribGroup) && attribGroup.isSelected()){
	            	sql = "SELECT G.[NAME], " +
	            			"L.[ATTRIBUTEGROUPID] " +
	            			"FROM [IDM_GOVERNANCE].dbo.[IDM_ATTRIBUTEGROUP_V] G " +
	            			"INNER JOIN [IDM_GOVERNANCE].dbo.[IDM_ATTRIBUTEGROUPLINK_V] L " +
	            			"ON G.[ID] = L.[ATTRIBUTEID] " +
	            			"WHERE L.[ATTRIBUTEGROUPID] = " + getSearch();
	            }
	            
	            //TODO Find out what exactly a view group is
	            if(box.equals(viewGroup) && viewGroup.isSelected()){
	            	sql = "SELECT";
	            }
	            
	            rs = stmt.executeQuery(sql);
	            if(rs != null){
	            	resultList.add(rs);
	            }else ;
        	}
        }

        } catch(SQLException e) {
		e.printStackTrace();
	}
		
		return resultList;
	}
	
	/**
	 * Cleans up the program on closing
	 * @param con
	 * 		The database connection established during runtime
	 * @param stmt
	 * 		Any relevant statements or queries sent to the database
	 */
	public static void closeConnection(Connection con, Statement stmt){
		// Connection, statement, and result cleanup
		if(con != null){
			try{
				con.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		if(stmt != null){
			try{
				stmt.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	public ArrayList<JCheckBox> getObjectTypeList(){
		return this.objectTypeList;
	}
	
	public ArrayList<JCheckBox> getSearchTypeList(){
		return this.searchTypeList;
	}
	
	public ArrayList<String> getsearchQueryList(){
		return this.searchQueryList;
	}
	
	public ArrayList<String> getOperandList(){
		return this.operandList;
	}
	
	public String getSearch(){
		return this.search;
	}
}
