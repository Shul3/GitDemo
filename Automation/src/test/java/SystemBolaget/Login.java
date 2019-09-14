package SystemBolaget;

import static org.testng.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import Framework.TestBase;
import ObjectRepository.SystembolagetHomePF;
import ObjectRepository.SystembolagetLoginPage;
import ObjectRepository.SystembolagetSearchResultPF;
import REST.RestDemo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;

//Search product by name

public class Login extends TestBase {
//	WebDriver driver;
//	TestBase obj = new TestBase();
	String prodVar ="searchProd"; //Variable in .properties file

	public static Logger log = LogManager.getLogger(Login.class.getName());

	@BeforeClass()
	public void testBeforeSuite() throws IOException {
		System.out.println("Start Test: " + this.getClass().getSimpleName());
		getWebDriver("\\systembolaget.properties"); //Call the reusable method
		log.info("Driver is initialized. Navigated to Home page. ClassName- " + this.getClass().getSimpleName());
	}

	@AfterClass
	public void testAfterSuite() throws InterruptedException, IOException {
		System.out.println("End Test: " + this.getClass().getSimpleName());
		closeBrowser();
		log.info("Driver closed the browser. ClassName- " + this.getClass().getSimpleName());
	}

	@Test(testName = "Login", priority = 1)
	public void LogIn() {

		assertEquals(driver.getTitle(), "Välkommen till Systembolaget.se");
		log.info("Successfully validated the Title of home page");
		
		assertEquals(driver.getCurrentUrl(), "https://www.systembolaget.se/");
		log.info("Successfully validated the URL of home page");

		// Implement Page Object Model pattern style
		SystembolagetLoginPage sl = new SystembolagetLoginPage(driver);
		sl.Button20year().click();

	}

	@Test(testName = "SearchProduct", dependsOnMethods = "LogIn")
	public void Search() {
		
		// Implement Page Object Factory Model pattern style
		SystembolagetHomePF sh = new SystembolagetHomePF(driver);
		sh.Search().sendKeys(searchProduct(prodVar));
		sh.Submit().click();

	}

	@Test(testName = "SearchResult", dependsOnMethods = "Search")
	public void SearchResult() throws InterruptedException {
		
		// Implement Page Object Factory Model pattern style
		SystembolagetSearchResultPF sr = new SystembolagetSearchResultPF(driver);
		System.out.println(">>== " + sr.Results().toString());
		
//		Thread.sleep(3000);

	}
}
