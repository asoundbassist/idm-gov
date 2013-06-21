package function;

import java.sql.*;

public class Test {
    static Connection con = null;
    static Statement stmt = null;
    static ResultSet rs = null;
    
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String connectionUrl="jdbc:sqlserver://CPPCPWCV62W7C:1433;integratedSecurity=true;";
		
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection(connectionUrl);
            System.out.println("Connected");
            
            System.out.println("Creating statement...");
            stmt = con.createStatement();
            String sql;
            sql = "SELECT L.[LOVName], "
            		+ "L.[LOVID], "
            		+ "LVal.[Value] "
            		+ "FROM [IDM_GOVERNANCE].dbo.[IDM_LOV_VALUES] LVal " +
            		"INNER JOIN [IDM_GOVERNANCE].dbo.[IDM_LOV] L " +
            		"ON LVal.[LOVStepID] = L.[StepID] " +
            		"WHERE LVal.[Value] = 'Deluxe'";
            
            ResultSet rs = stmt.executeQuery(sql);
            
            while(rs.next()){
            	String s = rs.getString("LOVName");
            	System.out.println(s);
            }
            
            rs.close();
            stmt.close();
            con.close();
            
        }catch(Exception e){
        	e.printStackTrace();
        }

	}

}