package sk.upjs.ed;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sk.upjs.ed.entity.Doucovanie;
import sk.upjs.ed.entity.DoucovanyPredmet;
import sk.upjs.ed.entity.Doucovatel;
import sk.upjs.ed.entity.Student;
import sk.upjs.ed.entity.StupenStudia;
import sk.upjs.ed.persistent.DaoFactory;
import sk.upjs.ed.persistent.DoucovanieDao;
import sk.upjs.ed.persistent.DoucovatelDao;
import sk.upjs.ed.persistent.StudentDao;


public class FutureLessonsListController {

	private DoucovanieDao doucovanieDao = DaoFactory.INSTANCE.getDoucovanieDao();
	private ObservableList<Doucovanie> doucovanieModel;
	
	private StudentDao studentDao = DaoFactory.INSTANCE.getStudentDao();
	private ObservableList<Student> studentsModel;
	
	private DoucovatelDao doucovatelDao = DaoFactory.INSTANCE.getDoucovatelDao();
	private ObservableList<Doucovatel> doucovateliaModel;
	
	private Map<String, BooleanProperty> columnsVisibility = new LinkedHashMap<>();
	private ObjectProperty<Doucovanie> selectedDoucovanie = new SimpleObjectProperty<>();
	
    @FXML
    private TableView<Doucovanie> lessonsTableView;
    
    @FXML
    private Button addButton;

    @FXML
    private Button editButton;

    @FXML
    private Button deleteButton;

