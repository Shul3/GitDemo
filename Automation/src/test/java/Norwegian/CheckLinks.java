package Norwegian;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import Framework.ExcelDrive;
import Framework.TestBase;

public class CheckLinks {

	// Test Case: Count all links on the web page, footer of the page and the sub footer section.
	// Check all links in sub footer section "Planera och boka flygresa": open alla links och kolla titles
	//Expected titles:
	// Billiga flyg och goda erbjudanden på flygresor | Norwegian,
	// Presentkort hos Norwegian | Norwegian Reward,
	// Norwegian Reward - Norwegians gratis lojalitetsprogram,
	// Platsreservation | Norwegian,
	// Våra biljettyper | Norwegian,
	// Lågpriskalender: Hitta billigaste flyg | Norwegian,
	// Ruttkarta | Norwegian,
	// Flygresor från Stockholm-Arlanda | Alla destinationer | Norwegian]

	WebDriver driver;
	WebElement footer, subFooter;
	int numLinks = 0;
	Properties prop = new Properties();
	ArrayList<String> titleList = new ArrayList<String>();
	ArrayList<String> originList = new ArrayList<String>();
	
	ExcelDrive eObj = new ExcelDrive();
	TestBase obj = new TestBase();
	public static Logger log = LogManager.getLogger(CheckLinks.class.getName());
	
	@BeforeClass(groups = "Smoke")
	public void testBeforeSuite() throws IOException {
			
		driver = obj.getWebDriver("\\norwegian.properties"); //Call the reusable method
		log.info("Start Test: " + this.getClass().getSimpleName());
		log.info("Driver is initialized. Navigated to Home page");
	}

	@AfterClass(groups = "Smoke")
	public void testAfterSuite() throws InterruptedException, IOException {
		log.info("End Test: " + this.getClass().getSimpleName());
		obj.closeBrowser();
	}

	@Test(priority = 1, groups = "Smoke")
	public void numberOfLinks() throws IOException {
		// TC: Check numbers of links on the page
				
		// Account of links on the entire web-page
		log.info("==>> Number of Links on webpage: " + driver.findElements(By.tagName("a")).size());

		// Account of links on the Footer of web-page
		footer = driver.findElement(By.className("page-footer"));
		//System.out.println("==>> Number of Links on footer: " + footer.findElements(By.tagName("a")).size());
		log.info("==>> Number of Links on footer: " + footer.findElements(By.tagName("a")).size());

	}

	@Test(priority = 2, groups = "Smoke")
	public void findFooter() {
		subFooter = driver.findElement(By.cssSelector("footer[class='page-footer '] .list.list--reset"));
		numLinks = subFooter.findElements(By.tagName("a")).size();
		log.info("==>> Number of Links on part of footer: " + numLinks);

	}

	@Test(priority = 3, groups = "Smoke")
	public void clickOnLinks() throws InterruptedException {
		// Click on every links

		String clickOnLinkTab = Keys.chord(Keys.CONTROL, Keys.ENTER);

		for (int i = 1; i < subFooter.findElements(By.tagName("a")).size(); i++) {
			subFooter.findElements(By.tagName("a")).get(i).sendKeys(clickOnLinkTab);
		}
	}

	@Test(priority = 4, groups = "Smoke")
	public void printTitleOfLinks() throws InterruptedException {
		// Check titles of links

		Thread.sleep(3000);

		// Get Home window
		String homeWindow = driver.getWindowHandle();

		// Print Title of every tabs
		Set<String> tabs = driver.getWindowHandles();
		Iterator<String> it = tabs.iterator();
		int x = 0;

		while (it.hasNext()) {
			driver.switchTo().window(it.next());
			titleList.add(driver.getTitle().trim());
		}

		// Switch to home
		driver.switchTo().window(homeWindow);
	}

	@Test(priority = 5, groups = "Smoke")
	public void checkTitleOfLinks() throws InterruptedException, IOException {

		// Read the row of expected links titles from Excel file where: filePath, sheetName, columnName,
		// testCase-name from column name
		originList = eObj.getData(System.getProperty("user.dir") + "\\src\\main\\resources\\workbook.xlsx", "norwegian", "testcases", "checklinks");

		Collections.sort(titleList);
		Collections.sort(originList);
		
		
		log.info("Expected titles names: " + Arrays.toString(titleList.toArray()));
		log.info("Original titles names: " + Arrays.toString(originList.toArray()));
		
		Assert.assertEquals(titleList, originList, "Titles names are different");
		log.info("Titles names are the same");
		//log.error("Titles names are different");
		

		

	}

}
