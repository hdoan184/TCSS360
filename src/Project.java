/*
 * TCSS 360 - Winter2017 - Smallest Team
 */
public class Project {
	/**
	 * projectID:  project id of ProjectList.
	 * projectName: project name of ProjectList.
	 */
	private String projectID, projectName;
	
	/** The total cost of this project.*/
	private double totalCost;
	
	/** The items number of this project.*/
	private int itemsNumber;
	
	/**
	 * Create and initializes a project.
	 * @param projectID 
	 * 					the project id of this project.
	 * @param projectName
	 *   				the project name of this project.
	 * @param totalCost
	 * 					the total cost of this project.
	 * @param itemsNumber
	 * 					the items number of this project.
	 */
	public Project(String projectID, String projectName, double totalCost, int itemsNumber){
		this.setProjectID(projectID);
		this.setProjectName(projectName);
		this.setTotalCost(totalCost);
		this.setItemsNumber(itemsNumber);
	}
	
	/**
	 * Modifies the project id of the project.
	 * @param projectID
	 * 				the project identification number to update.
	 * @throws IllegalArgumentException if projectId is null or not 3 digital.
	 */
	public void setProjectID(String projectID){
		if(projectID == null || projectID.length() != 3){
			throw new IllegalArgumentException("Please supply a volid projectID.");
		}
		this.projectID = projectID;
	}
	
	/**
	 * Return a project ID.
	 * @return project ID.
	 * 	
	 */
	public String getProjectID(){
		return projectID;
	}
	
	/**
	 * Modifies the project name of the project.
	 * @param projectName
	 * @throws IllegalArgumentException if projectName is null or empty.
	 */
	public void setProjectName(String projectName){
		if(projectName == null || projectName.length() ==0){
			throw new IllegalArgumentException("Please supply a valid project name");
		}
		this.projectName = projectName;
	}
	
	/**
	 * Return project name.
	 * @return projectName.
	 */
	public String getProjectName(){
		return projectName;
	}

	/**
	 * Set the total cost of project
	 * @param totalCost
	 * @throws IllegalArugementEsception if the total cost of project is less than zero.
	 */
	public void setTotalCost(double totalCost){
		if(totalCost< 0){
			throw new IllegalArgumentException("total cost of project cannot be a negative value.");
		}
		this.totalCost = totalCost;
	}
	
	/**
	 * Return the total cost of project.
	 * @return totalCost
	 */
	public double getTotalCost(){
		return totalCost;
	}
	
	/**
	 * Set the items number of project
	 * @param itemsNumber
	 * @throws IllegalArugementException if the items number of project is less than zero.
	 */
	public void setItemsNumber(int itemsNumber){
		if(itemsNumber < 0){
			throw new IllegalArgumentException("the number of item cannot be a neagtive value.");
		}
		this.itemsNumber = itemsNumber;
	}
	
	/**
	 * Return the number of items of project.
	 * @return itemsNumber.
	 */
	public int getItemsNumber(){
		return itemsNumber;
	}

	public String toString() {
		return "Project [project list =" + projectID + ", project name=" + projectName + ", total cost=" + totalCost
				+ "number of items=" + itemsNumber + "]";
	}
}
