package info.cukes.cucumber_jvm;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Login_Page 
{
	
	WebDriver driver;
	
	
	public Login_Page (WebDriver test)
	{

		driver = test;
	}

	By username= By.name("userName");
	By password = By.name("password");
	By submit = By.name("login");
	
	public void create_user()
	{
		driver.findElement(username).sendKeys("tcoedemo");
		driver.findElement(password).sendKeys("password");
		driver.findElement(submit).click();
		
	}
}
