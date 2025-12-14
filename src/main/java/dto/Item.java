package dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Item 
{
	@JsonProperty("@odata.type")
	private String odataType;
	@JsonProperty("@microsoft.graph.conflictBehavior")
    private String microsoftGraphConflictBehavior;
    private String name;
    
	public Item(String odataType, String microsoftGraphConflictBehavior, String name) {
		super();
		this.odataType = odataType;
		this.microsoftGraphConflictBehavior = microsoftGraphConflictBehavior;
		this.name = name;
	}
    
    public Item() {}

	public String getOdataType() {
		return odataType;
	}

	public String getMicrosoftGraphConflictBehavior() {
		return microsoftGraphConflictBehavior;
	}

	public String getName() {
		return name;
	}

	public void setOdataType(String odataType) {
		this.odataType = odataType;
	}

	public void setMicrosoftGraphConflictBehavior(String microsoftGraphConflictBehavior) {
		this.microsoftGraphConflictBehavior = microsoftGraphConflictBehavior;
	}

	public void setName(String name) {
		this.name = name;
	}
}
