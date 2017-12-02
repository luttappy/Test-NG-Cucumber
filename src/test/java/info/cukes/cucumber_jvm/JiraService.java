package info.cukes.cucumber_jvm;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.builder.RequestSpecBuilder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import cucumber.api.Scenario;




public class JiraService  {
	
	//public static String urlStr = "https://260750:Start123@localhost:8081/rest/raven/1.0/import/execution/cucumber";
	public static String urlStr = "https://172.31.8.251:8081/rest/raven/1.0/import/execution/cucumber";
	  public static JiraDataType getCallJira(String ticketNumber, String testID, Scenario scenario) {

		    Client client = ClientBuilder.newClient();

		    WebTarget webTarget = client.target("https://260750:Start123@172.31.8.251:8081/rest/raven/1.0/api/testexec/" + ticketNumber + "/test");
		    Invocation.Builder inBuilder = webTarget.request(MediaType.APPLICATION_JSON);
		    inBuilder.header("AUTHORIZATION", "Basic MjYwNzUwOlN0YXJ0MTIz");

		    Response response = inBuilder.buildGet().invoke();
		    if (response.getStatus() != 200) {
		      System.out.println("New User was not created -> Status Code -> " + response.getStatusInfo());
		    }

		    JsonArray arrayValue = JsonUtil.toJson(response.readEntity(String.class)).getAsJsonArray();
		    JsonArray value = arrayValue.getAsJsonArray();
		    System.out.println(value.size());
		    JiraDataType jiraData = null;
		    for (int i = 0; i < value.size(); i++) {
		      JsonObject jsonObject = value.get(i).getAsJsonObject();

		      jiraData = JsonUtil.toObject(JiraDataType.class, jsonObject);

		      System.out.println(jiraData.getKey());

		      // Update jira status
		      int exeID = jiraData.getId();

		      if(testID.contains(jiraData.getKey())){
		        updateStatusJiraTicket(jiraData.getId(), jiraData.getKey(), testID, scenario);
		      }
		      //updateStatusJiraTicket(jiraData.getId(), jiraData.getKey(), failedJiraID);

		    }
		    return jiraData;
		  }

          //update status pass or fail for bulk regression test
		  private static void updateStatusJiraTicket(int exeId, String jiraID, String failedJiraID, Scenario scenario) {

		    String jsonBody;
		    Entity<String> jiraType;
		    Response response;
		    Client client = ClientBuilder.newClient();
		    //client.register(new JacksonFeature()).register(JacksonProvider.class);

		    WebTarget webTarget = client.target("https://260750:Start123@172.31.8.251:8081/rest/raven/1.0/api/testrun/" + exeId + "/");
		    Invocation.Builder inBuilder = webTarget.request(MediaType.APPLICATION_JSON);
		    inBuilder.header("AUTHORIZATION", "Basic MjYwNzUwOlN0YXJ0MTIz");

		    String[] arrayValue = failedJiraID.split(",");

		    if (Arrays.asList(arrayValue).contains(jiraID) && scenario.isFailed()) {

		      //LOGGER.INFO("this updates Fail in JIRA");
		      jsonBody = "{\r\n\"status\" :\"FAIL\"\t\r\n}";
		      jiraType = Entity.entity(jsonBody, MediaType.APPLICATION_JSON);
		      response = inBuilder.buildPut(jiraType).invoke();
		      if (response.getStatus() != 200) {
		        System.out.println(" -> Status Code -> " + response.getStatusInfo());
		      }
		    } else {

		      jsonBody = "{\r\n\"status\" :\"PASS\"\t\r\n}";

		      jiraType = Entity.entity(jsonBody, MediaType.APPLICATION_JSON);
		      response = inBuilder.buildPut(jiraType).invoke();
		      if (response.getStatus() != 200) {
		       System.out.println("New User was not created -> Status Code -> " + response.getStatusInfo());
		      }

		    }

		  }

