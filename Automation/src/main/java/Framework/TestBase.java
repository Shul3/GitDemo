package Framework;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.io.FileHandler;
import org.testng.Assert;

public class TestBase {
	public Properties prop = new Properties();
	public static WebDriver driver = null;
	public JavascriptExecutor js;
	// public static Logger log = LogManager.getLogger(Login.class.getName());

	public WebDriver getWebDriver(String propFile) throws IOException {

		FileInputStream file = new FileInputStream(
				System.getProperty("user.dir") + "\\src\\main\\resources" + propFile);
		prop.load(file); // Load properties file
		
		//Get browser from Maven cmd:
		//mvn test -PSystembolaget -Dbrowser=chrome
//		String browserName = System.getProperty("browser");
		String browserName = prop.getProperty("browser");
		
		if (browserName.contains("chrome")) {
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\src\\main\\resources\\chromedriver.exe");
			ChromeOptions options = new ChromeOptions();
			//Start headless Chrome browser
			if (browserName.contains("headless"))
				options.addArguments("headless");
			driver = new ChromeDriver(options);
			
		} else if (browserName.contains("firefox")) {
			System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "\\src\\main\\resources\\geckodriver.exe");
			FirefoxOptions options = new FirefoxOptions();
			//Start headless FireFox browser
			if (browserName.contains("headless"))
				options.addArguments("-headless");
			driver = new FirefoxDriver(options);
			
		} else {
			System.out.println("Browser driver is not available");
			// log.info("Browser driver is not available");
			Assert.assertTrue(false);
		}

		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get(prop.getProperty("URL"));
		
		return driver;
	}

	public void closeBrowser() throws IOException {

		driver.manage().deleteAllCookies();
		driver.close();

		if (driver != null)
			driver.quit();
		driver = null;
	}

	public String searchProduct(String search) {

		return prop.getProperty(search);
	}
	

	public void getScreenshot(String result) {
		String screenName = result + "_" + System.currentTimeMillis() + ".png";
		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

		try {

			FileHandler.copy(src, new File(System.getProperty("user.dir") + "/screenshots/" + screenName + ""));
		} 
		catch (IOException e) {

			System.out.println(e.getMessage());
		}

	}
}
