package ui;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class ResultSetTable extends JTable{
 
  private final DefaultTableModel dataModel;
 
  public ResultSetTable(ArrayList<ResultSet> res)
                       throws SQLException{
 
    super();
    dataModel = new DefaultTableModel();
    setModel(dataModel);
    
	for(ResultSet rs : res){  
	    try {
	      //create an array of column names
	      ResultSetMetaData mdata = rs.getMetaData();
	      int colCount = mdata.getColumnCount();
	      String[] colNames = new String[colCount];
	      for (int i = 1; i <= colCount; i++) {
	        colNames[i - 1] = mdata.getColumnName(i);
	      }
	      dataModel.setColumnIdentifiers(colNames);
	 
	      //now populate the data
	      while (rs.next()) {
	        String[] rowData = new String[colCount];
	        for (int i = 1; i <= colCount; i++) {
	          rowData[i - 1] = rs.getString(i);
	        }
	        dataModel.addRow(rowData);
	      }
	    }
	    finally{
	      try {
	        rs.close();
	      }
	      catch (SQLException ignore) {
	      }
	    }
	}
  }
}
