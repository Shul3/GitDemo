package Norwegian;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;
//import java.util.logging.FileHandler;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import Appium.Demo;


public class BookResa {

	// TC: Munich - Stockholm
	// Departure date: 30 januari 2020
	// Return date: 20 februari 2020
	// 2 Adults + 1 child
	
	private static Logger log = LogManager.getLogger(BookResa.class.getName());

	JavascriptExecutor js;
	WebDriver driver;
	String text = "";
	String script = "";
	int i = 0;
	int count = 0;
	Properties prop = new Properties();
//	TestBase obj = new TestBase();

	@BeforeClass
	public void testBeforeSuite() throws IOException, InterruptedException {
		FileInputStream file = new FileInputStream(System.getProperty("user.dir") + "\\src\\main\\resources" + "\\norwegian.properties");
		prop.load(file); // Load properties file
		
		if(prop.get("browser").toString().equals("chrome")) {
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\src\\main\\resources\\chromedriver.exe");
			driver = new ChromeDriver();
		}
		else if(prop.get("browser").toString().equals("firefox")) {
			System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "\\src\\main\\resources\\geckodriver.exe");
			driver = new FirefoxDriver();
		}
		else {
			Assert.assertTrue(false, "WebDriver is not available");
		}
		
//		driver = obj.getWebDriver(); //Call the reusable method
		
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		System.out.println("Start Test: " + this.getClass().getSimpleName());
		
		
		driver.get(prop.get("URL").toString());
		Thread.sleep(2000);
		js = (JavascriptExecutor) driver;
	}

	@AfterClass
	public void testAfterSuite() throws IOException {
		System.out.println("End Test: " + this.getClass().getSimpleName());
		driver.manage().deleteAllCookies();
		driver.close();
		//driver.quit();
	}

	@Test(priority = 1)
	public void checkSubmitButton() throws InterruptedException {

		// Check Submit-button
		System.out.println(
				"=> " + driver.findElement(By.cssSelector("button[id='searchButton']")).getAttribute("aria-disabled"));
		Assert.assertTrue(Boolean.parseBoolean(
				driver.findElement(By.cssSelector("button[id='searchButton']")).getAttribute("aria-disabled")));
	}

	@Test(priority = 2)
	public void selectSource() throws InterruptedException {

		WebElement source = driver.findElement(By.id("airport-select-origin"));
		source.clear();
		// source.sendKeys(src); // send Munich
		source.click(); // Test with select
		source.sendKeys(Keys.DOWN);
		Thread.sleep(2000);

		// **** Check airport-origin
		script = "return document.getElementById('airport-select-origin').value;";
		text = (String) js.executeScript(script);
		// System.out.println(text);

		while (!text.equalsIgnoreCase((String) prop.get("origin"))) {
			i++;

			driver.findElement(By.id("airport-select-origin")).sendKeys(Keys.DOWN);
			script = "return document.getElementById('airport-select-origin').value;";
			text = (String) js.executeScript(script);

			// System.out.println(text);

			if (i > 200) {
				System.out.println("Element is not found");
				break;
			}
		}
		if (i <= 200) {
			driver.findElement(By.id("airport-select-origin")).sendKeys(Keys.ENTER);
		}
		Assert.assertEquals(text, prop.get("origin"));
	}

	@Test(priority = 3)
	public void selectDestination() throws InterruptedException {
		text = "";
		i = 0;

		WebElement dest = driver.findElement(By.id("airport-select-destination"));
		dest.clear(); // Reset default data
		// dest.sendKeys(dst); // send Stockholm
		dest.click(); // Select from drop-down menu
		dest.sendKeys(Keys.DOWN);
		Thread.sleep(2000);

		// **** Check airport- destination
		script = "return document.getElementById('airport-select-destination').value;";
		text = (String) js.executeScript(script);

		while (!text.equalsIgnoreCase((String) prop.get("destination"))) {
			i++;
			driver.findElement(By.id("airport-select-destination")).sendKeys(Keys.DOWN);
			script = "return document.getElementById('airport-select-destination').value;";
			text = (String) js.executeScript(script);
			if (i > 200) {
				System.out.println("Element is not found");
				break;
			}
		}
		if (i <= 200)
			driver.findElement(By.id("airport-select-destination")).sendKeys(Keys.ENTER);

		Assert.assertEquals(text, prop.get("destination"));
	}

	@Test(priority = 4)
	public void selectOutboundDate() throws InterruptedException {

		// Check the date is available for travel or not !!!
		///////////////////////////////////////////
		// Departure Day: 30 Januari

		// JavascriptExecutor js = (JavascriptExecutor) driver;
		WebElement dateDep = driver
				.findElement(By.cssSelector("section[id='outboundDate'] input[class='calendar__input']"));
		js.executeScript("arguments[0].scrollIntoView();", dateDep);

		Thread.sleep(1000);

		dateDep.click();
		Thread.sleep(2000);

		// WebDriverWait d = new WebDriverWait(driver, 10);
		// d.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='resultsContainer']/section/article[1]")));

		while (!driver
				.findElement(By.cssSelector(
						"section[id='outboundDate'] div[class='ui-datepicker-content'] button[id*='datepicker']"))
				.getText().contains(prop.get("outbondM").toString().trim())) {
			driver.findElement(By.cssSelector("section[id='outboundDate'] button[aria-label='Nästa']")).click();
		}

		Thread.sleep(1000);

		// Put into list and iterate
		// List<WebElement> dates = driver
		// .findElements(By.cssSelector("td[id*='datepicker']
		// button[aria-disabled='false'] span"));

		count = driver.findElements(By.cssSelector("td[id*='datepicker'] button[aria-disabled='false'] span")).size();

		for (int i = 0; i < count; i++) {

			text = driver.findElements(By.cssSelector("td[id*='datepicker'] button[aria-disabled='false'] span")).get(i)
					.getText();
			if (text.equalsIgnoreCase(prop.get("outbondD").toString().trim())) {
				driver.findElements(By.cssSelector("td[id*='datepicker'] button[aria-disabled='false'] span")).get(i)
						.click();
				break;
			}
		}
	}

	@Test(priority = 5)
	public void selectReturnDate() throws InterruptedException {

		// Check the date is available for travel or not !!!!
		//////////////////////////////////////////////////////
		// Arrival 20 Februari
		count = 0;

		while (!driver
				.findElement(By.cssSelector(
						"section[id='returnDate'] div[class='ui-datepicker-content'] button[id*='datepicker']"))
				.getText().contains(prop.get("returnM").toString().trim())) {

			driver.findElement(By.cssSelector("section[id='returnDate'] button[aria-label='Nästa']")).click();
		}

		Thread.sleep(2000);

		count = driver.findElements(By.cssSelector("td[id*='datepicker'] button[aria-disabled='false'] span")).size();

		for (int i = 0; i < count; i++) {

			text = driver.findElements(By.cssSelector("td[id*='datepicker'] button[aria-disabled='false'] span")).get(i)
					.getText();
			if (text.equalsIgnoreCase(prop.get("returnD").toString().trim())) {
				driver.findElements(By.cssSelector("td[id*='datepicker'] button[aria-disabled='false'] span")).get(i)
						.click();
				break;
			}
		}

	}

	@Test(priority = 15)
	public void checkTwoOneWays() throws InterruptedException {
		// Check radio-buttons Two/One way

		// Return Way check
		// driver.findElement(By.xpath("//div[@id='tripType']/span[1]/label/span[1]")).click();
		// //Click Tur/Retur radio-button
		WebElement rad1 = driver.findElement(By.xpath("//div[@id='tripType']/span[1]/label/input"));
		System.out.println(rad1.getAttribute("aria-checked"));
		Assert.assertEquals(rad1.getAttribute("aria-checked"), "true");

		// One-way ticket
		// driver.findElement(By.xpath("//div[@id='tripType']/span[2]/label/span[1]")).click();
		// //Click OneWay radio-button
		// Thread.sleep(3000);
		// Check isSelected Radio Button
		System.out.println(
				driver.findElement(By.xpath("//div[@id='tripType']/span[2]/label/input")).getAttribute("aria-checked"));
		Assert.assertEquals(
				driver.findElement(By.xpath("//div[@id='tripType']/span[2]/label/input")).getAttribute("aria-checked"),
				"false");

		Thread.sleep(3000);
		// System.out.println(driver.findElement(By.cssSelector("label[class='control
		// radio' span]")).getAttribute("class"));

	}

	@Test(priority = 16)
	public void addAdults() throws InterruptedException {

		// Check: One adult should be
		int countAdults = 1;
		script = "return document.getElementById('adultCount').value;";
		text = (String) js.executeScript(script);
		System.out.println(text);
		Assert.assertEquals(countAdults, Integer.parseInt(text), "Total adults are not " + countAdults);

		// Add one more adult
		driver.findElement(By.cssSelector(
				"button[class='numberfield__button  numberfield__button--add'][aria-controls='adultCount']")).click();
		countAdults++;

		// Check total adults: it should be countAdults
		script = "return document.getElementById('adultCount').value;";
		text = (String) js.executeScript(script);
		System.out.println(text);
		Assert.assertEquals(countAdults, Integer.parseInt(text), "Total adults are not " + countAdults);
	}

	@Test(priority = 17)
	public void addChildInfant() throws InterruptedException, IOException {

		// TC: Check total count of child and infant
		// Add one child and check again

		driver.findElement(By.cssSelector("button[id='addChildren']")).click();
		Thread.sleep(1000);

		// Check: No child should be
		int countChild = 0;
		script = "return document.getElementById('childCount').value;";
		text = (String) js.executeScript(script);
		// System.out.println(text);
		Assert.assertEquals(countChild, Integer.parseInt(text), "Total child are not " + countChild);

		int countInfant = 0;
		script = "return document.getElementById('infantCount').value;";
		text = (String) js.executeScript(script);
		// System.out.println(text);
		Assert.assertEquals(countInfant, Integer.parseInt(text), "Total infant are not " + countInfant);

		// Add one more child
		driver.findElement(By.cssSelector(
				"button[class='numberfield__button  numberfield__button--add'][aria-controls='childCount']")).click();
		countChild++;

		// Check total child: it should be countChild
		script = "return document.getElementById('childCount').value;";
		text = (String) js.executeScript(script);
		// System.out.println(text);
		Assert.assertEquals(countChild, Integer.parseInt(text), "Total child are not " + countChild);

		// Check total infant also, It should be zero

		// Take screenshot
		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(src, new File(System.getProperty("user.dir") +"\\screenshots\\addChildInfant.png"));

	}

	@Test(priority = 18)
	public void selectSubmitButton() throws InterruptedException {

	}



}
