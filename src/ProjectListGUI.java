/*
 * TCSS 360 - Winter - Smallest Team
 */

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

/**
 * Adapted from Internship Project that is my data basic class's final project.
 *  Class information: TCSS 445 Database System Design
 *  Professor: Anderson, Kevin.
 *  
 * Creates panel for project list.
 *
 * @author Hui Ting Cai;
 * @version February 23, 2018
 */
public class ProjectListGUI extends JPanel implements ActionListener, TableModelListener {

	/** A generated serial version UID for object Serialization. */
	private static final long serialVersionUID = 1961807587847090138L;

	/** Attribute names for the project. */
	private static final String[] columnNames = { "ProjectID", "ProjectName", "TotalCost", "ItemsNumber" };

	/** Attribute names for the project. */
	private static final String[] informations = { "ProjectID", "ProjectName", "Material", "Priece", "Material",
			"Priece", "Material", "Priece" };

	/** Buttons to list, delete, add project, compare project, and energyBills. */
	private JButton btnList, btnDelete, btnAdd, btnCompare, btnEnergyBills;

	/** Panels for organization of outer panel. */
	private JPanel pnlButtons, pnlContent;

	/** Database to retrieve and update project information. */
	private ProjectDB db;
	
	/** Storage for database project query. */
	private List<Project> list;
	
	/**Storage for project attribute data. */
	private Object[][] data;
	
	/** Container for project attribute data.*/
	private JTable table;
	
	/** Pane surrounding the project data table.*/
	private JScrollPane scrollPane;
	
	/** Main content panel for delete/compare tab. */
	private JPanel pnlDelete, pnlCompare;

	/** Label stating what to enter in order to delete. */
	private JLabel lblTitle;

	/** Field for identifying the project to delete. */
	private JTextField txfTitle;

	/** Button to delete a project. */
	private JButton btnTitleDelete;

	/** Container for adding data for a project. */
	private JPanel pnlAdd;

    /** Labels for project information data. */
	private JLabel[] txfLabel = new JLabel[8];
	
	/** Labels for compare project data.*/
	private JLabel[] txfLabelCompare = new JLabel[2];

    /** Editable fields for project information data. */
	private JTextField[] txfField = new JTextField[8];
	
	/** Editable fields for project compare data. */
	private JTextField[] txfFieldCompare = new JTextField[2];

	/** Button to add/ compare a project to the database. */
	private JButton btnAddProject, btnCompareProject;
	
	/** Total cost of project.*/
	private double total;
	
	/**The number of items for project.*/
	private int itemsNumber;
	
	/** The diyProject GUI. */
	private DiyProjectGUI diyProjectGUI;
	
	/**
	 *  Creates JPanel
	 * @param diyProjectGUI the Diy project view.
	 */
	public ProjectListGUI(DiyProjectGUI diyProjectGUI){
		this();
		this.diyProjectGUI = diyProjectGUI;
	}
	
