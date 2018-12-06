package sk.upjs.ed;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import sk.upjs.ed.entity.Doucovatel;
import javafx.fxml.FXML;


public class TeachersListController {

    
    @FXML
    private TableView<Doucovatel> teachersTableView;
    
    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button editButton;
    

    @FXML
    void initialize() {
        

    }
}
