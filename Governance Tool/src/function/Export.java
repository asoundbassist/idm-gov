package function;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class Export{
	
	public void exportToXls(Connection con, ResultSet rs) throws SQLException{
		try{
			FileInputStream file = new FileInputStream(new File("C:\\test.xls"));
			
			HSSFWorkbook wb = new HSSFWorkbook(file);
			HSSFSheet sheet = wb.getSheetAt(0);
			
			Iterator<Row> rowIterator = sheet.iterator();
			while(rowIterator.hasNext()){
				Row row = rowIterator.next();
				
				Iterator<Cell> cellIterator = row.cellIterator();
				while(cellIterator.hasNext()){
					Cell cell = cellIterator.next();
					
					switch(cell.getCellType()){
						case Cell.CELL_TYPE_BOOLEAN:
							System.out.println(cell.getBooleanCellValue() + "\t\t");
							break;
						case Cell.CELL_TYPE_NUMERIC:
							System.out.println(cell.getNumericCellValue() + "\t\t");
							break;
						case Cell.CELL_TYPE_STRING:
							System.out.println(cell.getStringCellValue() + "\t\t");
							break;
					}
					
					System.out.println("");
				}
				
				file.close();
				FileOutputStream out =
						new FileOutputStream(new File("C:\\test.xls"));
				wb.write(out);
				out.close();
			}
			
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public static void RsToXls(){
		 HSSFWorkbook workbook = new HSSFWorkbook();
		 HSSFSheet sheet = workbook.createSheet("Test");
		 
		 Map<String, Object[]> data = new HashMap<String, Object[]>();
		 data.put("1", new Object[] {"Emp No.", "Name", "Salary"});
		 data.put("2", new Object[] {1d, "John", 1500000d});
		 data.put("3", new Object[] {2d, "Sam", 800000d});
		 data.put("4", new Object[] {3d, "Dean", 700000d});
		  
		 Set<String> keyset = data.keySet();
		 int rownum = 0;
		 for (String key : keyset) {
		     Row row = sheet.createRow(rownum++);
		     Object [] objArr = data.get(key);
		     int cellnum = 0;
		     for (Object obj : objArr) {
		         Cell cell = row.createCell(cellnum++);
		         if(obj instanceof Date)
		             cell.setCellValue((Date)obj);
		         else if(obj instanceof Boolean)
		             cell.setCellValue((Boolean)obj);
		         else if(obj instanceof String)
		             cell.setCellValue((String)obj);
		         else if(obj instanceof Double)
		             cell.setCellValue((Double)obj);
		     }
		 }
		 
		 try{
			 FileOutputStream out =
					 	new FileOutputStream(new File("C:\\test.xls"));
			 workbook.write(out);
			 out.close();
			 System.out.println("It worked!");
			 
		 }catch(FileNotFoundException e){
			 e.printStackTrace();
		 }catch(IOException e){
			 e.printStackTrace();
		 }
	}
	
	public static void main(String[] args){
		RsToXls();
	}
}