	/**
	 * Adapted from Internship Project that is my data basic class's final project.
	 * 
	 * Creates JPanel and set basic proejctList gui structure with retrieved proejct data.
	 */
	public ProjectListGUI(){
		itemsNumber = 0;
		
		db = new ProjectDB();
		try {
			//A list of projects from the database of ProjectList.
			list = db.getProject();
			
			// [row][columnName] = [projects] [ attributes]
			data = new Object[list.size()][columnNames.length];
			for(int i = 0; i<list.size(); i++){
				data[i][0] = list.get(i).getProjectID();
				data[i][1] = list.get(i).getProjectName();
				data[i][2] = list.get(i).getTotalCost();
				data[i][3] = list.get(i).getItemsNumber();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		layoutComponents();
		setVisible(true);
		setSize(600,600);
	}
	
	/**
	 * Adapted from Internship Project that is my data basic class's final project.
	 * 
	 * Positions the various porjectList GUI components.
	 */
	private void layoutComponents(){
		pnlButtons = new JPanel();
		
		// Buttons
		btnList = new JButton("Project List");
		btnDelete = new JButton("   Delete  ");
		btnAdd = new JButton("   Add   ");
		btnCompare = new JButton("  Compare  ");
		btnEnergyBills = new JButton("Energy Bills");
		
		// Add action listener to buttons
		btnList.addActionListener(this);
		btnDelete.addActionListener(this);
		btnAdd.addActionListener(this);
		btnCompare.addActionListener(this);
		btnEnergyBills.addActionListener(this);

		// Add the buttons into panel button
		pnlButtons.add(btnList);
		pnlButtons.add(btnDelete);
		pnlButtons.add(btnAdd);
		pnlButtons.add(btnCompare);
		pnlButtons.add(btnEnergyBills);
		add(pnlButtons, BorderLayout.NORTH);
		
		// List tab
		pnlContent = new JPanel();
		table = new JTable(data, columnNames);
		scrollPane = new JScrollPane(table);
		pnlContent.add(scrollPane);
		table.getModel().addTableModelListener(this);
		
		// Delete tab
		pnlDelete = new JPanel();
		lblTitle = new JLabel("Enter ProjectID: ");
		txfTitle = new JTextField(3);
		btnTitleDelete = new JButton("Delete");
		btnTitleDelete.addActionListener(this);
		pnlDelete.add(lblTitle);
		pnlDelete.add(txfTitle);
		pnlDelete.add(btnTitleDelete);
		
		// Compare tab
		pnlCompare = new JPanel();
		pnlCompare.setLayout(new GridLayout(3,0));
		for (int i = 0; i< 2; i++){
			JPanel panel = new JPanel();
			txfLabelCompare[i] = new JLabel("Enter ProjectID: ");
			txfLabelCompare[i].setPreferredSize(new Dimension(105,20));
			txfFieldCompare[i] = new JTextField(3);
			panel.add(txfLabelCompare[i]);
			panel.add(txfFieldCompare[i]);
			pnlCompare.add(panel);
		}
		JPanel panelComp = new JPanel();
		btnCompareProject = new JButton("Compare");
		btnCompareProject.addActionListener(this);
		panelComp.add(btnCompareProject);
		pnlCompare.add(panelComp);
		
		// Add tab
		pnlAdd = new JPanel();
		pnlAdd.setLayout(new GridLayout(9,0));
		for (int i = 0; i< informations.length; i++){
			JPanel panel = new JPanel();
			txfLabel[i] = new JLabel(informations[i] + ": ");
			txfLabel[i].setPreferredSize(new Dimension(100,20));
			txfField[i] = new JTextField(25);
			panel.add(txfLabel[i]);
			panel.add(txfField[i]);
			pnlAdd.add(panel);
		}

		JPanel panel = new JPanel();
		btnAddProject = new JButton("Add");
		btnAddProject.addActionListener(this);
		panel.add(btnAddProject);
		pnlAdd.add(panel);
		add(pnlContent, BorderLayout.CENTER);
	}
	

    /**
     * Adapted from Internship Project that is my data basic class's final project.
     * 
     * Initiates appropriate actions corresponding with various button presses.
     * @param e button press event
     */
	@Override
	public void actionPerformed(ActionEvent e){
		if(e.getSource() == btnList){
			try {
				list = db.getProject();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			data = new Object[list.size()][columnNames.length];
			for(int i = 0; i<list.size(); i++){
				data[i][0] = list.get(i).getProjectID();
				data[i][1] = list.get(i).getProjectName();
				data[i][2] = list.get(i).getTotalCost();
				data[i][3] = list.get(i).getItemsNumber();
			}
			pnlContent.removeAll();
			table = new JTable(data, columnNames);
			table.getModel().addTableModelListener(this);
			scrollPane = new JScrollPane(table);
			pnlContent.add(scrollPane);
			pnlContent.revalidate();
			this.repaint();
			
		} else if (e.getSource() == btnDelete){
			pnlContent.removeAll();
			pnlContent.add(pnlDelete);
			pnlContent.revalidate();
			this.repaint();
			
		} else if (e.getSource() == btnAdd){
			pnlContent.removeAll();
			pnlContent.add(pnlAdd);
			pnlContent.revalidate();
			this.repaint();
			
		} else if (e.getSource() == btnCompare){
			pnlContent.removeAll();
			pnlContent.add(pnlCompare);
			pnlContent.revalidate();
			this.repaint();
			
		} else if (e.getSource() == btnEnergyBills){
	        pnlButtons.removeAll();
	        pnlContent.removeAll();
	        diyProjectGUI.changePanel(diyProjectGUI.energyBillsGUI, "Enegry Bills View");
	        diyProjectGUI.createPanel(true);
	        
		} else if (e.getSource() == btnTitleDelete){
			String projectId = txfTitle.getText();
			
			// the project id is 3 digital.
			if(projectId.length() == 3){ 
				db. removeProejct(projectId, true);
				txfTitle.setText("");
			} else { // show a error message.
				JOptionPane.showMessageDialog(null, "ProjectID is 3 digital");
			}
			
		} else if (e.getSource() ==btnAddProject){
			String projectID = txfField[0].getText();
			boolean addSuccess = false;
			if (projectID.length() == 3) {
				total = 0;
				itemsNumber = 0;
				
				// calculate the total cost and total items.
				for (int i = 3; i <informations.length; i= i+2 ){
					total = Double.parseDouble(txfField[i].getText()) + total;
					itemsNumber++;
				}
				
				Project project = new Project(projectID, txfField[1].getText(), total, itemsNumber);
				addSuccess = db.addProject(project, true, total, itemsNumber);
				//Set the text filed to empty. if add successful.
				if(addSuccess){
					for(int i = 0; i<txfField.length; i++){
						txfField[i].setText("");
					}
				}
				
			} else { //Show the error message to tell the user the proejectId is 3 digital.
				JOptionPane.showMessageDialog(null, "ProjectID is 3 digital");
			}
			
		} else if (e.getSource() == btnCompareProject){
			String project1ID = txfFieldCompare[0].getText();
			String project2ID = txfFieldCompare[1].getText();
			boolean compareSuccess = false;
			
			if(project1ID.length()==3){
				if(project2ID.length()==3){
					compareSuccess = db.compareProjects(project1ID, project2ID, true);
					//Set the text filed to empty. if add successful.
					if(compareSuccess){
						for(int i = 0; i<txfFieldCompare.length; i++){
							txfFieldCompare[i].setText("");
						}
					}
					
				} else {
					JOptionPane.showMessageDialog(null, "ProjectID is 3 digital");
				}
				
			} else {
				JOptionPane.showMessageDialog(null, "ProjectID is 3 digital");
			}
		}
	}

    /**
     * Copy from Internship Project that is my data basic class's final project.
     * 
     * Monitors the table for any change in data, updates database as required.
     * @param e table change event
     * 
     */
	@Override
	public void tableChanged(TableModelEvent e) {
		int row = e.getFirstRow();
        int column = e.getColumn();
        TableModel model = (TableModel)e.getSource();
        String columnName = model.getColumnName(column);
        Object data = model.getValueAt(row, column);
       
        db.updateProject(row, columnName, data);
	}
}
