package info.cukes.cucumber_jvm;

import org.testng.ITestResult;
import org.testng.annotations.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import java.io.File;
import java.io.IOException;

public class Login {

    ExtentReports reports;
    ExtentTest logger;

    //paths used
    String basePath = System.getProperty("user.dir");
    String reportPath = basePath + "/src/test/resources/Reports";
    String driversPath = basePath + "/src/test/resources/webDrivers";

    //remote driver services
    WebDriver driver;
    private static ChromeDriverService chromeService;

    @Test
    public void userinputs() {
        Login_Page login = new Login_Page(driver);
        login.create_user();
        logger.log(LogStatus.INFO, "User ID entered sucessfully");
        logger.log(LogStatus.INFO, "Password entered sucessfully");
        logger.log(LogStatus.INFO, "Clicks on Submit button");
        logger.log(LogStatus.PASS, "Login Sucessfull");
    }

    @BeforeMethod
    @Parameters("browser")

    public void invoke_browser(String browser) {
        System.out.println(browser);

        reports = new ExtentReports(reportPath + "/extentreport.html");
        logger = reports.startTest("Flight Reservation Login");

        if (browser.equalsIgnoreCase("chrome")) {
            logger.log(LogStatus.INFO, "Browser started ");
            chromeService = new ChromeDriverService.Builder().usingChromeDriverExecutable(new File(driversPath + "/chromedriver.exe")).usingAnyFreePort().build();
            try {
                chromeService.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
            driver = new RemoteWebDriver(chromeService.getUrl(),
                    DesiredCapabilities.chrome());

        }
//        else if (browser.equalsIgnoreCase("firefox")) {
//            System.setProperty("webdriver.gecko.driver", "C:\\Users\\kames\\Downloads\\geckodriver-v0.16.1-win64\\geckodriver.exe");
//            driver = new FirefoxDriver();
//
//        }
//        else if (browser.equalsIgnoreCase("IE")) {
//
//            System.setProperty("webdriver.ie.driver", "C:\\Users\\jaganv1\\Desktop\\jars\\IEDriverServer_x64_3.3.0\\IEDriverServer.exe");
//            driver = new InternetExplorerDriver();
//        }


        driver.get("http://newtours.demoaut.com/");
        logger.log(LogStatus.INFO, "Application is up and running");
        driver.manage().window().maximize();

    }

    @AfterMethod
    public void Closebrowser(ITestResult result) {
        reports.endTest(logger);
        reports.flush();
        driver.get(reportPath + "/extentreport.html");
    }
}


// different codes to try.....


//if(result.getStatus()==ITestResult.FAILURE)
//{
//
//
//String screenshot_path=utility.Screenshots(driver, result.getName());
//String image= logger.addScreenCapture(screenshot_path);
//logger.log(LogStatus.FAIL, "Title verification", image);
// 		  System.setProperty("webdriver.chrome.driver", "C:\\Users\\kames\\Downloads\\chromedriver_win32\\chromedriver.exe");
//			  driver =new ChromeDriver();
//
//
//}