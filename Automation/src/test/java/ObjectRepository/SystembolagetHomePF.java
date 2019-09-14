package ObjectRepository;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class SystembolagetHomePF {

	WebDriver driver;

	public SystembolagetHomePF(WebDriver driver) {

		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(id="ProductSearchTextInput")
	private WebElement searchBar;
	
	@FindBy(id="ProductSearchSubmitButton")
	private WebElement submitBtn;
	
	 
   
   public WebElement Search()
   {
	   System.out.println("Trying to identify Search bar");
	   return searchBar;
   }
         
   public WebElement Submit()
   {
	   System.out.println("Trying to identify Submit button of Search- bar");
	   return submitBtn;
   }
   
  
}
