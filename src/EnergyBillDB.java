/*
 * TCSS 360 - Winter2017 - Smallest Team
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.swing.JOptionPane;

/**
 *  Adapted from Internship Project that is my DataBase class's final project.
 *  Class information: TCSS 445 Database System Design
 *  Professor: Anderson, Kevin.
 *  
 * Database functionality for the EnergyBills aspect of the DiyProjectReview Program.
 * 
 * @author Hui Ting Cai
 * @version February 23, 2018
 */
public class EnergyBillDB {
	private static String userName = "caih6"; 
	private static String password = "huiting";
	private static String serverName = "cssgate.insttech.washington.edu";
	private static Connection conn;
	private List<EnergyBills> list;
	private List<String> projectIDList;
	
	/**
	 * Copy from Internship Project that is my DataBase class's final project.
	 * 
	 * Creates a sql connection to MySQL using the properties for userid,
	 * password and server information.
	 * @throws SQLException if SQL connection fails
	 */
	public static void createConnection() throws SQLException {
		Properties connectionProps = new Properties();
		connectionProps.put("user", userName);
		connectionProps.put("password", password);
		conn = DriverManager.getConnection("jdbc:" + "mysql" + "://" + serverName + "/", connectionProps);
		System.out.println("Connected to database");
	}
	
