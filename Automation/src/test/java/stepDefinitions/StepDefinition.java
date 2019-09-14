package stepDefinitions;

import static org.testng.Assert.assertEquals;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import Framework.TestBase;
import ObjectRepository.SystembolagetHomePF;
import ObjectRepository.SystembolagetLoginPage;
import ObjectRepository.SystembolagetSearchResultPF;
import SystemBolaget.Login;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
public class StepDefinition extends TestBase {

	// WebDriver driver;
	// TestBase obj = new TestBase();
	String prodVar = "searchProd"; // Variable in .properties file

	public static Logger log = LogManager.getLogger(Login.class.getName());

	@Given("^Initialize the browser and navigate to Systembolaget home page$")
	public void initialize_the_browser_navigate_home_page() throws Throwable {
		getWebDriver("\\systembolaget.properties"); // Call the reusable method
		log.info("Driver is initialized. Navigated to Home page. ClassName- " + this.getClass().getSimpleName());
	}

	@And("^Click on the adults button$")
	public void click_button_20_old() throws Throwable {

		SystembolagetLoginPage sl = new SystembolagetLoginPage(driver);
		sl.Button20year().click();
		log.info("Button is clicled");
	}

	@And("^Check home page title and URL$")
	public void check_page() throws Throwable {

		assertEquals(driver.getTitle(), "Välkommen till Systembolaget.se");
		log.info("Successfully validated the Title of home page");

		assertEquals(driver.getCurrentUrl(), "https://www.systembolaget.se/");
		log.info("Successfully validated the URL of home page");
	}

	@When("^I search for (.+)$")
	public void i_search_for_something(String text) throws Throwable {
		SystembolagetHomePF sh = new SystembolagetHomePF(driver);
		sh.Search().sendKeys(text);
		sh.Submit().click();
	}

	@Then("^It should be displayed at least one product$")
	public void the_products_should_be() throws Throwable {

		SystembolagetSearchResultPF sr = new SystembolagetSearchResultPF(driver);
		// System.out.println("==>> " + sr.Results().size());

		if (sr.Results().size() > 0) {
			sr.Results().get(0).click();
			Thread.sleep(3000);
			log.info("The first product of results is selected");
		} else {
			log.info("There is not product in results list");
			Assert.assertTrue(false);
		}

	}

	@Then("^The products description contains (.+)$")
	public void the_product_descriptiont_contains(String arg1) throws Throwable {

		SystembolagetSearchResultPF sr = new SystembolagetSearchResultPF(driver);
		String desc = sr.Description();
		log.info("The description of product  received");

		Assert.assertTrue(desc.contains(arg1));
		log.info("The product contains expected description");
	}

	@Then("^The browser closed$")
	public void the_browser_closed() throws Throwable {
		closeBrowser();
	}

}
