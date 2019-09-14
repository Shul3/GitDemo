package ObjectRepository;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class SystembolagetSearchResultPF {
	
	WebDriver driver;

	public SystembolagetSearchResultPF(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(css="div[class='elm-product-list-item-full-info']")
	private WebElement result;
	
	@FindAll(@FindBy(how = How.CSS, using = "div[class='elm-product-list-item-full-info']"))
	List<WebElement> results;
	
//	@FindAll(@FindBy(how = How.CSS, using = "p[class='description']"))
//	List<WebElement> descriptions;
	 
	@FindBy(xpath="//p[@class='description ']")
	private WebElement description;
   
   public WebElement Result()
   {
	   System.out.println("Trying to identify result of searching");
	   return result;
   }
         
   public List<WebElement> Results()
   {
	   System.out.println("Trying to identify results of searching");
	   return results;
   }
   
   public String Description()
   {
	   System.out.println("Trying to identify descriptions results of searching");
	   
	   return description.getText();
   }

}
