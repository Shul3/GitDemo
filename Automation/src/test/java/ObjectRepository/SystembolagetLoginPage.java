package ObjectRepository;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SystembolagetLoginPage {
	
	WebDriver driver;
	public SystembolagetLoginPage(WebDriver driver) {
		
		this.driver=driver;
	}
	
	
   private By button=By.xpath("//button[text()='Jag är 20 år eller äldre']");
   
   
   public WebElement Button20year()
   {
	   System.out.println("Trying to identify Button 'More then 20 year old' ");
	   return driver.findElement(button);
   }
         
   

}
