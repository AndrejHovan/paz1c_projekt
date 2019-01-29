package sk.upjs.ed;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;

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
import sk.upjs.ed.persistent.DaoFactory;
import sk.upjs.ed.persistent.DoucovanieDao;
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

public class HistoryListController {

   
    
    private DoucovanieDao doucovanieDao = DaoFactory.INSTANCE.getDoucovanieDao();
	private ObservableList<Doucovanie> doucovanieModel;
	
	private Map<String, BooleanProperty> columnsVisibility = new LinkedHashMap<>();
	private ObjectProperty<Doucovanie> selectedDoucovanie = new SimpleObjectProperty<>();
	
    
	@FXML
    private TableView<Doucovanie> historyTableView;
	
    @FXML
    private Button editButton;

    @FXML
    private Button deleteButton;

   
    @FXML
    void initialize() {
    	//nacitaju sa iba doucovania, ktorych zaciatok je starsi ako momentalny datum, t.j. doucovania, ktore uz prebehli
    	doucovanieModel = FXCollections.observableArrayList(doucovanieDao.getMinule());
    	
    	//na zaciatku nie je nic selectnute tak nic needitujeme
    	editButton.setDisable(true);
		deleteButton.setDisable(true);

    	//stlpec pre id
    	TableColumn<Doucovanie, Long> idStlpec = new TableColumn<>("ID");
    	idStlpec.setCellValueFactory(new PropertyValueFactory<>("id"));
    	historyTableView.getColumns().add(idStlpec);
    	columnsVisibility.put("ID", idStlpec.visibleProperty());

    	//stlpec pre zaciatok
    	TableColumn<Doucovanie, LocalDateTime> zaciatokStlpec = new TableColumn<>("Zaciatok");
    	zaciatokStlpec.setCellValueFactory(new PropertyValueFactory<>("Zaciatok"));
    	historyTableView.getColumns().add(zaciatokStlpec);
    	columnsVisibility.put("Zaciatok", zaciatokStlpec.visibleProperty());
    	
    	//stlpec pre cas
    	TableColumn<Doucovanie, String> casStlpec = new TableColumn<>("Cas");
    	casStlpec.setCellValueFactory(new PropertyValueFactory<>("Cas"));
    	historyTableView.getColumns().add(casStlpec);
    	columnsVisibility.put("Cas", casStlpec.visibleProperty());
    	
    	//stlpec pre trvanie
    	TableColumn<Doucovanie, Long> trvanieStlpec = new TableColumn<>("Trvanie");
    	trvanieStlpec.setCellValueFactory(new PropertyValueFactory<>("Trvanie"));
    	historyTableView.getColumns().add(trvanieStlpec);
    	columnsVisibility.put("Trvanie", trvanieStlpec.visibleProperty());
    	
    	//stlpec pre cenu
    	TableColumn<Doucovanie, Double> cenaStlpec = new TableColumn<>("Cena");
    	cenaStlpec.setCellValueFactory(new PropertyValueFactory<>("Cena"));
    	historyTableView.getColumns().add(cenaStlpec);
    	columnsVisibility.put("Cena", cenaStlpec.visibleProperty());
    	
    	//stlpec pre okruh
    	TableColumn<Doucovanie, String> okruhStlpec = new TableColumn<>("Okruh");
    	okruhStlpec.setCellValueFactory(new PropertyValueFactory<>("Okruh"));
    	historyTableView.getColumns().add(okruhStlpec);
    	columnsVisibility.put("Okruh", okruhStlpec.visibleProperty());
    	
    	//stlpec pre lokalitu
    	TableColumn<Doucovanie, String> lokaciaStlpec = new TableColumn<>("Lokácia");
    	lokaciaStlpec.setCellValueFactory(new PropertyValueFactory<>("lokacia"));
    	historyTableView.getColumns().add(lokaciaStlpec);
    	columnsVisibility.put("Lokácia", lokaciaStlpec.visibleProperty());
    	    	
    	//stlpec pre studenta
    	TableColumn<Doucovanie, String> studentStlpec = new TableColumn<>("Študent");
    	//studentStlpec.setCellValueFactory(new PropertyValueFactory<>("student"));
    	studentStlpec.setCellValueFactory(new PropertyValueFactory<>("celeMenoStudenta"));
    	historyTableView.getColumns().add(studentStlpec);
    	columnsVisibility.put("Študent", studentStlpec.visibleProperty());
    	
    	//stlpec pre doucovatela
    	TableColumn<Doucovanie, String> doucovatelStlpec = new TableColumn<>("Doučovateľ");
    	doucovatelStlpec.setCellValueFactory(new PropertyValueFactory<>("celeMenoDoucovatela"));
    	historyTableView.getColumns().add(doucovatelStlpec);
    	columnsVisibility.put("Doučovateľ", doucovatelStlpec.visibleProperty());
    	
    	//stlpec pre predmet
    	TableColumn<Doucovanie, String> predmetStlpec = new TableColumn<>("Predmet");
    	predmetStlpec.setCellValueFactory(new PropertyValueFactory<>("nazovPredmetu"));
    	historyTableView.getColumns().add(predmetStlpec);
    	columnsVisibility.put("Predmet", predmetStlpec.visibleProperty());

    	historyTableView.setItems(doucovanieModel);

    	//Tlacidlo na editaciu existujuceho doucovania
    	editButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				LessonEditController editController = 
							new LessonEditController(selectedDoucovanie.get());            
					showModalWindow(editController, "LessonEdit.fxml");
					// tento kod sa spusti az po zatvoreni okna
					doucovanieModel.setAll(doucovanieDao.getMinule());
			}
		});
    	
    	//Vyzmazat doucovanie zo zoznamu
    	deleteButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				doucovanieDao.delete(selectedDoucovanie.get().getId());
				doucovanieModel.setAll(doucovanieDao.getMinule());
			}
		});
    	
    	//ak selectneme nieco tak vieme pouzit edit a delete
    	historyTableView.getSelectionModel().
		selectedItemProperty().addListener(new ChangeListener<Doucovanie>() {
			@Override
			public void changed(ObservableValue<? extends Doucovanie> observable, 
					Doucovanie oldValue,
					Doucovanie newValue) {
				if (newValue == null) {
					editButton.setDisable(true);
					deleteButton.setDisable(true);

				} else {
					editButton.setDisable(false);
					deleteButton.setDisable(false);
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
