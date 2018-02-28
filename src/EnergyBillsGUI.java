/*
 * TCSS 360 - Winter2017 - Smallest Team
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
 *  Adapted from Internship Project that is my data basic class's final project.
 *  Class information: TCSS 445 Database System Design
 *  Professor: Anderson, Kevin.
 *  
 * Creates panel for energy bills list.
 *
 * @author Hui Ting Cai;
 * @version February 23, 2018
 */
public class EnergyBillsGUI extends JPanel implements ActionListener, TableModelListener{

	/** A generated serial version UID for object Serialization.*/
	private static final long serialVersionUID = -1526493556393294112L;
	
	/** Attribute names for the energyBills. */
	private static final String[] columnNames = { "ProjectID", "Jan to Mar", "Apr to Jun", "Jul to Sep", "Oct to Dec" };

	/** Buttons to list, delete, add energyBills, and projectList */
	private JButton btnList, btnDelete, btnAdd, btnProjectsList;

	/** Panels for organization of outer panel. */
	private JPanel pnlButtons, pnlContent;

	/** Database to retrieve and update energyBills information. */
	private EnergyBillDB db;
	
	/** Storage for database energyBills query. */
	private List<EnergyBills> list;
	
	/**Storage for energyBills attribute data. */
	private Object[][] data;
	
	/** Container for energyBills attribute data.*/
	private JTable table;
	
	/** Pane surrounding the energyBills data table.*/
	private JScrollPane scrollPane;
	
	/** Main content panel for delete tab. */
	private JPanel pnlDelete;

	/** Label stating what to enter in order to delete. */
	private JLabel lblTitle;

	/** Field for identifying the energyBills to delete. */
	private JTextField txfTitle;

	/** Button to delete a energyBills. */
	private JButton btnTitleDelete;

	/** Container for adding data for a energyBills. */
	private JPanel pnlAdd;

    /** Labels for energyBills information data. */
	private JLabel[] txfLabel = new JLabel[5];

    /** Editable fields for energyBills information data. */
	private JTextField[] txfField = new JTextField[5];

	/** Button to add a energyBills to the database. */
	private JButton btnAddEnergyBills;
	
	/** The diy Project view.*/
	private DiyProjectGUI diyProjectGUI;

	/**
	 *  Creates JPanel
	 * @param diyProjectGUI the Diy project view.
	 */
	public EnergyBillsGUI(DiyProjectGUI diyProjectGUI){
		this();
		this.diyProjectGUI = diyProjectGUI;
	}
	
