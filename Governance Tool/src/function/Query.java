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
	public ArrayList<ResultSet> doQuery(Connection con, Statement stmt) throws SQLException{
		
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
		if(lovVal.isSelected()){
			
				stmt = con.createStatement();
	            /*sql = "SELECT L.[LOVName], "
	            		+ "L.[LOVID], "
	            		+ "LVal.[Value] "
	            		+ "FROM [IDM_GOVERNANCE].dbo.[IDM_LOV_VALUES] LVal " +
	            		"INNER JOIN [IDM_GOVERNANCE].dbo.[IDM_LOV] L " +
	            		"ON LVal.[LOVStepID] = L.[StepID] " +
	            		"WHERE LVal.[Value] = \'" + getSearch() + "\'";*/
				
				sql = "SELECT DISTINCT B.LOVID, B.LOVName," +
						" C.Value, " +
						"A.AttributeName, " +
						"A.Attribute_GUID " +
						"FROM IDM_GOVERNANCE.dbo.IDM_LOV B " +
						"LEFT OUTER JOIN IDM_GOVERNANCE.dbo.IDM_LOV_VALUES C ON C.LOVStepID = B.StepID " +
						"LEFT OUTER JOIN IDM_GOVERNANCE.dbo.CURRENT_NODE_ATTRIBUTE_LOV_MAPPING A " +
						"ON B.StepID = A.LOVInternalID " +
						"WHERE C.Value = \'" + getSearch() + "\'";
	            
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
	            	/*sql = "SELECT MAPS.[AttributeName], " +
	            			"MAPS.[Attribute_GUID], " +
	            			"MAPS.[SubNodeType], " +
	            			"MAPS.[CollectionPath] " +
	            			"FROM [IDM_GOVERNANCE].[dbo].[20130328_NODE_ATTRIBUTE_MAPS] MAPS " +
	            			"WHERE MAPS.[AttributeName] = \'" + getSearch() + "\'";*/
	            }
	            
	            //TODO display group, help text, and names/guids where attribute is locally linked/inherited
	            else if(box.equals(attribute) && attribute.isSelected()){
	            	/*sql = "SELECT A.[AttributeName], " +
	            			"A.[AttributeID], " +
	            			"A.[validator], " +
	            			"B.[ATTR_DESC] " +
	            			"FROM [IDM_GOVERNANCE].[dbo].[ATTR_LIST] B " +
	            			"INNER JOIN [IDM_GOVERNANCE].[dbo].[IDM_ATTRIBUTES] A " +
	            			"ON A.[AttributeID] = B.[ATTR_GUID] " +
	            			"WHERE A.[AttributeName] = \'" + getSearch() + "\'";*/
	            	
/*	            	sql = "SELECT * " +
	            				"FROM [IDM_GOVERNANCE].[dbo].[CURRENT_NODE_ATTRIBUTE_LOV_MAPPING] A LEFT OUTER JOIN " +
	            					"OPENQUERY(DPR23MMS_SRO01,' " +
	            			"select CAST(a.nodeid AS VARCHAR(40)) as AttributeInternalID, " +
	            					"b.name as AttributeGUID, " +
	            					"stepview.pimviewapipck.getcontextname(a.nodeid) AttributeName, " +
							       "CAST(a.attributeid AS VARCHAR(40)) as MetadataAttributeInternalID, " +
							       "d.name as MetadataAttributeGUID, " +
							       "stepview.pimviewapipck.getcontextname(a.attributeid) as MetadataAttributeName, " +
							       "CAST(a.valueid AS VARCHAR(40)) as MetadataValueInternalID, " +
							       "c.value as MetadataValue " +
							  "from stepview.valuelink_all a, " +
							       "stepview.node_all b, " +
							       "stepview.value_all c, " +
							       "stepview.attribute_all d " +
							"where b.id = a.nodeid " +
							   "and a.nodetype = ''a'' " +
							   "and c.id = a.valueid " +
							   "and d.id = a.attributeid " +
							   "and d.name in (''27715ad5-b32c-46de-9819-a6f4d2916ef9'') " +
							   "and b.name in (''d7ca3d28-e515-4451-b07e-908194762d66'')') B ON B.AttributeInternalID = A.AttributeInternalID " +
							"INNER JOIN [IDM_GOVERNANCE].[dbo].[IDM_ATTRIBUTEGROUPLINK_V] C ON C.ATTRIBUTEID = B.ATTRIBUTEINTERNALID " +
							"INNER JOIN [IDM_GOVERNANCE].[dbo].[IDM_ATTRIBUTEGROUP_V] D ON D.ID = C.ATTRIBUTEGROUPID " +
							"ORDER BY A.ATTRIBUTENAME, D.NAME";*/
	            	
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
	            			"WHERE A.AttributeName = '" + getSearch() + "'";

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
	            	
/*	            	sql = "SELECT LowestLevelNodeInternalID, " +
	            			"CollectionPath, " +
	            			"SubNodeType, " +
	            			"COUNT(DISTINCT OMSID) AS OMSID_COUNT " +
	            			"FROM " +
	            			"(SELECT DISTINCT B.LowestLevelNodeInternalID, B.CollectionPath, A.OMSID, B.SubNodeType " +
	            			"FROM IDM_GOVERNANCE.dbo.ITEM_NODE_MAPPINGS_APPROVED A, " +
			            		"IDM_GOVERNANCE.dbo.CURRENT_NODE_ATTRIBUTE_LOV_MAPPING B, " +
			            	    "IDM_GOVERNANCE.dbo.IDM_COLLECTION_NODE D " +
			            	 "WHERE B.SubNodeGUID = A.PARENT_ID " +
			            	   "AND LEN(A.PARENT_ID) <> 9 " +
			            	   "AND B.SubNodeType <> 'SuperSKUz' " +
			            	   "AND D.StepID = B.LowestLevelNodeInternalID " +
			            	   "AND D.NodeID IN ('a6ef05b1-06d5-46b6-9b7e-83740662a177','1a8837bf-1084-4747-adfa-7fd34108182d') " +
			            	"UNION " +
			            	"SELECT DISTINCT B.LowestLevelNodeInternalID, B.CollectionPath, A.OMSID, B.SubNodeType " +
			            	  "FROM IDM_GOVERNANCE.dbo.ITEM_NODE_MAPPINGS_APPROVED A, " +
			            	       "IDM_GOVERNANCE.dbo.CURRENT_NODE_ATTRIBUTE_LOV_MAPPING B, " +
			            	       "IDM_GOVERNANCE.dbo.IDM_COLLECTION_NODE D " +
			            	 "WHERE B.SubNodeGUID = A.SSKU_PARENT_ID " +
			            	   "AND B.SubNodeType <> 'SuperSKU' " +
			            	   "AND D.StepID = B.LowestLevelNodeInternalID " +
			            	   "AND D.NodeID IN ('a6ef05b1-06d5-46b6-9b7e-83740662a177','1a8837bf-1084-4747-adfa-7fd34108182d') " +
			            	   ") S " +
			            	"GROUP BY LowestLevelNodeInternalID, CollectionPath, SubNodeType";*/
	            	
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
	            			"WHERE A.NodeId = '" + getSearch() + "'";
	            }
	            
	            //TODO display group, help text, and names/guids where attribute is locally linked/inherited
	            if(box.equals(attribute) && attribute.isSelected()){
	            	/*sql = "SELECT A.[AttributeName], " +
	            			"A.[AttributeID], " +
	            			"A.[validator], " +
	            			"B.[ATTR_DESC] " +
	            			"FROM [IDM_GOVERNANCE].[dbo].[ATTR_LIST] B " +
	            			"INNER JOIN [IDM_GOVERNANCE].[dbo].[IDM_ATTRIBUTES] A " +
	            			"ON A.[AttributeID] = B.[ATTR_GUID] " +
	            			"WHERE A.[AttributeID] = " + getSearch();*/
	            	
	            	sql = "SELECT [AttributeName], " +
	            			"[Attribute_GUID], " +
	            			"[CollectionPath], " +
	            			"[validator] " +
	            			"FROM [IDM_GOVERNANCE].[dbo].[CURRENT_NODE_ATTRIBUTE_LOV_MAPPING] " +
	            			"WHERE [Attribute_GUID] = \'" + getSearch() + "\'";
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
	public ArrayList<ResultSet> doQueryMultiple(Connection con, Statement stmt) throws SQLException{
		
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
		if(lovVal.isSelected()){
			
				stmt = con.createStatement();
	            /*sql = "SELECT L.[LOVName], "
	            		+ "L.[LOVID], "
	            		+ "LVal.[Value] "
	            		+ "FROM [IDM_GOVERNANCE].dbo.[IDM_LOV_VALUES] LVal " +
	            		"INNER JOIN [IDM_GOVERNANCE].dbo.[IDM_LOV] L " +
	            		"ON LVal.[LOVStepID] = L.[StepID] " +
	            		"WHERE LVal.[Value] = \'" + getSearch() + "\'";*/
				
				sql = "SELECT DISTINCT B.LOVID, B.LOVName," +
						" C.Value, " +
						"A.AttributeName, " +
						"A.Attribute_GUID " +
						"FROM IDM_GOVERNANCE.dbo.IDM_LOV B " +
						"LEFT OUTER JOIN IDM_GOVERNANCE.dbo.IDM_LOV_VALUES C ON C.LOVStepID = B.StepID " +
						"LEFT OUTER JOIN IDM_GOVERNANCE.dbo.CURRENT_NODE_ATTRIBUTE_LOV_MAPPING A " +
						"ON B.StepID = A.LOVInternalID " +
						"WHERE C.Value = \'" + searchQueryList.get(0) + "\' ";
				
				for(int i=1; i<searchQueryList.size(); i++){
					String sqlMod = operandList.get(i-1) + " C.Value = \'" + searchQueryList.get(i) + "\'";
					sql += sqlMod;
				}
				
				sql += " ORDER BY C.Value";
				
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
	            	/*sql = "SELECT MAPS.[AttributeName], " +
	            			"MAPS.[Attribute_GUID], " +
	            			"MAPS.[SubNodeType], " +
	            			"MAPS.[CollectionPath] " +
	            			"FROM [IDM_GOVERNANCE].[dbo].[20130328_NODE_ATTRIBUTE_MAPS] MAPS " +
	            			"WHERE MAPS.[AttributeName] = \'" + getSearch() + "\'";*/
	            }
	            
	            //TODO display group, help text, and names/guids where attribute is locally linked/inherited
	            else if(box.equals(attribute) && attribute.isSelected()){
	            	/*sql = "SELECT A.[AttributeName], " +
	            			"A.[AttributeID], " +
	            			"A.[validator], " +
	            			"B.[ATTR_DESC] " +
	            			"FROM [IDM_GOVERNANCE].[dbo].[ATTR_LIST] B " +
	            			"INNER JOIN [IDM_GOVERNANCE].[dbo].[IDM_ATTRIBUTES] A " +
	            			"ON A.[AttributeID] = B.[ATTR_GUID] " +
	            			"WHERE A.[AttributeName] = \'" + getSearch() + "\'";*/
	            	
/*	            	sql = "SELECT * " +
	            				"FROM [IDM_GOVERNANCE].[dbo].[CURRENT_NODE_ATTRIBUTE_LOV_MAPPING] A LEFT OUTER JOIN " +
	            					"OPENQUERY(DPR23MMS_SRO01,' " +
	            			"select CAST(a.nodeid AS VARCHAR(40)) as AttributeInternalID, " +
	            					"b.name as AttributeGUID, " +
	            					"stepview.pimviewapipck.getcontextname(a.nodeid) AttributeName, " +
							       "CAST(a.attributeid AS VARCHAR(40)) as MetadataAttributeInternalID, " +
							       "d.name as MetadataAttributeGUID, " +
							       "stepview.pimviewapipck.getcontextname(a.attributeid) as MetadataAttributeName, " +
							       "CAST(a.valueid AS VARCHAR(40)) as MetadataValueInternalID, " +
							       "c.value as MetadataValue " +
							  "from stepview.valuelink_all a, " +
							       "stepview.node_all b, " +
							       "stepview.value_all c, " +
							       "stepview.attribute_all d " +
							"where b.id = a.nodeid " +
							   "and a.nodetype = ''a'' " +
							   "and c.id = a.valueid " +
							   "and d.id = a.attributeid " +
							   "and d.name in (''27715ad5-b32c-46de-9819-a6f4d2916ef9'') " +
							   "and b.name in (''d7ca3d28-e515-4451-b07e-908194762d66'')') B ON B.AttributeInternalID = A.AttributeInternalID " +
							"INNER JOIN [IDM_GOVERNANCE].[dbo].[IDM_ATTRIBUTEGROUPLINK_V] C ON C.ATTRIBUTEID = B.ATTRIBUTEINTERNALID " +
							"INNER JOIN [IDM_GOVERNANCE].[dbo].[IDM_ATTRIBUTEGROUP_V] D ON D.ID = C.ATTRIBUTEGROUPID " +
							"ORDER BY A.ATTRIBUTENAME, D.NAME";*/
	            	
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
					
					sql += " ORDER BY A.AttributeName";
	            	
	            	for(int i=0; i<operandList.size()-1; i++){
						String sqlMod = operandList.get(i) + "A.AttributeName = \'" + getSearch() + "\'";
						sql.concat(sqlMod);
					}

	            }
	            
	            else if(box.equals(attribGroup) && attribGroup.isSelected()){
	            	sql = "SELECT G.[NAME], " +
	            			"L.[ATTRIBUTEGROUPID] " +
	            			"FROM [IDM_GOVERNANCE].dbo.[IDM_ATTRIBUTEGROUP_V] G " +
	            			"INNER JOIN [IDM_GOVERNANCE].dbo.[IDM_ATTRIBUTEGROUPLINK_V] L " +
	            			"ON G.[ID] = L.[ATTRIBUTEID] " +
	            			"WHERE G.[NAME] = \'" + getSearch() + "\'";
	            	
	            	for(int i=0; i<operandList.size()-1; i++){
						String sqlMod = operandList.get(i) + "G.[NAME] = \'" + getSearch() + "\'";
						sql.concat(sqlMod);
					}
	            	

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
	            	
/*	            	sql = "SELECT LowestLevelNodeInternalID, " +
	            			"CollectionPath, " +
	            			"SubNodeType, " +
	            			"COUNT(DISTINCT OMSID) AS OMSID_COUNT " +
	            			"FROM " +
	            			"(SELECT DISTINCT B.LowestLevelNodeInternalID, B.CollectionPath, A.OMSID, B.SubNodeType " +
	            			"FROM IDM_GOVERNANCE.dbo.ITEM_NODE_MAPPINGS_APPROVED A, " +
			            		"IDM_GOVERNANCE.dbo.CURRENT_NODE_ATTRIBUTE_LOV_MAPPING B, " +
			            	    "IDM_GOVERNANCE.dbo.IDM_COLLECTION_NODE D " +
			            	 "WHERE B.SubNodeGUID = A.PARENT_ID " +
			            	   "AND LEN(A.PARENT_ID) <> 9 " +
			            	   "AND B.SubNodeType <> 'SuperSKUz' " +
			            	   "AND D.StepID = B.LowestLevelNodeInternalID " +
			            	   "AND D.NodeID IN ('a6ef05b1-06d5-46b6-9b7e-83740662a177','1a8837bf-1084-4747-adfa-7fd34108182d') " +
			            	"UNION " +
			            	"SELECT DISTINCT B.LowestLevelNodeInternalID, B.CollectionPath, A.OMSID, B.SubNodeType " +
			            	  "FROM IDM_GOVERNANCE.dbo.ITEM_NODE_MAPPINGS_APPROVED A, " +
			            	       "IDM_GOVERNANCE.dbo.CURRENT_NODE_ATTRIBUTE_LOV_MAPPING B, " +
			            	       "IDM_GOVERNANCE.dbo.IDM_COLLECTION_NODE D " +
			            	 "WHERE B.SubNodeGUID = A.SSKU_PARENT_ID " +
			            	   "AND B.SubNodeType <> 'SuperSKU' " +
			            	   "AND D.StepID = B.LowestLevelNodeInternalID " +
			            	   "AND D.NodeID IN ('a6ef05b1-06d5-46b6-9b7e-83740662a177','1a8837bf-1084-4747-adfa-7fd34108182d') " +
			            	   ") S " +
			            	"GROUP BY LowestLevelNodeInternalID, CollectionPath, SubNodeType";*/
	            	
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
	            	/*sql = "SELECT A.[AttributeName], " +
	            			"A.[AttributeID], " +
	            			"A.[validator], " +
	            			"B.[ATTR_DESC] " +
	            			"FROM [IDM_GOVERNANCE].[dbo].[ATTR_LIST] B " +
	            			"INNER JOIN [IDM_GOVERNANCE].[dbo].[IDM_ATTRIBUTES] A " +
	            			"ON A.[AttributeID] = B.[ATTR_GUID] " +
	            			"WHERE A.[AttributeID] = " + getSearch();*/
	            	
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
