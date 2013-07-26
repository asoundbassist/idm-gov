package function;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Test {
    static Connection con = null;
    static Statement stmt = null;
    static ResultSet rs = null;
    
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Pattern pattern = Pattern.compile(
				"\"[^\"]*\"" +
				"|'[^']*'"// +
				//"|[A-Za-z']+"
				);
		String token;
		
		String test = "\"georgia tech\" and \"maryland\"";
		Scanner sc = new Scanner(test);
		while((token = sc.findInLine(pattern)) != null)
			;//System.out.println(token);
		
		String token_;
		Pattern pattern_ = Pattern.compile(
				"\"[^\"]*\"" +
				"|'[^']*'"// +
				//"|[A-Za-z']+"
				);
		String test_ = "georgia tech\" and \"maryland\"";
		Scanner sc_ = new Scanner(test_);
		while((token_ = sc_.findInLine(pattern_)) != null)
			System.out.println(token_);
			
		
		
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
            
            ResultSet rs_ = stmt.executeQuery(sql);
            while(rs_.next()){
            	String s = rs_.getString("AttributeName");
            	System.out.println(s);
            }
            
            ResultSet rs = stmt.executeQuery(sql);
            
            System.out.println(rs.next());
            
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