	/**
	 * Adapted from Internship Project that is my data basic class's final project.
	 * 
	 * Creates JPanel and set basic EnergyBills gui structure with retrieved energy bill data.
	 */
	public EnergyBillsGUI(){
		db = new EnergyBillDB();
		try {
			//A list of energy bills from the database of EnergyBills.
			list = db.getEnergyBills();
			
			// [row][columnName] = [projects] [ attributes]
			data = new Object[list.size()][columnNames.length];
			for(int i = 0; i<list.size(); i++){
				data[i][0] = list.get(i).getProjectID();
				data[i][1] = list.get(i).getEnergyBillsJanToMar();
				data[i][2] = list.get(i).getEnergyBillsAprToJun();
				data[i][3] = list.get(i).getEnergyBillsJulToSep();
				data[i][4] = list.get(i).getEnergyBillsOctToDec();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		layoutComponents();
		setVisible(true);
		setSize(1200,600);
	}
	
	/**
	 * Adapted from Internship Project that is my data basic class's final project.
	 * 
	 * Positions the various GUI components
	 */
	private void layoutComponents(){
		
		pnlButtons = new JPanel();
		
		// Create buttons
		btnList = new JButton("Bills List");
		btnDelete = new JButton("   Delete  ");
		btnAdd = new JButton("   Add   ");
		//btnCompare = new JButton("  Compare  ");
		btnProjectsList = new JButton("Project List");
		
		// Add action listener to buttons
		btnList.addActionListener(this);
		btnDelete.addActionListener(this);
		btnAdd.addActionListener(this);
		//btnCompare.addActionListener(this);
		btnProjectsList.addActionListener(this);
		
		// Add the buttons into panel button
		pnlButtons.add(btnList);
		pnlButtons.add(btnDelete);
		pnlButtons.add(btnAdd);
		//pnlButtons.add(btnCompare);
		pnlButtons.add(btnProjectsList);
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

		// Add tab
		pnlAdd = new JPanel();
		pnlAdd.setLayout(new GridLayout(6,0));
		for (int i = 0; i< columnNames.length; i++){
			JPanel panel = new JPanel();
			txfLabel[i] = new JLabel(columnNames[i] + ": ");
			txfLabel[i].setPreferredSize(new Dimension(100,20));
			txfField[i] = new JTextField(25);
			panel.add(txfLabel[i]);
			panel.add(txfField[i]);
			pnlAdd.add(panel);
		}

		JPanel panel = new JPanel();
		btnAddEnergyBills = new JButton("Add");
		btnAddEnergyBills.addActionListener(this);
		panel.add(btnAddEnergyBills);
		pnlAdd.add(panel);
		add(pnlContent, BorderLayout.CENTER);
	}
	
	/**
	 * Adapted from Internship Project that is my data basic class's final project.
	 * 
	 * Initiates appropriate actions corresponding with various button presses.
	 * @param e buttons press event.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnList){
			try {
				list = db.getEnergyBills();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			data = new Object[list.size()][columnNames.length];
			for(int i = 0; i<list.size(); i++){
				data[i][0] = list.get(i).getProjectID();
				data[i][1] = list.get(i).getEnergyBillsJanToMar();
				data[i][2] = list.get(i).getEnergyBillsAprToJun();
				data[i][3] = list.get(i).getEnergyBillsJulToSep();
				data[i][4] = list.get(i).getEnergyBillsOctToDec();
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
			
		} else if (e.getSource() == btnProjectsList){
	        pnlButtons.removeAll();
	        diyProjectGUI.changePanel(diyProjectGUI.projectListGUI, "Project List View");
	        diyProjectGUI.createPanel(false);
	        
		} else if (e.getSource() == btnTitleDelete){
			String projectId = txfTitle.getText();
			// the energyBills id is 3 digital.
			if(projectId.length() == 3){ 
				db. removeProejct(projectId, true);
				txfTitle.setText("");
			} else { // show a error message.
				JOptionPane.showMessageDialog(null, "ProjectID is 3 digital");
			}
			
		} else if (e.getSource() == btnAddEnergyBills){
			String projectID = txfField[0].getText();
			boolean addSuccess = false;
			if(projectID.length() == 3){
				EnergyBills energyBills = new EnergyBills(projectID, Double.parseDouble(txfField[1].getText()), Double.parseDouble(txfField[2].getText()),
						Double.parseDouble(txfField[3].getText()), Double.parseDouble(txfField[4].getText()));
				addSuccess = db.addEnergyBills(energyBills, true);
				if(addSuccess){
					for (int i=0; i<txfField.length; i++) {
						txfField[i].setText("");
					}
				}
		
			}else {
				JOptionPane.showMessageDialog(null, "StudentID is 3 digital");
			}
		}
	}

    /**
     * Copy from Internship Project that is my data basic class's final project.
     * 
     * Monitors the table for any change in data, updates database as required.
     * @param e table change event
     */
	@Override
	public void tableChanged(TableModelEvent e) {
		int row = e.getFirstRow();
        int column = e.getColumn();
        TableModel model = (TableModel)e.getSource();
        String columnName = model.getColumnName(column);
        Object data = model.getValueAt(row, column);
        
        db.updateEnergyBills(row, columnName, data);
		
	}
}
