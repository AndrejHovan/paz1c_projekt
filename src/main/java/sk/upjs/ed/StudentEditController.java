package sk.upjs.ed;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
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
	 private ComboBox<StupenStudia> stupenComboBox;

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
		//nastavujeme hodnoty pre combobox (mozne vybratelne)
		stupenComboBox.getItems().setAll(StupenStudia.values());
		//defaultne nastavime strednu skolu
		stupenComboBox.getSelectionModel().select(StupenStudia.values()[1]);
		//aby to aj FXmodel videl tak mu to pripomenieme.. - vysvetlit
		studentModel.setStupenStudia(stupenComboBox.getSelectionModel().getSelectedItem());
		//ak zmenime vyber tak sa zmeni aj v FXmodeli
		stupenComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<StupenStudia>(){

			@Override
			public void changed(ObservableValue<? extends StupenStudia> observable, StupenStudia oldValue,
					StupenStudia newValue) {
				if(newValue != null) {
					studentModel.setStupenStudia(newValue);
				}
				
			}
			
		});
    	nameTextField.textProperty().bindBidirectional(studentModel.menoProperty());
    	lastNameTextField.textProperty().bindBidirectional(studentModel.priezviskoProperty());
    	emailTextField.textProperty().bindBidirectional(studentModel.emailProperty());
    	phoneTextField.textProperty().bindBidirectional(studentModel.telefonProperty());
    	isActiveCheckBox.selectedProperty().bindBidirectional(studentModel.aktivnyProperty());
    	
    	saveButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				studentDao.save(studentModel.getStudent());
				saveButton.getScene().getWindow().hide();
			}
		});
    }
}
