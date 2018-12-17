package sk.upjs.ed;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
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
	//list doucovani
	private ObservableList<Doucovanie> doucovanieModel;
	
	//Mapa ktora urcuje ktore stlpce lessonsTableView su viditelne
	private Map<String, BooleanProperty> columnsVisibility = new LinkedHashMap<>();
	//zvolene doucovanie
	private ObjectProperty<Doucovanie> selectedDoucovanie = new SimpleObjectProperty<>();
	
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
    	//natiahnu sa z databazi vsetky doucovania novsie ako dnesny datum
    	doucovanieModel = FXCollections.observableArrayList(doucovanieDao.getBuduce());
		
    	//tlacidlo editacie je disabled kym sa neoznaci doucovanie
    	editButton.setDisable(true);
    	deleteButton.setDisable(true);
    	
    	//stlpec pre id
    	TableColumn<Doucovanie, Long> idStlpec = new TableColumn<>("ID");
    	idStlpec.setCellValueFactory(new PropertyValueFactory<>("id"));
    	//tu sa prida stlpec na id
    	lessonsTableView.getColumns().add(idStlpec);
    	//tu sa nastavi, ze je to viditelne predpokladam
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
    	
    	//nastavy hodnoty v tabulke, ktore sme nacitali z databazy
    	lessonsTableView.setItems(doucovanieModel);
    	    	    	    	
    	//Tlacidlo na pridanie doucovania
    	addButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				//po kliknuti sa otvori modalne okno na pridavanie / editovanie doucovania
				LessonEditController editController = 
							new LessonEditController(new Doucovanie());            
					showModalWindow(editController, "LessonEdit.fxml");
					// tento kod sa spusti az po zatvoreni okna
					//nacitaju sa opat vsetky doucovania
					doucovanieModel.setAll(doucovanieDao.getBuduce());
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
					doucovanieModel.setAll(doucovanieDao.getBuduce());
			}
		});
    	
    	//Vymazat doucovanie zo zoznamu
    	deleteButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				//z databazy sa vymaze doucovanie s danym ID
				doucovanieDao.delete(selectedDoucovanie.get().getId());
				//opat sa nacitaju doucovania, uz bez vymazanej polozky
				doucovanieModel.setAll(doucovanieDao.getBuduce());
			}
		});
    	
    	//selectovanie doucovani
    	lessonsTableView.getSelectionModel().
		selectedItemProperty().addListener(new ChangeListener<Doucovanie>() {
			@Override
			public void changed(ObservableValue<? extends Doucovanie> observable, 
					Doucovanie oldValue,
					Doucovanie newValue) {
				if (newValue == null) {
					//ak nie je nic selectnute, tlacidlo editacie je disabled
					editButton.setDisable(true);
					deleteButton.setDisable(true);
				} else {
					//ak mame vybrate doucovanie, je mozne kliknut na tlacidlo editacie
					editButton.setDisable(false);
					deleteButton.setDisable(false);
				}
				selectedDoucovanie.set(newValue);
			}
		});
    }
    
    //metoda na otvorenie modalneho okna zvolenej sceny
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