    /*
    private Long id;
	private LocalDateTime zaciatok;
	private int trvanie; // v minutach
	private double cena;
	private String okruh;
	private DoucovanyPredmet predmet;
	private Doucovatel doucovatel;
	private String lokacia;
	private Student student;
	*/
    @FXML
    void initialize() {
    	doucovanieModel = FXCollections.observableArrayList(doucovanieDao.getAll());
  
    	editButton.setDisable(true);
    	//stlpec pre id
    	TableColumn<Doucovanie, Long> idStlpec = new TableColumn<>("ID");
    	idStlpec.setCellValueFactory(new PropertyValueFactory<>("id"));
    	lessonsTableView.getColumns().add(idStlpec);
    	columnsVisibility.put("ID", idStlpec.visibleProperty());

    	//stlpec pre zaciatok
    	TableColumn<Doucovanie, LocalDateTime> zaciatokStlpec = new TableColumn<>("Zaciatok");
    	zaciatokStlpec.setCellValueFactory(new PropertyValueFactory<>("Zaciatok"));
    	lessonsTableView.getColumns().add(zaciatokStlpec);
    	columnsVisibility.put("Zaciatok", zaciatokStlpec.visibleProperty());
    	
    	//stlpec pre cas
    	TableColumn<Doucovanie, String> casStlpec = new TableColumn<>("Cas");
    	casStlpec.setCellValueFactory(new PropertyValueFactory<>("Cas"));
    	lessonsTableView.getColumns().add(casStlpec);
    	columnsVisibility.put("Cas", casStlpec.visibleProperty());
    	
    	//stlpec pre trvanie
    	TableColumn<Doucovanie, Long> trvanieStlpec = new TableColumn<>("Trvanie");
    	trvanieStlpec.setCellValueFactory(new PropertyValueFactory<>("Trvanie"));
    	lessonsTableView.getColumns().add(trvanieStlpec);
    	columnsVisibility.put("Trvanie", trvanieStlpec.visibleProperty());
    	
    	//stlpec pre cenu
    	TableColumn<Doucovanie, Double> cenaStlpec = new TableColumn<>("Cena");
    	cenaStlpec.setCellValueFactory(new PropertyValueFactory<>("Cena"));
    	lessonsTableView.getColumns().add(cenaStlpec);
    	columnsVisibility.put("Cena", cenaStlpec.visibleProperty());
    	
    	//stlpec pre okruh
    	TableColumn<Doucovanie, String> okruhStlpec = new TableColumn<>("Okruh");
    	okruhStlpec.setCellValueFactory(new PropertyValueFactory<>("Okruh"));
    	lessonsTableView.getColumns().add(okruhStlpec);
    	columnsVisibility.put("Okruh", okruhStlpec.visibleProperty());
    	
    	//stlpec pre lokalitu
    	TableColumn<Doucovanie, String> lokaciaStlpec = new TableColumn<>("Lokacia");
    	lokaciaStlpec.setCellValueFactory(new PropertyValueFactory<>("lokacia"));
    	lessonsTableView.getColumns().add(lokaciaStlpec);
    	columnsVisibility.put("Lokacia", lokaciaStlpec.visibleProperty());
    	
    	lessonsTableView.setItems(doucovanieModel);
    	
    	//stlpec pre studenta
    	TableColumn<Doucovanie, Student> studentStlpec = new TableColumn<>("Študent");
    	studentStlpec.setCellValueFactory(new PropertyValueFactory<>("student"));
    	lessonsTableView.getColumns().add(studentStlpec);
    	columnsVisibility.put("Študent", studentStlpec.visibleProperty());
    	
    	//stlpec pre doucovatela
    	TableColumn<Doucovanie, Doucovatel> doucovatelStlpec = new TableColumn<>("Doučovateľ");
    	doucovatelStlpec.setCellValueFactory(new PropertyValueFactory<>("doucovatel"));
    	lessonsTableView.getColumns().add(doucovatelStlpec);
    	columnsVisibility.put("Doučovateľ", doucovatelStlpec.visibleProperty());
    	
    	//stlpec pre predmet
    	TableColumn<Doucovanie, DoucovanyPredmet> predmetStlpec = new TableColumn<>("Predmet");
    	predmetStlpec.setCellValueFactory(new PropertyValueFactory<>("predmet"));
    	lessonsTableView.getColumns().add(predmetStlpec);
    	columnsVisibility.put("Predmet", predmetStlpec.visibleProperty());
    	
    	
    	/*
    	//stlpec pre stupen studia trosku komplikovanejsie lebo Enum
    	TableColumn<Doucovanie, StupenStudia> stupenStudiaStlpec = new TableColumn<>("Stupeň Štúdia");
    	stupenStudiaStlpec.setCellValueFactory(new PropertyValueFactory<>("StupenStudia"));
    	lessonsTableView.getColumns().add(stupenStudiaStlpec);
    	columnsVisibility.put("Stupeň štúdia", stupenStudiaStlpec.visibleProperty());
    
    	*/
    	
    	//Tlacidlo na pridanie doucovania
    	addButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				LessonEditController editController = 
							new LessonEditController(new Doucovanie());            
					showModalWindow(editController, "LessonEdit.fxml");
					// tento kod sa spusti az po zatvoreni okna
					doucovanieModel.setAll(doucovanieDao.getAll());
			}
		});
    	
    	//Tlacidlo na editaciu existujuceho doucovania
    	editButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				LessonEditController editController = 
							new LessonEditController(selectedDoucovanie.get());            
					showModalWindow(editController, "LessonEdit.fxml");
					// tento kod sa spusti az po zatvoreni okna
					doucovanieModel.setAll(doucovanieDao.getAll());
			}
		});
    	
    	//Vyzmazat doucovanie zo zoznamu
    	deleteButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				doucovanieDao.delete(selectedDoucovanie.get().getId());
				doucovanieModel.setAll(doucovanieDao.getAll());
			}
		});
    	
    	lessonsTableView.getSelectionModel().
		selectedItemProperty().addListener(new ChangeListener<Doucovanie>() {
			@Override
			public void changed(ObservableValue<? extends Doucovanie> observable, 
					Doucovanie oldValue,
					Doucovanie newValue) {
				if (newValue == null) {
					editButton.setDisable(true);
				} else {
					editButton.setDisable(false);
				}
				selectedDoucovanie.set(newValue);
			}
		});
    }
    
    private void showModalWindow(Object controller, String fxml) {
		try {
			FXMLLoader fxmlLoader = new	FXMLLoader(getClass().getResource(fxml));
			fxmlLoader.setController(controller);
			Parent rootPane	= fxmlLoader.load();
			Scene scene	= new Scene(rootPane);
			
			Stage dialog = new Stage();
			dialog.setScene(scene);
			dialog.initModality(Modality.APPLICATION_MODAL);
			dialog.showAndWait();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

