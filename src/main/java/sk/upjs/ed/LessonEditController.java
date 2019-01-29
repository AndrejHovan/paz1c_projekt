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
	private ComboBox<String> teacherComboBox;

	@FXML
	private TextField locationTextField;

	@FXML
	private TextField priceTextField;

	@FXML
	private ComboBox<String> studentComboBox;

	@FXML
	private ComboBox<String> subjectComboBox;

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
		studenti = FXCollections.observableArrayList(studentDao.getAllActive());
		ObservableList<String> menaStudentov = FXCollections.observableArrayList();
		for (int i = 0; i < studenti.size(); i++) {
			menaStudentov.add(studenti.get(i).getId() + " " + studenti.get(i).getMeno() + " " + studenti.get(i).getPriezvisko());
		}
		studentComboBox.setItems(menaStudentov);
		//defaultna hodnota comboBoxu
		if(doucovanieModel.getStudent() != null) {
			studentComboBox.getSelectionModel().select(doucovanieModel.getCeleMenoStudenta());
			studentModel = new StudentFxModel(doucovanieModel.getStudent());
		}else {
			studentComboBox.getSelectionModel().select(menaStudentov.get(0));
			studentModel = new StudentFxModel(studenti.get(0));
		}
		
		//nasetujeme doucovaniu studenta
		doucovanieModel.setStudent(studentModel.getStudent());
		
		//implementuje moznost zmeny hodnoty comboBoxu
		studentComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			//implementovana metoda pre ChangeListener
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (newValue != null) {
					//StudentFxModel studentModelTemp = new StudentFxModel(newValue);
					for (Student s : studenti) {
						if ((s.getId() + " " + s.getMeno() + " " + s.getPriezvisko()).equals(newValue)) {
							studentModel.setStudent(s);
							break;
						}
					}
					//studentModel.setStudent(newValue); 
					doucovanieModel.setStudent(studentModel.getStudent());
				}
			}
		});

		

		// comboBox pre vyber predmetov, analogicky predoslemu ComboBoxu
		predmety = FXCollections.observableArrayList(predmetDao.getAllForList());
		ObservableList<String> nazvyPredmetov = FXCollections.observableArrayList();
		for (int i = 0; i < predmety.size(); i++) {
			nazvyPredmetov.add(predmety.get(i).getNazov() + ", " + predmety.get(i).getStupenStudia());
		}
		subjectComboBox.setItems(nazvyPredmetov);

		if (doucovanieModel.getPredmet() == null) {
			predmetModel = new PredmetFxModel(predmety.get(0));
			subjectComboBox.getSelectionModel().select(nazvyPredmetov.get(0));
			doucovanieModel.setPredmet(predmetModel.getPredmet());
		} else {
			subjectComboBox.getSelectionModel().select(doucovanieModel.getNazovPredmetu());
			predmetModel = new PredmetFxModel(doucovanieModel.getPredmet());
		}

		subjectComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue,
					String newValue) {
				if (newValue != null) {
					for (int i = 0; i < predmety.size() ; i++) {
						if ((predmety.get(i).getNazov() + ", " + predmety.get(i).getStupenStudia()).equals(newValue)) {
							predmetModel = new PredmetFxModel(predmety.get(i));
							break;
						}
					}
					//predmetModel.setPredmet(newValue);
					doucovanieModel.setPredmet(predmetModel.getPredmet());
					boloUpdatenute = true;
					//update, aby boli nacitani iba ti doucovatelia, ktory dokazu doucit dany predmet
					UpdateDoucovatelov();
				}
			}
		});

		// comboBox pre vyber doucovatelov
		//Tento mi treba vysvetlit a poriadne okomentovat
		//je zlozitejsi, pretoze ponuka iba doucovateloch schopnych doucit vybrany predmet
		if(!boloUpdatenute) { //iba nazaciatku toto zbehne potom sa robi UpdateDoucovatelov().. asi sa to da aj krajsie
			doucovatelia = FXCollections.observableArrayList();
			ObservableList<String> menaDoucovatelov = FXCollections.observableArrayList();
			/*for (int i = 0; i < doucovatelia.size(); i++) {
				menaDoucovatelov.add(doucovatelia.get(i).getMeno() + " " + doucovatelia.get(i).getPriezvisko());
			}*/
			//doucovatelia.clear();
			List<Doucovatel> vsetciDoucovatelia = FXCollections.observableArrayList(doucovatelDao.getAll());
			for (Doucovatel d : vsetciDoucovatelia) {
				for (int i = 0; i < d.getPredmety().size(); i++) {
					if (predmetModel.getPredmet().getNazov().equals(d.getPredmety().get(i).getNazov())
							&& predmetModel.getStupenStudia().equals(d.getPredmety().get(i).getStupenStudia())) {
						doucovatelia.add(d);
						menaDoucovatelov.add(d.getMeno() + " " + d.getPriezvisko());
						break;
					}
				}
			}
			if (!doucovatelia.isEmpty()) {
				teacherComboBox.setItems(menaDoucovatelov);
				if (doucovanieModel.getDoucovatel() == null) {
					teacherComboBox.getSelectionModel().select(menaDoucovatelov.get(0));
					doucovatelModel = new DoucovatelFxModel(doucovatelia.get(0));
					doucovanieModel.setDoucovatel(doucovatelModel.getDoucovatel());
				} else {
					teacherComboBox.getSelectionModel().select(doucovanieModel.getCeleMenoDoucovatela());
					doucovatelModel = new DoucovatelFxModel(doucovanie.getDoucovatel());
				}
				
			}
			
		}
		
		teacherComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue,
					String newValue) {
				if (newValue != null) {
					for (int i = 0; i < doucovatelia.size() ; i++) {
						if ((doucovatelia.get(i).getMeno() + " " + doucovatelia.get(i).getPriezvisko()).equals(newValue)) {
							doucovatelModel = new DoucovatelFxModel(doucovatelia.get(i));
							break;
						}
					}
					//doucovatelModel.setDoucovatel(newValue);
					doucovanieModel.setDoucovatel(doucovatelModel.getDoucovatel());
				}
			}
		});
		
		//bindovanie hodnot
		durationTextField.textProperty().bindBidirectional(doucovanieModel.trvanieProperty(),
				new NumberStringConverter());
		fieldTextField.textProperty().bindBidirectional(doucovanieModel.okruhProperty());
		locationTextField.textProperty().bindBidirectional(doucovanieModel.lokaciaProperty());
		priceTextField.textProperty().bindBidirectional(doucovanieModel.cenaProperty(), new NumberStringConverter());
		startDatePicker.valueProperty().bindBidirectional(doucovanieModel.zaciatokProperty());
		timeTextField.textProperty().bindBidirectional(doucovanieModel.casProperty());

		//tlacidlo na ulozenie
		saveButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				doucovanieDao.save(doucovanieModel.getDoucovanie());
				saveButton.getScene().getWindow().hide();
			}
		});
		
		//tlacidlo na vycistenie controllera
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
		
		//pridat doucovanie ako nove
		addAsNewButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				doucovanieDao.add(doucovanieModel.getDoucovanie());
				addAsNewButton.getScene().getWindow().hide();
			}
		});

	}

	//metoda, ktora zaistuje, aby comboBox pre doucovatelov zobrazil iba tych, ktori su schopny doucit vybrany predmet
	private void UpdateDoucovatelov() {
		doucovatelia.clear();
		List<Doucovatel> vsetciDoucovatelia = FXCollections.observableArrayList(doucovatelDao.getAll());
		ObservableList<String> menaDoucovatelov = FXCollections.observableArrayList();
		//hlada sa, ci sa v kompetenciach doucovatela nachadza dany predmet na danom stupni studia
		//ak ano, tak sa prida k relevantnym doucovatelom
		for (Doucovatel d : vsetciDoucovatelia) {
			for (int i = 0; i < d.getPredmety().size(); i++) {
				//mozno by bolo jasnejsie, keby predmetModel bol vstup do funkcie
				if (predmetModel.getPredmet().getNazov().equals(d.getPredmety().get(i).getNazov())
						&& predmetModel.getStupenStudia() == d.getPredmety().get(i).getStupenStudia()) {
					doucovatelia.add(d);
					menaDoucovatelov.add(d.getMeno() + " " + d.getPriezvisko());
					break;
					//asi stacilo len podla ID .. 
				}
			}
		}
		//teraz sa do comboBoxu nasetuju relevantni doucovatelia
		teacherComboBox.setItems(menaDoucovatelov);
		if(doucovatelia.size() > 0) {
			teacherComboBox.getSelectionModel().select(menaDoucovatelov.get(0));
			teacherComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue,
						String newValue) {
					if (newValue != null) {
						for (int i = 0; i < doucovatelia.size() ; i++) {
							if ((doucovatelia.get(i).getMeno() + " " + doucovatelia.get(i).getPriezvisko()).equals(newValue)) {
								doucovatelModel = new DoucovatelFxModel(doucovatelia.get(i));
								break;
							}
						}
						//doucovatelModel.setDoucovatel(newValue);
						doucovanieModel.setDoucovatel(doucovatelModel.getDoucovatel());
					}
				}
			});
		}

		

	}

}
