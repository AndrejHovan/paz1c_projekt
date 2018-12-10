package sk.upjs.ed;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import sk.upjs.ed.entity.Doucovatel;
import sk.upjs.ed.persistent.DaoFactory;
import sk.upjs.ed.persistent.DoucovatelDao;

public class TeacherEditController {

	private DoucovatelDao doucovatelDao = DaoFactory.INSTANCE.getDoucovatelDao();
	private Doucovatel doucovatel;
	//private DoucovatelFxModel doucovatelModel;
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private CheckBox isActiveCheckBox;

    @FXML
    private Button saveAsNewButton;

    @FXML
    private Button saveButton;

    @FXML
    private ListView<?> subjectsList;

    @FXML
    private Button addSubjectButton;

    @FXML
    void initialize() {
        

    }
}
