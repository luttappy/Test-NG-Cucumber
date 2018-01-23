package info.cukes.cucumber_jvm;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;
import org.testng.annotations.AfterSuite;
import org.junit.AfterClass;
import org.junit.BeforeClass;

//import com.cucumber.listener.ExtentCucumberFormatter;

@RunWith(Cucumber.class)
@CucumberOptions(format = {"pretty","html:target/site/cucumber-pretty", "json:target/cucumber.json"},
	features = {"src/test/resources/info/cukes/cucumber_jvm"},// glue="src/test/java/info/cukes/cucumber_jvm", 
tags="@RunMe"
		)
                                                                                                  
public class RunCukesTest {
                
                
    @BeforeClass
   
    public static void setup() {
    	System.out.println("in before method");
    }
//                
////        // Initiates the extent report and generates the output in the output/Run_<unique timestamp>/report.html file by default.
////       ExtentCucumberFormatter.initiateExtentCucumberFormatter();
////
////      
////        
////
////        // Loads the extent config xml to customize on the report.
////        ExtentCucumberFormatter.loadConfig(new File("src/test/resources/extentConfig.xml"));
////        
////
////        // User can add the system information as follows
////        ExtentCucumberFormatter.addSystemInfo("Browser Name", "Chrome");
////        ExtentCucumberFormatter.addSystemInfo("Browser version", "v52.0.2743.116 ");
////        ExtentCucumberFormatter.addSystemInfo("Selenium version", "v3.0.0-beta2");
//
//
//    }

    @AfterClass
    public static void teatDown() throws Exception {
    	System.out.println("in after script");
    	//This will post report back in jira
//    	JiraService.createExecutionReportInJira();
    	
    }
    
}

