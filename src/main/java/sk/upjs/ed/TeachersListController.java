package sk.upjs.ed;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sk.upjs.ed.entity.Doucovatel;
import sk.upjs.ed.entity.Student;
import sk.upjs.ed.persistent.DaoFactory;
import sk.upjs.ed.persistent.DoucovatelDao;
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


public class TeachersListController {

    
	private DoucovatelDao doucovatelDao = DaoFactory.INSTANCE.getDoucovatelDao();
	private ObservableList<Doucovatel> doucovateliaModel;
	private Map<String, BooleanProperty> columnsVisibility = new LinkedHashMap<>();
	private ObjectProperty<Doucovatel> selectedDoucovatel = new SimpleObjectProperty<>();
	
	
    @FXML
    private TableView<Doucovatel> teachersTableView;
    
    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button editButton;
    

    @FXML
    void initialize() {
    	
    	doucovateliaModel = FXCollections.observableArrayList(doucovatelDao.getAll());
    	
    	editButton.setDisable(true);

    	//stlpec pre id
    	TableColumn<Doucovatel, Long> idStlpec = new TableColumn<>("ID");
    	idStlpec.setCellValueFactory(new PropertyValueFactory<>("id"));
    	teachersTableView.getColumns().add(idStlpec);
    	columnsVisibility.put("ID", idStlpec.visibleProperty());

    	//stlpec pre meno
    	TableColumn<Doucovatel, String> menoStlpec = new TableColumn<>("Meno");
    	menoStlpec.setCellValueFactory(new PropertyValueFactory<>("meno"));
    	teachersTableView.getColumns().add(menoStlpec);
    	columnsVisibility.put("Meno", menoStlpec.visibleProperty());
    	
    	//stlpec pre priezvisko
    	TableColumn<Doucovatel, String> priezviskoStlpec = new TableColumn<>("Priezvisko");
    	priezviskoStlpec.setCellValueFactory(new PropertyValueFactory<>("priezvisko"));
    	teachersTableView.getColumns().add(priezviskoStlpec);
    	columnsVisibility.put("Priezvisko", priezviskoStlpec.visibleProperty());
    	
    	//stlpec pre aktivitu
    	TableColumn<Doucovatel, Boolean> aktivnyStlpec = new TableColumn<>("Aktívny");
    	aktivnyStlpec.setCellValueFactory(new PropertyValueFactory<>("aktivny"));
    	teachersTableView.getColumns().add(aktivnyStlpec);
    	columnsVisibility.put("Aktívny", aktivnyStlpec.visibleProperty());
    	
    	teachersTableView.setItems(doucovateliaModel);
    	
    	//upravit doucovatela otvori modalne okno s udajmi doucovatela
    	editButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				TeacherEditController editController = 
							new TeacherEditController(selectedDoucovatel.get());            
					showModalWindow(editController, "TeacherEdit.fxml");
					// tento kod sa spusti az po zatvoreni okna
					doucovateliaModel.setAll(doucovatelDao.getAll());
			}
		});
    	
    	//pridat otvori to iste modalne okno len prazdne
    	addButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				TeacherEditController editController = 
							new TeacherEditController(new Doucovatel());            
					showModalWindow(editController, "TeacherEdit.fxml");
					// tento kod sa spusti az po zatvoreni okna
					doucovateliaModel.setAll(doucovatelDao.getAll());
			}
		});
    	
    	//vymazat vymaze doucovatela zo zoznamu
    	deleteButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				doucovatelDao.delete(selectedDoucovatel.get().getId());
	        	//List<Doucovatel> doucovatelia = doucovatelDao.getAll();
				doucovateliaModel.setAll(doucovatelDao.getAll());
			}
		});
    	
    	teachersTableView.getSelectionModel().
		selectedItemProperty().addListener(new ChangeListener<Doucovatel>() {
			@Override
			public void changed(ObservableValue<? extends Doucovatel> observable, 
					Doucovatel oldValue,
					Doucovatel newValue) {
				if (newValue == null) {
					editButton.setDisable(true);
				} else {
					editButton.setDisable(false);
				}
				selectedDoucovatel.set(newValue);
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
