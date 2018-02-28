/*
 * TCSS 360 - Winter - Smallest Team
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
 *  Adapted from Internship Project that is my data basic class's final project.
 *  Class information: TCSS 445 Database System Design
 *  Professor: Anderson, Kevin.
 *  
 * Database functionality for the Project aspect of the DiyProjectReview Program.
 * 
 * @author Hui Ting Cai
 * @version February 23, 2018
 */
public class ProjectDB {
	private static String userName = "caih6"; 
	private static String password = "huiting";
	private static String serverName = "cssgate.insttech.washington.edu";
	private static Connection conn;
	private List<Project> list;
	private List<String> projectIDList;
	
	/**
	 * Copy from Internship Project that is my data basic class's final project.
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
	 * Adapted from Internship Project that is my data basic class's final project.
	 * 
	 * Returns a list of projects from the database of ProjectList.
	 * @return a list of projects from the database of ProjectList.
	 * @throws SQLException if database query fails
	 */
	public List<Project> getProject() throws SQLException {
		if (conn == null) {
			createConnection();
		}
		Statement stmt = null;
		String query = "SELECT ProjectID, ProjectName, TotalCost, ItemsNumber " + "FROM caih6.Project ";
		list = new ArrayList<Project>();
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				String projectID = rs.getString("ProjectID");
				String projectName = rs.getString("ProjectName");
				double totalCost = rs.getDouble("TotalCost");
				int itemsNumber = rs.getInt("ItemsNumber");
				
				Project project = new Project(projectID, projectName, totalCost, itemsNumber);
				list.add(project);
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
	 *  Returns a total cost of projects from the database of ProjectList.
	 * @param projectId
	 * @return
	 * @throws SQLException
	 */
	public double getTotalCost(String projectId) throws SQLException{
		double totalCost = 0;
		boolean hasProjectId = false;
		if (conn == null) {
			createConnection();
		}
		Statement stmt = null;
		String query = "SELECT ProjectID, ProjectName, TotalCost, ItemsNumber " + "FROM caih6.Project ";
		list = new ArrayList<Project>();
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				String projectID = rs.getString("ProjectID");
				String projectName = rs.getString("ProjectName");
				double total = rs.getDouble("TotalCost");
				int itemsNumber = rs.getInt("ItemsNumber");
				if(projectId.equals(projectID)){
					totalCost = total;
					hasProjectId = true;
				}
			}
			
			// DataBase of ProjectList does not contain any project by that ID.
			if(!hasProjectId){ 
				totalCost = -1;
			}
		} catch (SQLException e) {
			System.out.println(e);
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return totalCost;
	}
	
	
	/**
	 * Returns a projectID list from the database of ProjectList.
	 * @return a projectID list from the database of ProjectList.
	 * @throws SQLException if database query fails
	 */
	public List<String> getProjectID() throws SQLException {
		if (conn == null) {
			createConnection();
		}
		Statement stmt = null;
		String query = "SELECT ProjectID " + "FROM caih6.Project ";
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
	 * Returns Project described by {@code projectID}.
	 * @param projectID identification number of desired project
	 * @return Project described by {@code projectID}.
	 */
	public Project getProject(String projectID) {
		Project project =null;
		
		try {
			list = getProject();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (Project project1 : list) {
			if (project1.getProjectID().contains(projectID)) {
				project = project1;
			}
		}
		return project;
	}
	
	
	/**
	 * Deletes {@code projectID} from the database of ProjectList.
	 * @param projectID identification number of project to be deleted
	 * @param showMessage true if successful delete; else false
	 */
	public void removeProejct(String projectID, boolean showMessage) {
		boolean hasProject =false;
		List<String> projectIdList1 = null;

		try {
			projectIdList1 = getProjectID();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// check if db of ProjectList contains the project
		for(String s : projectIdList1){ 
			if(s.contains(projectID)){
				hasProject = true;
			}
		}
		
		if(!hasProject){
			JOptionPane.showMessageDialog(null, "DataBase does not contain any project by that ID.");
		} else {
			String sql = "DELETE FROM caih6.Project WHERE ProjectID = ?";
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
	 * Compare two project and return the gap  between two project
	 * @param project1ID 
	 * 						the first projectId the user enter
	 * @param project2ID 
	 * 						the second projectId the user enter
	 * @param showMessage 
	 * 						show message if true. Otherwise, don't show the message
	 * @return isSuccess
	 * 						the compare is success?
	 * 			
	 */
	public boolean compareProjects(String project1ID, String project2ID, boolean showMessage){
		boolean hasProject1= false;
		boolean hasProject2 = false;
		boolean isSuccess = false;
		
		List<String> projectIdList = null;
		try {
			projectIdList = getProjectID();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// check if db of ProjectList contains the project.
		for(String s : projectIdList){ 
			if(s.contains(project1ID)){
				hasProject1 = true;
			}
			
			if(s.contains(project2ID)){
				hasProject2 = true;
			}
		}
		
		if(!hasProject1){
			JOptionPane.showMessageDialog(null, "DataBase does not contain any project by first input ID.");
		} 
		
		if(!hasProject2){
			JOptionPane.showMessageDialog(null, "DataBase does not contain any project by second input ID.");
		} 
		
		if (hasProject1 && hasProject2) {
			isSuccess = true;
			double project1Total = 0;
			double project2Total = 0;
			try {
			    project1Total = getTotalCost(project1ID);
				project2Total = getTotalCost(project2ID);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			double grap = project1Total - project2Total;
			JOptionPane.showMessageDialog(null,
					"Project ID: " + project1ID + "\nProject ID: " + project2ID + "\nGrap: " + grap + " $");

		}
		return isSuccess;
	}
	
	/**
	 * Adapted from Internship Project that is my data basic class's final project.
	 * 
	 * Adds a new project to the table.
	 * @param project
     * @param showMessage true
	 */
	public boolean addProject(Project project, boolean showMessage, double total, int itmesNum) {
		boolean hasProject = false;
		boolean isSuccess = false;
		
		List<String> projectIdList2 = null;
		try{
			projectIdList2 = getProjectID();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		// Check the db of ProjectList contains this project ?
		for(String projectId : projectIdList2){
			if(projectId.contains(project.getProjectID())){
				hasProject = true;
			}
		}
		
		// the db of ProjectList contains this project. The show a error message
		if(hasProject){
			JOptionPane.showMessageDialog(null, "A project by that ID already exists!");
		} else {
			String sql = "INSERT INTO caih6.Project (ProjectID, ProjectName, TotalCost, ItemsNumber) VALUES "
					+ "(?, ?, ?, ?); ";
			PreparedStatement preparedStatement = null;
			try {
				preparedStatement = conn.prepareStatement(sql);
				preparedStatement.setString(1, project.getProjectID());
				preparedStatement.setString(2, project.getProjectName());
				preparedStatement.setDouble(3, project.getTotalCost());
				preparedStatement.setInt(4, project.getItemsNumber());
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
				JOptionPane.showMessageDialog(null,
						"Added Successfully!\nTotal: " + total + " $\nTotal Items: " + itmesNum + " itmes\n");
			}
		}
		
		return isSuccess ;
	}
	
	/**
	 * Adapted from Internship Project that is my data basic class's final project.
	 * 
	 * Modifies the project information corresponding to the index in the list.
	 * @param row index of the element in the list
	 * @param columnName attribute to modify
	 * @param data supplied value to modify
	 */
	public void updateProject(int row, String columnName, Object data) {
		Project project = list.get(row);
		String projectID = project.getProjectID();
		String sql = "UPDATE caih6.Project SET " + columnName + " = ? WHERE ProjectID= ? ";
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
