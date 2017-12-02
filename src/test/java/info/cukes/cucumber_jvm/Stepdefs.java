package info.cukes.cucumber_jvm;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

//import com.relevantcodes.extentreports.LogStatus;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import cucumber.api.java.en.Then;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.IOException;


public class Stepdefs {
    private static ChromeDriverService service;
    WebDriver driver;
    By username = By.name("userName");
    By password = By.name("password");
    By submit = By.name("login");

    @Given("^the user opened \"([^\"]*)\" browser$")
    public void the_user_opened_browser(String arg1) {
        service = new ChromeDriverService.Builder().usingChromeDriverExecutable(new File("C:\\Users\\Ramesh\\Downloads\\chromedriver.exe")).usingAnyFreePort().build();
        try {
            service.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.setProperty("webdriver.chrome.driver", "C:/Users/Ramesh/Downloads/chromedriver.exe");
        driver = new RemoteWebDriver(service.getUrl(),
                DesiredCapabilities.chrome());
        //	System.setProperty("webdriver.gecko.driver", "D:\\\\ToolsQA\\trunk\\Library\\drivers\\geckodriver.exe");
        //	WebDriver driver = new FirefoxDriver();
        //        System.setProperty("webdriver.gecko.driver", "C:\\Users\\kames\\Downloads\\geckodriver-v0.16.1-win64\\geckodriver.exe");
        //        DesiredCapabilities capabilities = DesiredCapabilities.firefox();
        //        capabilities.setCapability("marionette", true);
        //        driver = new FirefoxDriver();
    }

    @When("^the user navigated to \"([^\"]*)\" page$")
    public void the_user_navigated_to_page(String arg1) {

        driver.get(arg1);
        driver.manage().window().maximize();
    }

    @When("^the user enters username \"([^\"]*)\" in  input field$")
    public void the_user_enters_username_in_input_field(String arg1) {
        driver.findElement(username).sendKeys(arg1);


    }

    @When("^the user enters password \"([^\"]*)\" in  input field$")
    public void the_user_enters_password_in_input_field(String arg1) {
        driver.findElement(password).sendKeys(arg1);
    }

    @Then("^the user clicks on \"([^\"]*)\" button$")
    public void the_user_clicks_on_button(String arg1) throws Throwable {
        driver.findElement(submit).click();
        driver.close();
    }


}
