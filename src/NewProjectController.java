import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * A controller class for the project screen of the application.
 * Controls all the buttons and what data is displayed to the user.
 * Created for TCSS 360 - Winter 2018
 * @author
 */
public class NewProjectController {

    /**
     * The list containing all the user created projects
     */
    private List<Project> myList = new ArrayList<Project>();

    /**
     * A button to go back to the main screen
     */
    @FXML
    private Button goBackToMain;

    /**
     * A TextArea for showing the cost of a project
     */
    @FXML
    private TextArea projectCost;

    /**
     * A TextField that allows the user to enter a project name
     */
    @FXML
    private TextField projectName;

    /**
     * A TextField that allows the user to enter a material name
     */
    @FXML
    private TextField materialName;

    /**
     * A TextField that allows the user to enter a material quantity
     */
    @FXML
    private TextField materialQuantity;

    /**
     * A TextField that allows the user to enter a material price
     */
    @FXML
    private TextField materialPrice;

    /**
     * A TableView used for displaying all materials in the open project
     */
    @FXML
    private TableView<Material> materials = new TableView<Material>();

    /**
     * The current project being worked on while in the "New Project" screen
     */
    private Project currentProject = new Project("");

    /**
     * Keeps the loaded project "as-is". Used for overwriting
     */
    private Project loadedProject = new Project("");

    /**
     * A button used for removing materials from a project
     */
    @FXML
    private Button removeButton;

    /**
     * Boolean that tells the program if this is a loaded project or not
     */
    private boolean isLoaded = false;

    /**
     * Used if opening a project via double click in the main menu
     * @author
     * @throws IOException if the load file was not found
     */
    public void initialize() throws IOException {
        loadState();
        File load = new File("LOAD_ME.txt");
        if(load.exists())   {
            Scanner s = new Scanner(load);
            String toLoad = "";
            while(!toLoad.contains("$$$") && s.hasNextLine())  {
                toLoad = s.nextLine();
            }
            if(toLoad.contains("$$$")) {
                isLoaded = true;
                loadedProject = loadedProject.loadProject(toLoad);
                currentProject = currentProject.loadProject(toLoad);
                materials.getItems().clear();
                List<Material> list = currentProject.getProjectMaterials();
                materials.getItems().addAll(list);
                projectName.setText(currentProject.getProjectName());
                projectCost.setText(currentProject.getDollarCost());
            }
        }
        //We will clear the files contents just in case it does not get deleted.
        BufferedWriter w = new BufferedWriter(new FileWriter(load));
        w.write(""); //Clears file
        w.flush();
        w.close();
        boolean b = load.delete();
    }


