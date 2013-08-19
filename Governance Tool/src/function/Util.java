package function;

import java.io.File;
import java.lang.Boolean;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Vector;
import java.util.regex.Pattern;

import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import jxl.*;
import jxl.write.*;

import notification.InvalidLogin;

/**
 * 
 * @author David Garner
 * @version 0.1
 * 
 * A collection of various methods to be used across classes,
 * compiled here for neatness
 *
 */

public class Util {
	
	/**
	 * Looks at all the check boxes in a given array list to see if any are checked
	 * 
	 * @param arr 
	 * 		The array list
	 * @return 
	 * 		A boolean indicating whether at least one check box is checked
	 */
	public static Boolean isChecked(ArrayList<JCheckBox> arr){
		boolean x = false;
		int i = 0;
		while(x == false && i < arr.size()){
			if (arr.get(i).isSelected()==true)
				x = true;
			i++;
		}
		
		return x;
	}
	
	/**
	 * Checks an ArrayList of JCheckBoxes to determine which are selected
	 * 
	 * @param checkArr
	 * 		An ArrayList of JCheckBox items
	 * @return
	 * 		An ArrayList of Strings indicating which ones were checked
	 */
	public static ArrayList<String> getChecked(ArrayList<JCheckBox> checkArr){
		ArrayList<String> selected = new ArrayList<String>();
		
		for(JCheckBox i : checkArr)
			if(i.isSelected())
				selected.add(i.getText());
		
		return selected;
	}

	/**
	 * Converts an ArrayList containing Characters to a String
	 * @param list
	 * 		The list containing the chars to be condensed
	 * @return
	 * 		The complete contents of the original Character
	 * 		ArrayList in the form  of a String
	 */
	public static String getStringRepresentation(ArrayList<Character> list){
		StringBuilder builder = new StringBuilder(list.size());
		for(Character ch : list)
			builder.append(ch);
		
		return builder.toString();
	}
	
	/**
	 * Takes in a string and parses it according to items
	 * 		surrounded in double quotes
	 * @param query
	 * 		The string to be parsed
	 * @return
	 * 		An ArrayList of Strings that were found to be 
	 * 		surrounded in double quotes
	 */
	public static ArrayList<String> parse(String query){
		Scanner sc = new Scanner(query);
		Pattern pattern;
		//This is the pattern we will use
		pattern = Pattern.compile(
				"\"[^\"]*\"" +
				"|'[^']*+'"// +
				//"|[A-Za-z']+"
				);
		String token;
		
		//Create ArrayList for placing strings
		ArrayList<String> tokenArr = new ArrayList<String>();
		//Each time we find something surrounded in quotes,
		//We add it to the ArrayList
		while((token = sc.findInLine(pattern)) != null){
			token=token.trim();
			tokenArr.add(token);
		}
		
		sc.close();
		return tokenArr;
	}
	//TODO Export method
	
	public static DefaultTableModel buildTableModel(ArrayList<ResultSet> res, String searchType)
	        throws SQLException {

		 Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		 Vector<String> columnNames = new Vector<String>();
		 
		 for(ResultSet rs : res){
		    ResultSetMetaData metaData = rs.getMetaData();
	
		    // names of columns
		    
		    int columnCount = metaData.getColumnCount();
		    for (int column = 1; column <= columnCount; column++) {
		        columnNames.add(metaData.getColumnName(column));
		    }
	
		    // data of the table
		    while (rs.next()) {
		        Vector<Object> vector = new Vector<Object>();
		        for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
		            vector.add(rs.getObject(columnIndex));
		            //System.out.println(vector.toString());
		        }
		        data.add(vector);
		    }
		}
		    return new DefaultTableModel(data, columnNames);
	}
	
	public static Connection getConnection(Connection con, String url){
		try{
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			con = DriverManager.getConnection(url);
		}catch(Exception e){
            InvalidLogin invalid = new InvalidLogin();
            invalid.setVisible(true);
			e.printStackTrace();
		}
		
		return con;
	}
	
	public static void fillData(JTable table, File file) {

        try {

            WritableWorkbook workbook1 = Workbook.createWorkbook(file);
            WritableSheet sheet1 = workbook1.createSheet("First Sheet", 0);
            TableModel model = table.getModel();

            for (int i = 0; i < model.getColumnCount(); i++) {
                Label column = new Label(i, 0, model.getColumnName(i));
                sheet1.addCell(column);
            }
            int j = 0;
            for (int i = 0; i < model.getRowCount(); i++) {
                for (j = 0; j < model.getColumnCount(); j++) {
                    Label row = new Label(j, i + 1,
                            model.getValueAt(i, j).toString());
                    sheet1.addCell(row);
                }
            }
            workbook1.write();
            workbook1.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
