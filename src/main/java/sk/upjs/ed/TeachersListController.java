package sk.upjs.ed;

import java.util.LinkedHashMap;
import java.util.Map;

import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sk.upjs.ed.entity.Doucovatel;
import sk.upjs.ed.persistent.DaoFactory;
import sk.upjs.ed.persistent.DoucovatelDao;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;


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
    	
    	teachersTableView.setItems(doucovateliaModel);


    }
}