	/**
	 * Adapted from Internship Project that is my DataBase class's final project.
	 *  
	 * Returns a list of energy bills from the database of EnergyBills.
	 * @return a list of energy from the database of EnergyBills
	 * @throws SQLException if database query fails
	 */
	public List<EnergyBills> getEnergyBills() throws SQLException {
		if (conn == null) {
			createConnection();
		}
		Statement stmt = null;
		String query = "SELECT ProjectID, JanToMar, AprToJun, JulToSep, OctToDec " + "FROM caih6.EnergyBills ";
		list = new ArrayList<EnergyBills>();
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				String projectID = rs.getString("ProjectID");
				double janToMar = rs.getDouble("JanToMar");
				double aprToJun = rs.getDouble("AprToJun");
				double julToSep = rs.getDouble("JulToSep");
				double octToDec = rs.getDouble("OctToDec");
				
				EnergyBills energybills = new EnergyBills(projectID, janToMar, aprToJun, julToSep, octToDec);
				list.add(energybills);
			}
		} catch (SQLException e) {
			System.out.println(e);
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return list;
	}
	
	/**
	 * Deletes {@code projectID} from the database of EnergyBills.
	 * @param projectID identification number of energy bills to be deleted
	 * @param showMessage true if successful delete; else false
	 */
	public void removeProejct(String projectID, boolean showMessage) {
		boolean hasEnergyBills =false;
		List<String> projectIdList1 = null;

		try {
			projectIdList1 = getProjectID();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// check if db of EnergyBills table contains this energy bills.
		for(String s : projectIdList1){ 
			if(s.contains(projectID)){
				hasEnergyBills = true;
			}
		}
		
		if(!hasEnergyBills){
			JOptionPane.showMessageDialog(null, "DataBase of Energy Bills does not contain any energy bills by that ID.");
		} else {
			String sql = "DELETE FROM caih6.EnergyBills WHERE ProjectID = ?";
			PreparedStatement preparedStatement = null;
			try {
				preparedStatement = conn.prepareStatement(sql);
				preparedStatement.setString(1, projectID);
				preparedStatement.executeUpdate();
			} catch (SQLException e) {
				System.out.println(e);

			} finally {
				if (preparedStatement != null) {
					try {
						preparedStatement.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
			if(showMessage) {
				JOptionPane.showMessageDialog(null, "Deleted Successfully!");
			}
		}
	}
	
	/**
	 * 
	 * Returns a projectID list from the database of energy bills.
	 * @return a projectID list from the database of energy bills
	 * @throws SQLException if database query fails
	 */
	public List<String> getProjectID() throws SQLException {
		if (conn == null) {
			createConnection();
		}
		Statement stmt = null;
		String query = "SELECT ProjectID " + "FROM caih6.EnergyBills ";
		projectIDList = new ArrayList<String>();
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				String projectID = rs.getString("ProjectID");
				projectIDList.add(projectID);
			}
		} catch (SQLException e) {
			System.out.println(e);
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return projectIDList;
	}
	
	/**
	 * Adapted from Internship Project that is my DataBase class's final project.
	 * 
	 * Adds a new energy bills to the table.
	 * @param energyBills
     * @param showMessage true
	 */
	public boolean addEnergyBills(EnergyBills energyBills, boolean showMessage) {
		boolean hasEnergyBills = false;
		boolean hasProject = false;
		boolean isSuccess = false;
		ProjectDB projectListDB = new ProjectDB();
		List<String> projectListId = null;
		List<String> enrgyBillsListId = null;
		
		try{
			// the list of ProjectId of EnrgyBill  
			enrgyBillsListId  = getProjectID();
			// the list of ProjectId of ProjectList
			projectListId = projectListDB.getProjectID();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		// Check the db of energyBills table contains this energyBills ?
		for(String enerygBillsId : enrgyBillsListId ){
			if(enerygBillsId.contains(energyBills.getProjectID())){
				hasEnergyBills = true;
			}
		}
		
		// Check the db of ProjectList table contains this energyBills?
		for (String projectId : projectListId){
			if(projectId.contains(energyBills.getProjectID())){
				hasProject = true;
			}
		}
		
		// the db of energyBills table contains this energy bills. Then show a error message
		if(hasEnergyBills){
			JOptionPane.showMessageDialog(null, "A eneryBills by that ID already exists in database of energy bills!");
		} 
		// the db of proejctList table contains this energy bills. Then show a error message.
		if(!(hasProject)){
			JOptionPane.showMessageDialog(null, "A eneryBills by that ID doesn't exist in database of proejct list!");
		}
		
		if(!(hasEnergyBills) && hasProject) {
			String sql = "INSERT INTO caih6.EnergyBills (ProjectID, JanToMar, AprToJun, JulToSep, OctToDec) VALUES "
					+ "(?, ?, ?, ?, ?); ";
			PreparedStatement preparedStatement = null;
			try {
				preparedStatement = conn.prepareStatement(sql);
				preparedStatement.setString(1, energyBills.getProjectID());
				preparedStatement.setDouble(2, energyBills.getEnergyBillsJanToMar());
				preparedStatement.setDouble(3, energyBills.getEnergyBillsAprToJun());
				preparedStatement.setDouble(4, energyBills.getEnergyBillsJulToSep());
				preparedStatement.setDouble(5, energyBills.getEnergyBillsOctToDec());
				preparedStatement.executeUpdate();
			} catch (SQLException e) {
				System.out.println(e);
			} finally {
				if(preparedStatement != null){
					try{
						preparedStatement.close();
					} catch(SQLException e){
						e.printStackTrace();
					}
				}
			}
			isSuccess = true;
			if (showMessage) {
				JOptionPane.showMessageDialog(null, "Added Successfully!");
			}
		}
		
		return isSuccess ;
	}


	/**
	 * Adapted from Internship Project that is my DataBase class's final project.
	 * 
	 * Modifies the enegyBills information corresponding to the index in the list.
	 * @param row index of the element in the list
	 * @param columnName attribute to modify
	 * @param data supplied value to modify
	 */
	public void updateEnergyBills(int row, String columnName, Object data) {
		EnergyBills energyBills = list.get(row);
		String projectID = energyBills.getProjectID();
		String sql = "UPDATE caih6.EnergyBills SET " + columnName + " = ? WHERE ProjectID= ? ";
		System.out.println(sql);
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = conn.prepareStatement(sql);
			if (data instanceof String)
				preparedStatement.setString(1, (String) data);
			else if (data instanceof Integer)
				preparedStatement.setInt(1, (Integer) data);
			preparedStatement.setString(2, projectID);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e);
			e.printStackTrace();
		}
	}
}