		  private static String testIDJiraCall(String jiraKey) {

		    Client client = ClientBuilder.newClient();


		    WebTarget webTarget = client.target("https://260750:Start123@172.31.8.251:8081/rest/api/2/search?jql=issueKey=" + jiraKey + "&fields=description");
		    Invocation.Builder inBuilder = webTarget.request(MediaType.APPLICATION_JSON);
		    inBuilder.header("AUTHORIZATION", "Basic MjYwNzUwOlN0YXJ0MTIz");

		    Response response = inBuilder.buildGet().invoke();

		    JsonObject jsonObject = JsonUtil.toJson(response.readEntity(String.class)).getAsJsonObject();

		    if (response.getStatus() != 200) {
		    	 System.out.println("New User was not created -> Status Code -> " + response.getStatusInfo());
		    }

		    JsonArray jsonObject2 = jsonObject.getAsJsonArray("issues").getAsJsonArray();

		    System.out.println("this is" + jsonObject2.get(0));
		    return null;

		  }

          //just a main 
		  public static void main(String[] arg) {

		    //getCallJira("OCP-282333");
		    pullScenarioFromJira("OCP-282333");
		   // createFeatureFile();

		  }

		  //Download gherkin steps from Jira and create as .feature in target folder
		  public static void pullScenarioFromJira(String testExecutionID) {


		    Client client = ClientBuilder.newClient();
		    WebTarget webTarget;
		    Invocation.Builder inBuilder;
		    JsonArray arrayValue;
		    JsonArray value;

		    webTarget = client.target("https://260750:Start123@172.31.8.251:8081/rest/raven/1.0/api/testexec/" + testExecutionID + "/test");
		    inBuilder = webTarget.request(MediaType.APPLICATION_JSON);
		    inBuilder.header("AUTHORIZATION", "Basic MjYwNzUwOlN0YXJ0MTIz");

		    Response response = inBuilder.buildGet().invoke();
		    if (response.getStatus() != 200) {
		    	 System.out.println("New User was not created -> Status Code -> " + response.getStatusInfo());
		    }

		    arrayValue = JsonUtil.toJson(response.readEntity(String.class)).getAsJsonArray();
		    value = arrayValue.getAsJsonArray();
		    System.out.println(value.size());
		    JiraDataType jiraData = null;
		    for (int i = 0; i < value.size(); i++) {
		      JsonObject jsonObject = value.get(i).getAsJsonObject();

		      jiraData = JsonUtil.toObject(JiraDataType.class, jsonObject);

		      String testKey = jiraData.getKey();

		      if (testKey != null) {
		    	  //create as .feature in target folder
		        createFeatureFile(testKey);
		      }

		    }
		  }

		  // create as .feature in target folder
		    public static void createFeatureFile(String testKey) {

		      WebTarget webTarget;
		      Invocation.Builder inBuilder;
		      Client client = ClientBuilder.newClient();

		      webTarget = client.target("https://260750:Start123@172.31.8.251:8081/rest/raven/1.0/api/test?keys="+testKey+"&fields=customfield_23242");
		      inBuilder = webTarget.request(MediaType.APPLICATION_JSON);
		      inBuilder.header("AUTHORIZATION", "Basic MjYwNzUwOlN0YXJ0MTIz");

		      Response response = inBuilder.buildGet().invoke();
		      if (response.getStatus() != 200) {
		    	  System.out.println("Jira s -> Status Code -> " + response.getStatusInfo());
		      }
		      JsonElement actualJson = JsonUtil.toJson(response.readEntity(String.class)).getAsJsonArray().get(0).getAsJsonObject().get("definition");

		      try {

		        String downloadFeaturePath = "target/SmokeFeature";
		        File screenshotDir = new File(downloadFeaturePath);
		        if (!screenshotDir.exists()) {
		          screenshotDir.mkdirs();
		          System.out.println();

		        }

		      FileWriter ryt = new FileWriter(downloadFeaturePath+"/"+testKey+".feature");
		      // Write content to the feature file
		      BufferedWriter out = new BufferedWriter(ryt);
		      out.write(actualJson.getAsString());
		      out.close();

		      } catch (IOException e) {
		        e.printStackTrace();
		      }
		    }
		    
