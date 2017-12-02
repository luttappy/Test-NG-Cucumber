package info.cukes.cucumber_jvm;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.Test;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.*;
import cucumber.api.testng.TestNGCucumberRunner;

@Test(dataProviderClass = TestNGCucumberRunner.class)
@CucumberOptions(format = {"pretty","html:target/site/cucumber-pretty", "json:target/cucumber.json", "rerun:target/rerun.txt"},
                        
                            monochrome = true, tags = "@RunMe")

public class TestNGRunnerTest extends AbstractTestNGCucumberTests {

  @AfterSuite
  public void testNGTearDown() throws Exception{
    String jiraXrayReport = System.getProperty("jira.xray.report");
    Boolean jiraReportValue = Boolean.valueOf(jiraXrayReport);

    if(jiraReportValue) {
  	JiraService.createExecutionReportInJira();


    }
  }


}
