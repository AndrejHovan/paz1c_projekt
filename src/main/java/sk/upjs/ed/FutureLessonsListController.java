package sk.upjs.ed;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import sk.upjs.ed.entity.Doucovanie;

public class FutureLessonsListController {


    @FXML
    private TableView<Doucovanie> lessonsTableView;
    
    @FXML
    private Button addButton;

    @FXML
    private Button editButton;

    @FXML
    private Button deleteButton;

    @FXML
    void initialize() {
        

    }
}

