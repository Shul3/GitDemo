package Framework;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class JdbcConnection {

	public static void main(String[] args) throws SQLException {
		
		
		// Connect to Soapui- database on the local MySql-server
		String host = "localhost";
		String port = "3306";

		Connection con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/soapui", "root", "root");
		Statement s = con.createStatement();
		ResultSet rs = s.executeQuery("select * from credentials where scenario = 'zerobalancecard'");

		while (rs.next()) {
			System.out.println(rs.getString("username"));
			System.out.println(rs.getString("password"));
			
			//Example of using
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\src\\main\\resources\\chromedriver.exe");
			WebDriver driver = new ChromeDriver();
			driver.get("https://login.salesforce.com");
			
			driver.findElement(By.id("username")).sendKeys(rs.getString("username"));
			driver.findElement(By.id("password")).sendKeys(rs.getString("password"));
			
			
		}

	}

}
