package sk.upjs.ed;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import sk.upjs.ed.entity.Student;
import sk.upjs.ed.entity.StupenStudia;
import sk.upjs.ed.persistent.DaoFactory;

import sk.upjs.ed.persistent.StudentDao;

public class StudentEditController {
	
	private StudentDao studentDao = DaoFactory.INSTANCE.getStudentDao();
	private Student student;
	private StudentFxModel studentModel;
	
	 @FXML
	 private CheckBox isActiveCheckBox;

	 @FXML
	 private ComboBox<?> stupenComboBox;

	 @FXML
	 private TextField nameTextField;

	 @FXML
	 private TextField lastNameTextField;

	 @FXML
	 private TextField emailTextField;

	 @FXML
	 private TextField phoneTextField;
	 
	 @FXML
	 private Button saveButton;


    public StudentEditController(Student student) {
    	this.student = student;
    	this.studentModel = new StudentFxModel(student);
    }

	@FXML
    void initialize() {
		//stupenComboBox.getItems().setAll(StupenStudia.values());
    	nameTextField.textProperty().bindBidirectional(studentModel.menoProperty());
    	lastNameTextField.textProperty().bindBidirectional(studentModel.priezviskoProperty());
    	emailTextField.textProperty().bindBidirectional(studentModel.emailProperty());
    	phoneTextField.textProperty().bindBidirectional(studentModel.telefonProperty());
    	isActiveCheckBox.selectedProperty().bindBidirectional(studentModel.aktivnyProperty());
    	//stupenComboBox.getSelectionModel().s
    	//este stupen combo box
    	
    	saveButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				studentDao.save(studentModel.getStudent());
				saveButton.getScene().getWindow().hide();
			}
		});
    }
}
