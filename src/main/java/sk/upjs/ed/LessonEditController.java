package sk.upjs.ed;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.Property;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.util.converter.NumberStringConverter;
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
	
	private boolean boloUpdatenute;

	@FXML
	private TextField durationTextField;

	@FXML
	private TextField fieldTextField;

	@FXML
	private TextField timeTextField;

	@FXML
	private DatePicker startDatePicker;

	@FXML
	private Button saveButton;

	@FXML
	private Button addAsNewButton;

	@FXML
	private Button clearButton;

	
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
	void saveButtonClicked(ActionEvent event) {

	}

	@FXML
	void saveNewDocuovanieButtonClicked(ActionEvent event) {

	}

	@FXML
	void clearButtonClicked(ActionEvent event) {

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
					StudentFxModel studentSkuska = new StudentFxModel(newValue);
					studentModel = studentSkuska; // teraz je ok nechapem...
				}
			}
		});

		/*
		 * if (!studenti.isEmpty()) {
		 * studentComboBox.getSelectionModel().select(studenti.get(0)); }
		 */
		/*
		 * if (!doucovatelia.isEmpty()) {
		 * teacherComboBox.getSelectionModel().select(doucovatelia.get(0)); }
		 */

		// comboBox pre vyber predmetov
		predmety = FXCollections.observableArrayList(predmetDao.getAll());
		subjectComboBox.setItems(predmety);

		if (predmetModel == null) {
			predmetModel = new PredmetFxModel(predmety.get(0));
			subjectComboBox.getSelectionModel().select(predmety.get(0));
			//UpdateDoucovatelov();
			System.out.println("Som null");
		}

		subjectComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<DoucovanyPredmet>() {

			@Override
			public void changed(ObservableValue<? extends DoucovanyPredmet> observable, DoucovanyPredmet oldValue,
					DoucovanyPredmet newValue) {
				if (newValue != null) {
					PredmetFxModel predmetModelTemp = new PredmetFxModel(newValue);
					predmetModel = predmetModelTemp;
					/*predmetModel.setId(newValue.getId());
					predmetModel.setNazov(newValue.getNazov());
					predmetModel.setStupenStudia(newValue.getStupenStudia());
					System.out.println(newValue.toString());
					System.out.println(predmetModel.getPredmet().toString());*/
					UpdateDoucovatelov();
					boloUpdatenute = true;
				}
			}
		});

		/*
		 * if (!predmety.isEmpty()) {
		 * subjectComboBox.getSelectionModel().select(predmety.get(0)); }
		 */

		// comboBox pre vyber doucovatelov
		//List<Doucovatel> prazdnyListDoucovatelov = new ArrayList<>();
		if(!boloUpdatenute) {
			doucovatelia = FXCollections.observableArrayList(doucovatelDao.getAll());
			doucovatelia.clear();
			List<Doucovatel> vsetciDoucovatelia = FXCollections.observableArrayList(doucovatelDao.getAll());
			for (Doucovatel d : vsetciDoucovatelia) {
				for (int i = 0; i < d.getPredmety().size(); i++) {
					if (predmetModel.getPredmet().getNazov().equals(d.getPredmety().get(i).getNazov())
							&& predmetModel.getStupenStudia() == d.getPredmety().get(i).getStupenStudia()) {
						doucovatelia.add(d);
						System.out.println(predmetModel.getNazov());
						break;
					}
				}
			}
			teacherComboBox.setItems(doucovatelia);
			teacherComboBox.getSelectionModel().select(doucovatelia.get(0));
		}
		
		teacherComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Doucovatel>() {

			@Override
			public void changed(ObservableValue<? extends Doucovatel> observable, Doucovatel oldValue,
					Doucovatel newValue) {
				if (newValue != null) {
					DoucovatelFxModel doucovatelModelTemp = new DoucovatelFxModel(newValue);
					doucovatelModel = doucovatelModelTemp;
				}
			}
		});
		

		/*
		 * List<Doucovatel> prazdnyListDoucovatelov = new ArrayList<>(); doucovatelia =
		 * FXCollections.observableArrayList(prazdnyListDoucovatelov); List<Doucovatel>
		 * vsetciDoucovatelia =
		 * FXCollections.observableArrayList(doucovatelDao.getAll()); for (Doucovatel d
		 * : vsetciDoucovatelia) { for (int i = 0; i<d.getPredmety().size(); i++) {
		 * if(predmetModel.getPredmet().getNazov().equals(d.getPredmety().get(i).
		 * getNazov()) && predmetModel.getStupenStudia() ==
		 * d.getPredmety().get(i).getStupenStudia()) { doucovatelia.add(d); break; } } }
		 * //doucovatelia = FXCollections.observableArrayList(doucovatelDao.getAll());
		 * if(!doucovatelia.isEmpty()) { teacherComboBox.setItems(doucovatelia);
		 * teacherComboBox.getSelectionModel().select(doucovatelia.get(0));
		 * 
		 * teacherComboBox.getSelectionModel().selectedItemProperty().addListener(new
		 * ChangeListener<Doucovatel>() {
		 * 
		 * @Override public void changed(ObservableValue<? extends Doucovatel>
		 * observable, Doucovatel oldValue, Doucovatel newValue) { if (newValue != null)
		 * { doucovatelModel.setDoucovatel(newValue); } } }); }
		 */

		// toto by malo spravne konvertovat int property na string, podobne neskor s
		// double property
		durationTextField.textProperty().bindBidirectional(doucovanieModel.trvanieProperty(),
				new NumberStringConverter());
		fieldTextField.textProperty().bindBidirectional(doucovanieModel.okruhProperty());
		locationTextField.textProperty().bindBidirectional(doucovanieModel.lokaciaProperty());
		priceTextField.textProperty().bindBidirectional(doucovanieModel.cenaProperty(), new NumberStringConverter());
		startDatePicker.valueProperty().bindBidirectional(doucovanieModel.zaciatokProperty());
		timeTextField.textProperty().bindBidirectional(doucovanieModel.casProperty());

		saveButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				doucovanieDao.save(doucovanieModel.getDoucovanie());
				saveButton.getScene().getWindow().hide();
			}
		});
		
		clearButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				durationTextField.clear();
				fieldTextField.clear();
				priceTextField.clear();
				timeTextField.clear();
				locationTextField.clear();
				startDatePicker.setValue(null);
			}
		});
		
		addAsNewButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				doucovanieDao.add(doucovanieModel.getDoucovanie());
				addAsNewButton.getScene().getWindow().hide();
			}
		});

	}

	private void UpdateDoucovatelov() {
		doucovatelia.clear();
		List<Doucovatel> vsetciDoucovatelia = FXCollections.observableArrayList(doucovatelDao.getAll());
		for (Doucovatel d : vsetciDoucovatelia) {
			for (int i = 0; i < d.getPredmety().size(); i++) {
				if (predmetModel.getPredmet().getNazov().equals(d.getPredmety().get(i).getNazov())
						&& predmetModel.getStupenStudia() == d.getPredmety().get(i).getStupenStudia()) {
					doucovatelia.add(d);
					System.out.println(predmetModel.getNazov());
					break;
				}
			}
		}
		teacherComboBox.setItems(doucovatelia);
		if(doucovatelia.size() > 0) {
			teacherComboBox.getSelectionModel().select(doucovatelia.get(0));
			teacherComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Doucovatel>() {

				@Override
				public void changed(ObservableValue<? extends Doucovatel> observable, Doucovatel oldValue,
						Doucovatel newValue) {
					if (newValue != null) {
						doucovatelModel.setDoucovatel(newValue);
					}
				}
			});
		}

		

	}

}
