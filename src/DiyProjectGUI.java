/*
 * TCSS 360 - Winter2017 - Smallest Team
 */
import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Creates GUI for Diy Project Review Program.
 * @author Hui Ting Cai
 * @version February 23, 2018
 *
 */
public class DiyProjectGUI extends JFrame{
	
	/** The Project List GUI.*/
	public ProjectListGUI projectListGUI;
	
	/** The Energy Bills GUI. */
	public EnergyBillsGUI energyBillsGUI;
	
	/** Generated serial version UID for object Serialization. */
	private static final long serialVersionUID = -563784821983183954L;
	
	/** Panels to assist in GUI organization. */
	private JPanel pnlCenter, pnlButtons, pnlTitle, pnlText1, pnlText2;

	/** User type and desired action buttons. */
	private JButton btnProjectList, btnEnergyBills;
	
	/**
	 * Create JFrame and Initializes basic GUI structure.
	 */
	public DiyProjectGUI(){
		super("DIY Project");
	
		projectListGUI = new ProjectListGUI(this);
		energyBillsGUI = new EnergyBillsGUI(this);
		pnlCenter = new JPanel(new BorderLayout());
		pnlButtons = new JPanel();
		pnlTitle = new JPanel(new BorderLayout());
		pnlText1 = new JPanel();
		pnlText2 = new JPanel();
		
		// The buttons
		btnProjectList = new JButton("Project List");
		btnEnergyBills = new JButton("Energy Bills");
		
		// The texts
		pnlText1.add(createLabel("Welcome To !", 50));
		pnlText2.add(createLabel("Diy Project", 30));
		
		btnProjectListListener();
		btnEnergyBilssListener();
	}
	
    /**
     * Returns custom label of specified length.
     * @param s text for label
     * @param length width for label
     * @return formatted label
     */
    public JLabel createLabel(String s, int length) {
        JLabel jl = new JLabel(s);
        jl.setFont(new Font("SansSerif", Font.BOLD, length));
        return jl;
    }
    
	/**
	 * Initializes JFrame values.
	 */
	public void start() {
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		LayOutComponents();
		setVisible(true);
		setSize(600, 600);
	}
	
	/**
	 * Positions the various GUI components.
	 */
	private void LayOutComponents() {
		
		// Buttons panel
		pnlButtons.add(btnProjectList);
		pnlButtons.add(btnEnergyBills);
		pnlCenter.add(pnlButtons, BorderLayout.CENTER);
		
		// Title panel
		pnlTitle.add(pnlText1, BorderLayout.NORTH);
		pnlTitle.add(pnlText2, BorderLayout.CENTER);
		
		// Add title panel into Frame.
		this.add(pnlTitle, BorderLayout.NORTH);
		
		// Add Center panel into Frame.
		this.add(pnlCenter, BorderLayout.CENTER);
	}
	
    /**
     * Replaces and redraws current JPanels with {@code jp}.
     * @param jp panel to change to
     */
	public void changePanel(JPanel jp, String title) {
        pnlCenter.removeAll();
        pnlTitle.removeAll();
        pnlCenter.add(jp);
        setTitle(title);
        pnlCenter.revalidate();
        this.repaint();
    }
	
	/**
	 * Create a new panel object. If the isProejctListPanel is true, create a
	 * project List Panel. Otherwise, create a energy bills panel.
	 * 
	 * @param isProjectListPanel 
	 */
	public void createPanel(boolean isProjectListPanel){
		if(isProjectListPanel){
			projectListGUI = new ProjectListGUI(this);
		} else {
			energyBillsGUI = new EnergyBillsGUI(this); 
		}
	}
	
/**
	public void switchPanel(JPanel jp, String title){
        pnlCenter.removeAll();
       // pnlTitle.removeAll();
        pnlCenter.add(jp);
        setTitle(title);
        pnlCenter.revalidate();
        this.repaint();
	}
*/
	
	
	/**
	 * Starts DIY Project Review GUI.
	 * @param args unused command line parameters
	 */
	public static void main(String[] args) {
		DiyProjectGUI diyProjectGUI = new DiyProjectGUI();
		diyProjectGUI.start();
	}
	
	/**
	 * Add ActionListener to Project list button.
	 */
	private void btnProjectListListener() {
		btnProjectList.addActionListener((theEvent) -> {
			changePanel(projectListGUI, "Project List View");
		});
	}
	
	/**
	 * Add ActionListener to Energy Bills button.
	 */
	private void btnEnergyBilssListener() {
		btnEnergyBills.addActionListener((theEvent) -> {
			changePanel(energyBillsGUI, "Enegry Bills View");
		});
	}
}