		    /**
		     * this method is to post execution repot in JIRA screenshot for Kibana Reporting
		     */
		    public static void createExecutionReportInJira() throws Exception {

		      com.jayway.restassured.response.Response response;
		      String jiraExecutionKey;
		      String jiraXrayURL;
				//https://172.31.8.251:8081/rest/raven/1.0/import/execution/cucumber
				RestAssured.requestSpecification = new RequestSpecBuilder().setBaseUri("https://172.31.8.251:8081/rest/raven/1.0/import/execution/cucumber").build();
		      //Cucmber.json generated by cucumber
		      File inFile = new File("D:\\JiraWorkspace\\TestNG-and-Cucumber\\TestNG-and-Cucumber-master\\target\\cucumber.json");

		      RestAssured.reset();
		      response = RestAssured
		        .given()
		        .body(inFile)
		        .with()
		        .contentType("application/json")
		        .accept("application/json")
					  .post("https://260750:Start123@localhost:8081/rest/raven/1.0/import/execution/cucumber");
		      if (response.getStatusCode() != 200) {
		        System.out.println(response.getStatusCode()+"error generating Jira report");
		        return;
		      }

		      jiraExecutionKey = response.getBody().jsonPath().get("testExecIssue.key");
		      jiraXrayURL =urlStr + jiraExecutionKey;

		      //Adding comments and assigned to sthan8
		       //addCommentsInJiraTest(jiraExecutionKey);

		      //Change status to "Deploy Ready"
		      //changeStatusInJiraTest(jiraExecutionKey);
		    }
		    
		    /**
		     * this method is to update test execution jira ticket with comments and change status to deploy ready
		     */
		    private static void addCommentsInJiraTest(String jiraExecutionKey) {

		      String jiraURL = urlStr + jiraExecutionKey;

		      String jsonBody = "{\"update\" : {\"comment\" :[{\"add\" : {\"body\" : \"Test executed in :Environment:= " + System.getProperty("ocp.environment") + " && Browser:= " + System.getProperty("browser") + "\"}}],\"assignee\" : [{\"set\" : {\"name\" : \"sthan8\"}}]}}";

		      //String jsonBody = "{ \"body\": \"Test executed in :Environment:= " + System.getProperty("ocp.environment") +" && Browser:= "+ System.getProperty("browser")+"\"}";
		      com.jayway.restassured.response.Response response;

		      RestAssured.reset();
		      RestAssured.requestSpecification = new RequestSpecBuilder().setBaseUri(jiraURL).build();
		      response = RestAssured.given()
		        .body(jsonBody).contentType("application/json")
		        .when().accept("application/json")
		        .put(jiraURL)
		        .then()
		        .extract()
		        .response();
		      if (response != null) {
		        System.out.println(response
		          .getBody()
		          .asString());
		      } else {
		    	  System.out.println("Null Response??");
		      }
		    }
		    
		    /**
		     * this method is to update change status to deploy ready in Jira
		     */
		    private static void changeStatusInJiraTest(String jiraExecutionKey) {

		      String jiraURL = urlStr + jiraExecutionKey + "/transitions?expand=transitions.fields";

		      // 61 is for Deploy ready : check the jira api document for the other status code
		      String jsonBody = "{\"transition\":{\"id\":\"61\"}}";
		      com.jayway.restassured.response.Response response;

		      RestAssured.reset();
		      RestAssured.requestSpecification = new RequestSpecBuilder().setBaseUri(jiraURL).build();
		      response = RestAssured.given()
		        .body(jsonBody).contentType("application/json")
		        .when().accept("application/json")
		        .post(jiraURL)
		        .then()
		        .extract()
		        .response();
		      if (response != null) {
		        System.out.println(response
		          .getBody()
		          .asString());
		      } else {
		        System.out.println(("Null Response??"));
		      }
		    }

		    public static void issueDetails() {

				com.jayway.restassured.response.Response response;

				RestAssured.reset();
				RestAssured.requestSpecification = new RequestSpecBuilder().setBaseUri("http://260750:Start123@172.31.8.251:8081/rest/api/2/issue/SCRUM-10").build();
				response = RestAssured.given()
						.when().accept("application/json")
						.get("http://260750:Start123@172.31.8.251:8081/rest/api/2/issue/SCRUM-10")
						.then()
						.extract()
						.response();
				if (response != null) {
					System.out.println(response
							.getBody()
							.asString());
				} else {
					System.out.println(("Null Response??"));
				}


			}
}
