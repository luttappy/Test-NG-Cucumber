package info.cukes.cucumber_jvm;

public class JiraDataType {
	
	private int id;
	  private String key;
	  private int rank;
	  private String status;
	  private String self;
	  private String reporter;
	  private String assignee;
	  private String precondition;
	  private String type;
	  private String definition;
	  private String testExecIssue;

	  // Description
	  private String description;

	  public String getKey() {
	    return key;
	  }

	  public void setKey(String key) {
	    this.key = key;
	  }

	  public int getRank() {
	    return rank;
	  }

	  public void setRank(int rank) {
	    this.rank = rank;
	  }

	  public String getStatus() {
	    return status;
	  }

	  public void setStatus(String status) {
	    this.status = status;
	  }

	  public String getSelf() {
	    return self;
	  }

	  public void setSelf(String self) {
	    this.self = self;
	  }

	  public String getReporter() {
	    return reporter;
	  }

	  public void setReporter(String reporter) {
	    this.reporter = reporter;
	  }

	  public String getAssignee() {
	    return assignee;
	  }

	  public void setAssignee(String assignee) {
	    this.assignee = assignee;
	  }

	  public String getPrecondition() {
	    return precondition;
	  }

	  public void setPrecondition(String precondition) {
	    this.precondition = precondition;
	  }

	  public String getType() {
	    return type;
	  }

	  public void setType(String type) {
	    this.type = type;
	  }

	  public String getDefinition() {
	    return definition;
	  }

	  public void setDefinition(String definition) {
	    this.definition = definition;
	  }

	  public String getDescription() {
	    return description;
	  }

	  public void setDescription(String description) {
	    this.description = description;
	  }

	  public int getId() {
	    return id;
	  }

	  public void setId(int id) {
	    this.id = id;
	  }

	  public String getTestExecIssue() {
	    return testExecIssue;
	  }

	  public void setTestExecIssue(String testExecIssue) {
	    this.testExecIssue = testExecIssue;
	  }

	
}
