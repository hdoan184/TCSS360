/*
 * TCSS 360 - Winter2017 - Smallest Team
 */
/**
 * Represents a energy bills with a projectId, JanToMar, AprToJun, JulToSep, OctToDec.
 * 
 * @author Hui Ting Cai
 * @version February 23, 2018
 */
public class EnergyBills {
	
	/** The  projectID: project id of energy bills. */
	private String projectID;

	/** The total of each three month's energy bills.*/
	private double janToMar, aprToJun, julToSep, octToDec;

	/**
	 * Create and initializes a energy bills
	 * 
	 * @param projectID
	 *            project id of energy bills
	 * @param janToMar
	 *            the total of energy bills from January to March.
	 * @param aprToJun
	 *            the total of energy bills from April to June.
	 * @param julToSep
	 *            the total of energy bills from July to September.
	 * @param octToDec
	 *            the total of energy bills from October to December.
	 */
	public EnergyBills(String projectID, double janToMar, double aprToJun, double julToSep, double octToDec) {

		this.setPojectID(projectID);
		this.setEnergyBillsJanToMar(janToMar);
		this.setEnergyBillsAprToJun(aprToJun);
		this.setEnergyBillsJulToSep(julToSep);
		this.setEnergyBillsOctToDec(octToDec);
	}

	/**
	 * Modifies the project id of the energy bill
	 * @param projectID
	 * 					the project identification number to update.
	 * @throws IllegalArgumentException  
	 * 					if projectID is null or not 3 digital.
	 */
	public void setPojectID(String projectID){
		if(projectID == null || projectID.length() != 3){
			throw new IllegalArgumentException("Please supply a volid projectID");
		}
		this.projectID = projectID;
	}
	
	/**
	 * Return a project ID.
	 * @return projectID
	 */
	public String getProjectID(){
		return projectID;
	}
	
	/**
	 * Set the total of energy bills from January to March.
	 * @param janToMar
	 * 				the total of energy bills from January to March.
	 * @throws IllegalArugementEsception if the total of bills is less than zero.
	 */
	public void setEnergyBillsJanToMar(double janToMar){
		if(janToMar < 0 ){
			throw new IllegalArgumentException("The total of energy bills cannot be a negative value.");
		}
		this.janToMar = janToMar;
	}
	
	/**
	 * Return the total of energy bills from January to March..
	 * @return janToMar.
	 */
	public double getEnergyBillsJanToMar(){
		return janToMar;
	}

	/**
	 * Set the total of energy bills form April to June.
	 * @param aprToJun
	 * 				the total energy bills from April to June.
	 * @throws IllegalArugementEsception if the total of energy bills is less than zero.
	 */
	public void setEnergyBillsAprToJun(double aprToJun){
		if(aprToJun < 0 ){
			throw new IllegalArgumentException("The total energy bills cannot be a negative value.");
		}
		this.aprToJun = aprToJun;
	}
	
	/**
	 * Return the total of energy bills form April to Jube.
	 * @return aprToJun.
	 */
	public double getEnergyBillsAprToJun(){
		return aprToJun;
	}
	
	/**
	 * Set the total of energy bills from July to September.
	 * @param julToSep
	 * 				the total of energy bills from July to September.
	 * @throws IllegalArugementEsception if the total of energy bills is less than zero.
	 */
	public void setEnergyBillsJulToSep(double julToSep){
		if(julToSep < 0 ){
			throw new IllegalArgumentException("The total of energy bills cannot be a negative value.");
		}
		this.julToSep = julToSep;
	}
	
	/**
	 * Return the total of energy bills from July to September.
	 * @return julToSep.
	 */
	public double getEnergyBillsJulToSep(){
		return julToSep;
	}
	
	/**
	 * Set the total of energy bills from October to December.
	 * @param octToDec
	 * 				the total of energy bills from October to December.
	 * @throws IllegalArugementEsception if the total of energy bills is less than zero.
	 */
	public void setEnergyBillsOctToDec(double octToDec){
		if(octToDec < 0 ){
			throw new IllegalArgumentException("The total of energy bills cannot be a negative value.");
		}
		this.octToDec = octToDec;
	}
	
	/**
	 * Return the total of energy bills from October to December.
	 * @return octToDec.
	 */
	public double getEnergyBillsOctToDec(){
		return octToDec;
	}
	
	public String toString() {
		return "Project [project list =" + projectID +  ", Juanury to March =" + janToMar
				+ ", April to June=" + aprToJun + ", July to September =" + julToSep 
				+ ", October to December =" + octToDec +  "]";
	}
}