    /**
     * Method executes if the user hits the "go back" button. Takes the user back to the main menu screen.
     * @author
     * @throws IOException if FXMLLoader fails
     */
    @FXML
    private void handleButtonAction() throws IOException {
        Stage stage = (Stage) goBackToMain.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Method is called when the user hits the "add" button. It grabs the data from the user entry box,
     * creates a material from it, and adds it to the current project.
     * @author
     */
    @FXML
    protected void handleAddMaterialButton()   {
        try {
            int num = Integer.parseInt(materialQuantity.getText().trim());
        } catch (Exception e)   {
            inputValidationMessage();
            return;
        }
        try{
            new BigDecimal(materialPrice.getText());
        } catch (Exception e)   {
            inputValidationMessage();
            return;
        }
        currentProject.addMaterial(materialName.getText(), Integer.parseInt(materialQuantity.getText().trim()),
                new BigDecimal(materialPrice.getText()));
        materials.getItems().add(new Material(materialName.getText(), materialQuantity.getText().trim(),
                materialPrice.getText()));
        materialName.clear();
        materialQuantity.clear();
        materialPrice.clear();
       // updateTotal();
        projectCost.setText(currentProject.getDollarCost());
    }

    /**
     * Displays an error message indicating invalid input has been entered.
     * @author
     */
    private void inputValidationMessage()  {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("An error has occurred");
        alert.setHeaderText(null);
        alert.setContentText("Invalid entries. Please assure you are entering a number " +
                "for quantity and a number/decimal for cost.");
        alert.showAndWait();
    }

    /**
     * Method executed when the user hits the "done" button. What this does is saves the current project to the
     * save file, adds it to the project list, and goes back to the main menu
     * @author
     * @throws IOException if FXMLLoader fails
     */
    @FXML
    private void handleProjectCompleteButton() throws IOException {
        if(materials.getItems().size() != 0)    {
            currentProject.setProjectName(projectName.getText());
            int success = 0;;// = myList.add(currentProject);
            for(Project p : myList) {
                if(p.toString().equals(currentProject.toString()))  {
                    success = -1;
                }
            }
            if (success == -1)   {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("An error has occurred");
                alert.setHeaderText(null);
                alert.setContentText("A project with those attributes already exists. " +
                        "Try changing materials or using a different name.");
                alert.showAndWait();
            } else {
                if(isLoaded) {
                    overwriteProject(currentProject);
                }
                myList.add(currentProject);
                saveProjectState();
                Stage stage = (Stage) goBackToMain.getScene().getWindow();
                Parent root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("An error has occurred");
            alert.setHeaderText(null);
            alert.setContentText("Please add at least one material before trying to save your project.");
            alert.showAndWait();
        }
    }

    private void overwriteProject(Project theProject) {
        myList.remove(loadedProject);
        myList.add(theProject);
    }

    /**
     * Saves all projects to the save file. Should be called before switching scenes.
     * @author
     * @throws IOException if the save file is not found
     */
    private void saveProjectState() throws IOException   {
        File file = new File("saveProject.txt");
        FileWriter wr = new FileWriter(file, false);
        wr.write(""); //Clear and update file
        for(int i = 0; i < myList.size(); i++)    {
            wr.append(myList.get(i).toString() + "\n");
        }
        wr.flush();
        wr.close();
    }

    /**
     * Reads the save file in order to display all projects on the TableView in the home screen.
     * @author
     * @throws FileNotFoundException if the save file is not found
     */
    private void loadState() throws FileNotFoundException {
        File f = new File("saveProject.txt");
        if(f.exists()) {
            Scanner s = new Scanner(f);
            while (s.hasNextLine()) {
                String line = s.nextLine();
                if (line.contains("$$$")) {
                    String name = line.substring(0, line.indexOf("$$$"));
                    line = line.substring(line.indexOf("$$$"));
                    String[] mats = line.split("%%%");
                    Project toAdd = new Project(name);
                    for (int i = 0; i < mats.length; i++) {
                        mats[i] = mats[i].replace("$$$", "");
                        toAdd.addMaterial(mats[i].substring(0, mats[i].indexOf(",")),
                                Integer.parseInt(mats[i].substring(mats[i].indexOf(",") + 1,
                                        mats[i].lastIndexOf(","))),
                                mats[i].substring(mats[i].lastIndexOf(",") + 1));
                    }
                    myList.add(toAdd);
                }
            }
        }
    }

    /**
     * Exits the application. Used for "File -> Close"
     * @author
     */
    @FXML
    private void exitApp() {
        Platform.exit();
    }

    /**
     * Opens a screen displaying information about the team that developed this application.
     * @author
     */
    @FXML
    private void aboutScreen() {
        new AboutScreen(goBackToMain.getScene().getWindow());
    }

    /**
     * Removes a material from the project.
     * @author
     */
    @FXML
    private void removeSelected()   {
        Material mat = materials.getSelectionModel().getSelectedItem();
        if(mat == null) {
            return;
        }
        currentProject.removeMaterial(mat);
        materials.getItems().remove(mat);
        //updateTotal();
        projectCost.setText(currentProject.getDollarCost());
    }

//    public Controller getEnergyController() {
//
//    }


}
