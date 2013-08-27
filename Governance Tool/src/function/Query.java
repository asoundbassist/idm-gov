package function;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

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
	 * @throws SQLException 
	 * @throws IOException 
	 */
	public ArrayList<ResultSet> doQuery(Connection con, Statement stmt, JCheckBox partial) throws SQLException{
		
		if(search.equalsIgnoreCase("Justin Bieber") || search.equalsIgnoreCase("Justin Bieber Duct Tape")
				|| search.equalsIgnoreCase("Justin Bieber Duck Tape")){
			BufferedImage image;
			try {
				image = ImageIO.read(new File("src/notification/jBeebz.png"));
				JLabel picLabel = new JLabel(new ImageIcon(image));
				JOptionPane.showMessageDialog(null, picLabel, "Baby, baby, baby, OOOOOHHHH!", JOptionPane.PLAIN_MESSAGE, null);
			} catch (IOException e) {
				e.printStackTrace();
			}
		
				return null;
		}
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
        String operator = "";
        String like = "";
        
        if(partial.isSelected()){
        	operator = "LIKE";
        	like = "%";
        }else
        	operator = "=";
		if(lovVal.isSelected()){
			
				stmt = con.createStatement();
				
				sql = "SELECT DISTINCT B.LOVID, B.LOVName," +
						" C.Value, " +
						"A.AttributeName, " +
						"A.Attribute_GUID " +
						"FROM IDM_GOVERNANCE.dbo.IDM_LOV B " +
						"LEFT OUTER JOIN IDM_GOVERNANCE.dbo.IDM_LOV_VALUES C ON C.LOVStepID = B.StepID " +
						"LEFT OUTER JOIN IDM_GOVERNANCE.dbo.CURRENT_NODE_ATTRIBUTE_LOV_MAPPING A " +
						"ON B.StepID = A.LOVInternalID " +
						"WHERE C.Value " + operator + " " + like + " \'" + getSearch() + "\'" + like +
						" ORDER BY A.Attribute_GUID";
	            
	            rs = stmt.executeQuery(sql);
	            
	            if(sql == "")
	            	;
	            else{
		            rs = stmt.executeQuery(sql);
		            resultList.add(rs);
		            sql = "";
	            }
	        
		}

		if(name.isSelected()){
			for(JCheckBox box : objectList){	
	        	stmt = con.createStatement();
	            //TODO # sub nodes, parent node name/GUID
	            if(box.equals(colNode) && colNode.isSelected()){
	            	
	            	sql = "SELECT A.CollectionPath, " +
	            			"A.NodeId, " +
	            			"A.SubNodeType, " +
	            			"A.OmsidCount, " +
	            			"B.NodeName, " +
	            			"C.AttributeName, " +
	            			"C.Attribute_GUID " +
	            			"FROM IDM_GOVERNANCE.dbo.IDM_COLLECTION_NODE_ITEM_COUNTS A " +
	            			"INNER JOIN IDM_GOVERNANCE.dbo.IDM_COLLECTION_NODE B " +
	            				"ON A.LowestLevelNodeInternalID = B.StepID " +
	            				"JOIN IDM_GOVERNANCE.dbo.CURRENT_NODE_ATTRIBUTE_LOV_MAPPING C " +
	            				"ON C.CollectionPath = A.CollectionPath " +
	            			"WHERE B.NodeName " + operator + " " + like + " '" + getSearch() + "'" + like +
	            			" ORDER BY A.NodeId";
	            }
	            
	            //TODO display group, help text, and names/guids where attribute is locally linked/inherited
	            else if(box.equals(attribute) && attribute.isSelected()){
	            	
	            	sql = "SELECT " +
	            				"A.AttributeName, " +
	            				"A.Attribute_GUID, " +
	            				"B.NodeName, " +
	            				"C.NodeID " +
	            			"FROM IDM_GOVERNANCE.dbo.CURRENT_NODE_ATTRIBUTE_LOV_MAPPING A " +
	            			"INNER JOIN IDM_GOVERNANCE.dbo.IDM_COLLECTION_NODE_ITEM_COUNTS C " +
	            				"ON A.CollectionPath = C.CollectionPath " +
	            			"JOIN IDM_GOVERNANCE.dbo.IDM_COLLECTION_NODE B " +
	            				"ON A.LowestLevelNodeInternalID = B.StepID " +
	            			"WHERE A.AttributeName " + operator + " " + like + " '" + getSearch() + "'" + like +
	            			" ORDER BY A.Attribute_GUID";

	            }
	            
	            else if(box.equals(attribGroup) && attribGroup.isSelected()){
	            	sql = "SELECT G.[NAME], " +
	            			"L.[ATTRIBUTEGROUPID] " +
	            			"FROM [IDM_GOVERNANCE].dbo.[IDM_ATTRIBUTEGROUP_V] G " +
	            			"INNER JOIN [IDM_GOVERNANCE].dbo.[IDM_ATTRIBUTEGROUPLINK_V] L " +
	            			"ON G.[ID] = L.[ATTRIBUTEID] " +
	            			"WHERE G.[NAME] " + operator + " " + like + " \'" + getSearch() + "\'" + like +
	            			" ORDER BY L.[ATTRIBUTEGROUPID]";
	            	

	            }
	            
	            else if(box.equals(viewGroup) && viewGroup.isSelected()){
	            	sql = "SELECT";
	            }
	            
	            if(sql == "")
	            	continue;
	            else{
		            rs = stmt.executeQuery(sql);
		            resultList.add(rs);
		            sql = "";
		            continue;
	            }
			}
		}
        
        if(guid.isSelected()){
        	for(JCheckBox box : objectList){
        		stmt = con.createStatement();
	            //TODO # sub nodes, parent node name/GUID
	            if(box.equals(colNode) && colNode.isSelected()){
	            	
	            	sql = "SELECT A.CollectionPath, " +
	            			"A.NodeId, " +
	            			"A.SubNodeType, " +
	            			"A.OmsidCount, " +
	            			"B.NodeName, " +
	            			"C.AttributeName, " +
	            			"C.Attribute_GUID " +
	            			"FROM IDM_GOVERNANCE.dbo.IDM_COLLECTION_NODE_ITEM_COUNTS A " +
	            			"INNER JOIN IDM_GOVERNANCE.dbo.IDM_COLLECTION_NODE B " +
	            				"ON A.LowestLevelNodeInternalID = B.StepID " +
	            				"JOIN IDM_GOVERNANCE.dbo.CURRENT_NODE_ATTRIBUTE_LOV_MAPPING C " +
	            				"ON C.CollectionPath = A.CollectionPath " +
	            			"WHERE A.NodeId " + operator + " '" + getSearch() + "'" + like +
	            			" ORDER BY A.NodeId";
	            }
	            
	            //TODO display group, help text, and names/guids where attribute is locally linked/inherited
	            if(box.equals(attribute) && attribute.isSelected()){
	            	
	            	sql = "SELECT [AttributeName], " +
	            			"[Attribute_GUID], " +
	            			"[CollectionPath], " +
	            			"[validator] " +
	            			"FROM [IDM_GOVERNANCE].[dbo].[CURRENT_NODE_ATTRIBUTE_LOV_MAPPING] " +
	            			"WHERE [Attribute_GUID] " + operator + " \'" + getSearch() + "\' " +
	            			"ORDER BY [Attribute_GUID]";
	            }
	            
	            if(box.equals(attribGroup) && attribGroup.isSelected()){
	            	sql = "SELECT G.[NAME], " +
	            			"L.[ATTRIBUTEGROUPID] " +
	            			"FROM [IDM_GOVERNANCE].dbo.[IDM_ATTRIBUTEGROUP_V] G " +
	            			"INNER JOIN [IDM_GOVERNANCE].dbo.[IDM_ATTRIBUTEGROUPLINK_V] L " +
	            			"ON G.[ID] = L.[ATTRIBUTEID] " +
	            			"WHERE L.[ATTRIBUTEGROUPID] " + operator + " " + getSearch() + " " + like +
	            			" ORDER BY L.[ATTRIBUTEGROUPID]";
	            }
	            
	            //TODO Find out what exactly a view group is
	            if(box.equals(viewGroup) && viewGroup.isSelected()){
	            	sql = "SELECT";
	            }
	            
	            if(sql == "")
	            	continue;
	            else{
		            rs = stmt.executeQuery(sql);
		            resultList.add(rs);
		            sql = "";
		            continue;
	            }
        	}
        }

        } catch(SQLException e) {
        	e.printStackTrace();
        }
		
		return resultList;
	}
	
	/**
	 * Performs a set of SQL queries using a set of AND or OR operands 
	 * and collects the result sets in an ArrayList.
	 * @param con
	 * 		The connection to the database
	 * @param stmt
	 * 		The statement by which the query is to be executed
	 * @return
	 * 		An ArrayList of ResultSets containing all the data queried from the database
	 * @throws SQLException 
	 * @throws IOException 
	 */
	public ArrayList<ResultSet> doQueryMultiple(Connection con, Statement stmt, JCheckBox partial) throws SQLException{
		
		if(search.equalsIgnoreCase("Justin Bieber") || search.equalsIgnoreCase("Justin Bieber Duct Tape")
				|| search.equalsIgnoreCase("Justin Bieber Duck Tape")){
			BufferedImage image;
			try {
				image = ImageIO.read(new File("src/notification/jBeebz.png"));
				JLabel picLabel = new JLabel(new ImageIcon(image));
				JOptionPane.showMessageDialog(null, picLabel, "Baby, baby, baby, OOOOOHHHH!", JOptionPane.PLAIN_MESSAGE, null);
			} catch (IOException e) {
				e.printStackTrace();
			}
		
				return null;
		}
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
        	
        String operator = "";
        String like = "%";
        
        if(partial.isSelected())
        	operator = "LIKE";
        else
        	operator = "=";
		if(lovVal.isSelected()){
			
				stmt = con.createStatement();
				
				sql = "SELECT DISTINCT B.LOVID, " +
						"B.LOVName," +
						" C.Value, " +
						"A.AttributeName, " +
						"A.Attribute_GUID " +
						"FROM IDM_GOVERNANCE.dbo.IDM_LOV B " +
						"INNER JOIN IDM_GOVERNANCE.dbo.IDM_LOV_VALUES C ON C.LOVStepID = B.StepID " + //Left outer join
						"INNER JOIN IDM_GOVERNANCE.dbo.CURRENT_NODE_ATTRIBUTE_LOV_MAPPING A " + //Left outer join
						"ON B.StepID = A.LOVInternalID " +
						"WHERE C.Value = \'" + searchQueryList.get(0) + "\'";
				
				for(int i=1; i<searchQueryList.size(); i++){
					String sqlMod = operandList.get(i-1) + " C.Value = \'" + searchQueryList.get(i) + "\'";
					sql += sqlMod;
				}
				
				sql += " ORDER BY B.LOVID";
				
	            rs = stmt.executeQuery(sql);
	            
	            if(sql == "")
	            	;
	            else{
		            rs = stmt.executeQuery(sql);
		            
		            sql = "";
		            resultList.add(rs);
		            sql = "";
	            }
	        
		}

		if(name.isSelected()){
			for(JCheckBox box : objectList){	
	        	stmt = con.createStatement();
	            //TODO # sub nodes, parent node name/GUID
	            if(box.equals(colNode) && colNode.isSelected()){
	            	
	            	sql = "SELECT A.CollectionPath, " +
	            			"A.NodeId, " +
	            			"A.SubNodeType, " +
	            			"A.OmsidCount, " +
	            			"B.NodeName, " +
	            			"C.AttributeName, " +
	            			"C.Attribute_GUID " +
	            			"FROM IDM_GOVERNANCE.dbo.IDM_COLLECTION_NODE_ITEM_COUNTS A " +
	            			"INNER JOIN IDM_GOVERNANCE.dbo.IDM_COLLECTION_NODE B " +
	            				"ON A.LowestLevelNodeInternalID = B.StepID " +
	            				"JOIN IDM_GOVERNANCE.dbo.CURRENT_NODE_ATTRIBUTE_LOV_MAPPING C " +
	            				"ON C.CollectionPath = A.CollectionPath " +
	            			"WHERE B.NodeName = '" + searchQueryList.get(0) + "'";
	            	
	            	for(int i=1; i<searchQueryList.size(); i++){
						String sqlMod = operandList.get(i-1) + " A.NodeId = \'" + searchQueryList.get(i) + "\'";
						sql += sqlMod;
					}
	            	
	            	sql += " ORDER BY A.NodeId";
	            	
	            }
	            
	            //TODO display group, help text, and names/guids where attribute is locally linked/inherited
	            else if(box.equals(attribute) && attribute.isSelected()){
	            	
	            	sql = "SELECT " +
	            				"A.AttributeName, " +
	            				"A.Attribute_GUID, " +
	            				"B.NodeName, " +
	            				"C.NodeID " +
	            			"FROM IDM_GOVERNANCE.dbo.CURRENT_NODE_ATTRIBUTE_LOV_MAPPING A " +
	            			"INNER JOIN IDM_GOVERNANCE.dbo.IDM_COLLECTION_NODE_ITEM_COUNTS C " +
	            				"ON A.CollectionPath = C.CollectionPath " +
	            			"JOIN IDM_GOVERNANCE.dbo.IDM_COLLECTION_NODE B " +
	            				"ON A.LowestLevelNodeInternalID = B.StepID " +
	            			"WHERE A.AttributeName = '" + searchQueryList.get(0) + "'";
	            	
					for(int i=1; i<searchQueryList.size(); i++){
						String sqlMod = operandList.get(i-1) + " A.AttributeName = \'" + searchQueryList.get(i) + "\'";
						sql += sqlMod;
					}
					
					sql += " ORDER BY A.Attribute_GUID";
	            	
	            	for(int i=1; i<searchQueryList.size(); i++){
						String sqlMod = operandList.get(i-1) + " A.AttributeName = \'" + searchQueryList.get(i) + "\'";
						sql += sqlMod;
					}

	            }
	            
	            else if(box.equals(attribGroup) && attribGroup.isSelected()){
	            	sql = "SELECT G.[NAME], " +
	            			"L.[ATTRIBUTEGROUPID] " +
	            			"FROM [IDM_GOVERNANCE].dbo.[IDM_ATTRIBUTEGROUP_V] G " +
	            			"INNER JOIN [IDM_GOVERNANCE].dbo.[IDM_ATTRIBUTEGROUPLINK_V] L " +
	            			"ON G.[ID] = L.[ATTRIBUTEID] " +
	            			"WHERE G.[NAME] = \'" + getSearch() + "\'";
	            	
	            	for(int i=1; i<searchQueryList.size(); i++){
						String sqlMod = operandList.get(i-1) + " G.[NAME] = \'" + searchQueryList.get(i) + "\'";
						sql += sqlMod;
					}
	            	
	            	sql += "ORDER BY L.[ATTRIBUTEGROUPID]";

	            }
	            
	            else if(box.equals(viewGroup) && viewGroup.isSelected()){
	            	sql = "SELECT";
	            }
	            
	            if(sql == "")
	            	continue;
	            else{
		            rs = stmt.executeQuery(sql);
		            resultList.add(rs);
		            sql = "";
		            continue;
	            }
			}
		}
        
        if(guid.isSelected()){
        	for(JCheckBox box : objectList){
        		stmt = con.createStatement();
	            //TODO # sub nodes, parent node name/GUID
	            if(box.equals(colNode) && colNode.isSelected()){
	            	
	            	sql = "SELECT A.CollectionPath, " +
	            			"A.NodeId, " +
	            			"A.SubNodeType, " +
	            			"A.OmsidCount, " +
	            			"B.NodeName, " +
	            			"C.AttributeName, " +
	            			"C.Attribute_GUID " +
	            			"FROM IDM_GOVERNANCE.dbo.IDM_COLLECTION_NODE_ITEM_COUNTS A " +
	            			"INNER JOIN IDM_GOVERNANCE.dbo.IDM_COLLECTION_NODE B " +
	            				"ON A.LowestLevelNodeInternalID = B.StepID " +
	            				"JOIN IDM_GOVERNANCE.dbo.CURRENT_NODE_ATTRIBUTE_LOV_MAPPING C " +
	            				"ON C.CollectionPath = A.CollectionPath " +
	            			"WHERE A.NodeId = '" + searchQueryList.get(0) + "'";
	            	
					for(int i=1; i<searchQueryList.size(); i++){
						String sqlMod = operandList.get(i-1) + " A.NodeId = \'" + searchQueryList.get(i) + "\'";
						sql += sqlMod;
					}
					
					sql += " ORDER BY A.NodeId";
	            }
	            
	            //TODO display group, help text, and names/guids where attribute is locally linked/inherited
	            if(box.equals(attribute) && attribute.isSelected()){
	            	
	            	sql = "SELECT [AttributeName], " +
	            			"[Attribute_GUID], " +
	            			"[CollectionPath], " +
	            			"[validator] " +
	            			"FROM [IDM_GOVERNANCE].[dbo].[CURRENT_NODE_ATTRIBUTE_LOV_MAPPING] " +
	            			"WHERE [Attribute_GUID] = \'" + searchQueryList.get(0) + "\'";
	            	
					for(int i=1; i<searchQueryList.size(); i++){
						String sqlMod = operandList.get(i-1) + " [Attribute_GUID] = \'" + searchQueryList.get(i) + "\'";
						sql += sqlMod;
					}
					
					sql += " ORDER BY [ATTRIBUTE_GUID]";
	            }
	            
	            if(box.equals(attribGroup) && attribGroup.isSelected()){
	            	sql = "SELECT G.[NAME], " +
	            			"L.[ATTRIBUTEGROUPID] " +
	            			"FROM [IDM_GOVERNANCE].dbo.[IDM_ATTRIBUTEGROUP_V] G " +
	            			"INNER JOIN [IDM_GOVERNANCE].dbo.[IDM_ATTRIBUTEGROUPLINK_V] L " +
	            			"ON G.[ID] = L.[ATTRIBUTEID] " +
	            			"WHERE L.[ATTRIBUTEGROUPID] = " + searchQueryList.get(0);
	            	
					for(int i=1; i<searchQueryList.size(); i++){
						String sqlMod = operandList.get(i-1) + " L.[ATTRIBUTEGROUPID] = \'" + searchQueryList.get(i) + "\'";
						sql += sqlMod;
					}
					
					sql += " ORDER BY L.[ATTRIBUTEGROUPID]";
	            }
	            
	            if(box.equals(viewGroup) && viewGroup.isSelected()){
	            	sql = "SELECT";
	            }
	            
	            if(sql == "")
	            	continue;
	            else{
		            rs = stmt.executeQuery(sql);
		            resultList.add(rs);
		            sql = "";
		            continue;
	            }
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
