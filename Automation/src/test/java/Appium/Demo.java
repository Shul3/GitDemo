package Appium;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.logging.log4j.*;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;

public class Demo {
	// Prerequisites:
	// My Appium server is started
	// My emulator is opened
	
	private static Logger log = LogManager.getLogger(Demo.class.getName());

	//@Parameters({ "AppiumURL" })
	@Test()
	public void setUp() throws IOException, InterruptedException {
	//public void setUp(String url) throws IOException, InterruptedException {

		File dir = new File("src/test/java/Appium");
		File fn = new File(dir, "Systembolaget.apk");

		Runtime runtime = Runtime.getRuntime();
		runtime.exec("cmd.exe /c start cmd.exe /k \"appium -a 127.0.0.1 -p 4723\"");
		runtime.exec(
				"cmd.exe /c start cmd.exe /k \"C:\\Users\\sergejs.sulziks\\AppData\\Local\\Android\\Sdk\\emulator\\emulator.exe -avd MyDemo2\"");

		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setCapability(MobileCapabilityType.DEVICE_NAME, "MyDemo2");
		cap.setCapability(MobileCapabilityType.APP, fn.getAbsolutePath());
		cap.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 180);

		Thread.sleep(20000);
		AndroidDriver<AndroidElement> driver = new AndroidDriver<AndroidElement>(
				new URL("http://127.0.0.1:4723/wd/hub"), cap);

	}

	@AfterTest
	public void end() {

	}

	@Test(timeOut = 10000)
	public void logIn() {
		System.out.println("==>> logIn() ");

	}

	@Test
	public void searchProduct() {

	}

}
