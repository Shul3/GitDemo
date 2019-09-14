package REST;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;
import Framework.ExcelDrive;
import Norwegian.CheckLinks;

public class RestDemo {
	ExcelDrive eObj = new ExcelDrive();
	ArrayList<String> resultRow = new ArrayList<String>();
	
	private static Logger log = LogManager.getLogger(RestDemo.class.getName());

	@Test(priority=1)
	public void ReadDataFromFile() throws IOException {

		// Read the row of data from Excel file where: filePath, sheetName, columnName,
		// testCase name from column name
		resultRow = eObj.getData(System.getProperty("user.dir") + "\\src\\main\\resources\\workbook.xlsx", "testdata", "testcases", "post");
		
	}

	@Test(dependsOnMethods="ReadDataFromFile")
	public void PrintData() {

		System.out.println("==>>>> " + resultRow.size());
		System.out.println("==>>>> " + resultRow.get(0));
		System.out.println("==>>>> " + resultRow.get(1));
		System.out.println("==>>>> " + resultRow.get(2));
		System.out.println("==>>>> " + resultRow.get(3));
		
	}

}
