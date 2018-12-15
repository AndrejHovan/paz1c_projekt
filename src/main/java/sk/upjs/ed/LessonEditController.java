package sk.upjs.ed;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.Property;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import sk.upjs.ed.entity.Doucovanie;
import sk.upjs.ed.entity.DoucovanyPredmet;
import sk.upjs.ed.entity.Doucovatel;
import sk.upjs.ed.entity.Student;
import sk.upjs.ed.entity.StupenStudia;
import sk.upjs.ed.persistent.DaoFactory;
import sk.upjs.ed.persistent.DoucovanieDao;
import sk.upjs.ed.persistent.DoucovanyPredmetDao;
import sk.upjs.ed.persistent.DoucovatelDao;
import sk.upjs.ed.persistent.StudentDao;

public class LessonEditController {

	private StudentDao studentDao = DaoFactory.INSTANCE.getStudentDao();
	private Student student;
	private StudentFxModel studentModel;
	private ObservableList<Student> studenti;

	private DoucovanyPredmetDao predmetDao = DaoFactory.INSTANCE.getPredmetDao();
	private PredmetFxModel predmetModel;
	private DoucovatelDao doucovatelDao = DaoFactory.INSTANCE.getDoucovatelDao();
	private Doucovatel doucovatel;
	private DoucovatelFxModel doucovatelModel;
	private ObservableList<Doucovatel> doucovatelia;

	private DoucovanieDao doucovanieDao = DaoFactory.INSTANCE.getDoucovanieDao();
	private Doucovanie doucovanie;
	private DoucovanieFxModel doucovanieModel;
	private ObservableList<DoucovanyPredmet> predmety;

	@FXML
	private TextField durationTextField;

	@FXML
	private TextField fieldTextField;

	@FXML
	private DatePicker startDatePicker;

	@FXML
	private Button saveButton;

	@FXML
	private Button addAsNewButton;

	@FXML
	private Button clearButton;

	@FXML
	private Button deleteButton;

	@FXML
	private ComboBox<Doucovatel> teacherComboBox;

	@FXML
	private TextField locationTextField;

	@FXML
	private TextField priceTextField;

	@FXML
	private ComboBox<Student> studentComboBox;

	@FXML
	private ComboBox<DoucovanyPredmet> subjectComboBox;

	@FXML
	void clearButtonClicked(ActionEvent event) {

	}

	@FXML
	void deleteButtonClicked(ActionEvent event) {

	}

	@FXML
	void saveButtonClicked(ActionEvent event) {

	}

	@FXML
	void saveNewDocuovanieButtonClicked(ActionEvent event) {

	}

	public LessonEditController(Doucovanie doucovanie) {
		this.doucovanie = doucovanie;
		this.doucovanieModel = new DoucovanieFxModel(doucovanie);

	}

	@FXML
	void initialize() {
		// comboBox pre vyber studentov
		studenti = FXCollections.observableArrayList(studentDao.getAll());
		studentComboBox.setItems(studenti);
		studentComboBox.getSelectionModel().select(studenti.get(0));

		studentComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Student>() {

			@Override
			public void changed(ObservableValue<? extends Student> observable, Student oldValue, Student newValue) {
				if (newValue != null) {
					studentModel.setStudent(newValue);
				}
			}
		});

		if (!studenti.isEmpty()) {
			studentComboBox.getSelectionModel().select(studenti.get(0));
		}
		// comboBox pre vyber doucovatelov
		doucovatelia = FXCollections.observableArrayList(doucovatelDao.getAll());
		teacherComboBox.setItems(doucovatelia);
		teacherComboBox.getSelectionModel().select(doucovatelia.get(0));

		teacherComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Doucovatel>() {

			@Override
			public void changed(ObservableValue<? extends Doucovatel> observable, Doucovatel oldValue,
					Doucovatel newValue) {
				if (newValue != null) {
					doucovatelModel.setDoucovatel(newValue);
					UpdatePredmety();
				}
			}
		});

		if (!doucovatelia.isEmpty()) {
			teacherComboBox.getSelectionModel().select(doucovatelia.get(0));
		}
		
		 /*
		// comboBox pre vyber predmetov
		predmety = (ObservableList<DoucovanyPredmet>) doucovatelia.get(0).getPredmety();
		subjectComboBox.setItems(predmety);
		// subjectComboBox.getSelectionModel().select(predmety.get(0));
		subjectComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<DoucovanyPredmet>() {

			@Override
			public void changed(ObservableValue<? extends DoucovanyPredmet> observable, DoucovanyPredmet oldValue,
					DoucovanyPredmet newValue) {
				if (newValue != null) {
					predmetModel.setPredmet(newValue);
				}
			}
		});

		if (!predmety.isEmpty()) {
			subjectComboBox.getSelectionModel().select(predmety.get(0));
		}
	*/
		// trvanie je int, neviem, ako sa int dava do textfieldu, trvanieProperty vracia
		// integer property
		// durationTextField.textProperty().bindBidirectional((Property<String>)
		// doucovanieModel.trvanieProperty().asString());
		fieldTextField.textProperty().bindBidirectional(doucovanieModel.okruhProperty());
		locationTextField.textProperty().bindBidirectional(doucovanieModel.lokaciaProperty());
		// priceTextField.textProperty().bindBidirectional((Property<String>)
		// doucovanieModel.cenaProperty().asString());

	}

	private void UpdatePredmety() {
		predmety = (ObservableList<DoucovanyPredmet>) doucovatelModel.getPredmety();
		subjectComboBox.setItems(predmety);
		subjectComboBox.getSelectionModel().select(predmety.get(0));
		subjectComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<DoucovanyPredmet>() {

			@Override
			public void changed(ObservableValue<? extends DoucovanyPredmet> observable, DoucovanyPredmet oldValue,
					DoucovanyPredmet newValue) {
				if (newValue != null) {
					predmetModel.setPredmet(newValue);
				}
			}
		});

		if (!predmety.isEmpty()) {
			subjectComboBox.getSelectionModel().select(predmety.get(0));
		}
	}

}
