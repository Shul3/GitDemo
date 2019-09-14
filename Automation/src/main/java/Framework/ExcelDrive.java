package Framework;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ExcelDrive  {
	
	public  ArrayList<String> getData(String filePath, String sheetName, String columnName, String testCase) throws IOException {
		
		ArrayList<String> answer = new ArrayList<String>();
		
		//File input
		FileInputStream fil = new FileInputStream(filePath);
		
		//Create workbook- object
		XSSFWorkbook workbook = new XSSFWorkbook(fil);
		
		//Task_1 : Get TestData- sheet
		for(int i=0; i< workbook.getNumberOfSheets(); i++) {
			
			if(workbook.getSheetName(i).equalsIgnoreCase(sheetName)) {
				
				XSSFSheet sheet = workbook.getSheetAt(i);
				
			
			//Identify Testcases column by scanning the entire 1st row.
			//Once the column is identified then scan entire testcase column to identify Purchase-testcase row
			//After grab Purchase testcase row = pull all data of that row and feed into test
				
				//Task_1 : Identify Testcases column by scanning the entire 1st row.	
				Iterator<Row> rows = sheet.iterator(); 			//sheet is collection of rows
				Row firstrow = rows.next();
				Iterator<Cell> ce = firstrow.cellIterator();	//row is collection of cells
				int k=0, column =0;
				
				while(ce.hasNext()) {
					Cell value = ce.next();
					if(value.getStringCellValue().equalsIgnoreCase(columnName)) {
						//desired columns value
						column = k;
					}
					k++;
				}
				
				//System.out.println("==>>> "+ column);
				
				//Task_2 : Once the column is identified then scan entire testcase column to identify Purchase-testcase row
				while(rows.hasNext()) {
					
					Row r=rows.next();
					if(r.getCell(column).getStringCellValue().equalsIgnoreCase(testCase)) {
						
						//Task_3:After grab Purchase testcase row = pull all data of that row and feed into test
						Iterator<Cell> cd = r.cellIterator();
						Cell c = cd.next();
						while(cd.hasNext()) {
							c = cd.next();
							if(c.getCellTypeEnum() == CellType.STRING) {
								answer.add(c.getStringCellValue());
							}else if(c.getCellTypeEnum() == CellType.NUMERIC) {
								answer.add(NumberToTextConverter.toText(c.getNumericCellValue()));
							}else if(c.getCellTypeEnum() == CellType.BOOLEAN) {
								answer.add(Boolean.toString(c.getBooleanCellValue()));
							}else {
								answer.add(c.getStringCellValue());
							}
							
						}
						
					}
				}
			}
		}
		workbook.close();
		return answer;
	}

}
