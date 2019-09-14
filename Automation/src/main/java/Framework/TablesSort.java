package Framework;
import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

//DropDown Suggestive

public class TablesSort {

	public static void main(String[] args) throws InterruptedException {

		System.setProperty("webdriver.chrome.driver", "C:\\Utils\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.get("https://rahulshettyacademy.com/seleniumPractise/#/offers");

		// Double click on header of the second column to get asc- order
		// Take the second column of the tables
		// Get sorted array from the column from web page
		// Create array list then copy the array list to another array list and use
		// sort-method
		// desc- and asc- sort order, int- and str- data-type
		//By.cssSelector("tr th:nth-child(2)") -- for String column
		//By.cssSelector("tr th:nth-child(3)") -- for integer column
		
		driver.findElement(By.cssSelector("tr th:nth-child(2)")).click(); // desc order
		Thread.sleep(2000);
		driver.findElement(By.cssSelector("tr th:nth-child(2)")).click(); // asc order

		List<WebElement> webList = driver.findElements(By.cssSelector("tr td:nth-child(2)"));
		sortColumn(webList, "asc", "str");

	}

	public static void sortColumn(List<WebElement> colList, String order, String format) {
		// Method for columns sorting for String- eller Integer data type

		ArrayList<String> originalList = new ArrayList<String>();
		for (int i = 0; i < colList.size(); i++) {
			originalList.add(colList.get(i).getText());
		}

		ArrayList<String> sortlList = new ArrayList<String>();
		// sortlList = (ArrayList<String>) originalList.clone();
		for (int i = 0; i < originalList.size(); i++) {
			sortlList.add(originalList.get(i));
		}

		if (format.equalsIgnoreCase("int")) {
			Arrays.sort(sortlList.toArray()); // For integer data type
			if (order.equalsIgnoreCase("desc")) {
				Arrays.sort(sortlList.toArray(), Collections.reverseOrder()); // Sort int-array in desc order
			} 
		} else {
			Collections.sort(sortlList); // For string data type
			if (order.equalsIgnoreCase("desc")) {
				Collections.reverse(sortlList); // Sort arrayList in desc order
			} else {
				//Collections.sort(sortlList); // Sort arrayList in asc-order
			}
		}
		
		System.out.println("******** original *****");
		 for (String s : originalList) {
		 System.out.println(s);
		 }

		 System.out.println("******** sorted *****");
		 for (String s : sortlList) {
		 System.out.println(s);
		 }

		// Compare two arrays
		Assert.assertTrue(originalList.equals(sortlList)); // True because arrays have the same asc order.

	}

}
