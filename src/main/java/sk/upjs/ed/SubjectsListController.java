package sk.upjs.ed;

import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.beans.property.BooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sk.upjs.ed.entity.DoucovanyPredmet;
import sk.upjs.ed.entity.StupenStudia;
import sk.upjs.ed.persistent.DaoFactory;
import sk.upjs.ed.persistent.DoucovanyPredmetDao;

public class SubjectsListController {

	private DoucovanyPredmetDao predmetDao = DaoFactory.INSTANCE.getPredmetDao();
	private ObservableList<DoucovanyPredmet> predmetModel;
	private Map<String, BooleanProperty> columnsVisibility = new LinkedHashMap<>();
	
	
    @FXML
    private TableView<DoucovanyPredmet> subjectsTableView;
    
    @FXML
    void initialize() {
    	
    	predmetModel = FXCollections.observableArrayList(predmetDao.getAllForList());
    	
    	//stlpec pre id
    	TableColumn<DoucovanyPredmet, Long> idStlpec = new TableColumn<>("ID");
    	idStlpec.setCellValueFactory(new PropertyValueFactory<>("id"));
    	subjectsTableView.getColumns().add(idStlpec);
    	columnsVisibility.put("ID", idStlpec.visibleProperty());
    	
    	//stlpec pre meno
    	TableColumn<DoucovanyPredmet, String> nazovStlpec = new TableColumn<>("Nazov");
    	nazovStlpec.setCellValueFactory(new PropertyValueFactory<>("nazov"));
    	subjectsTableView.getColumns().add(nazovStlpec);
    	columnsVisibility.put("Nazov", nazovStlpec.visibleProperty());
    	
    	//stlpec pre stupen studia trosku komplikovanejsie lebo Enum
    	TableColumn<DoucovanyPredmet, StupenStudia> stupenStudiaStlpec = new TableColumn<>("Stupeň Štúdia");
    	stupenStudiaStlpec.setCellValueFactory(new PropertyValueFactory<>("StupenStudia"));
    	subjectsTableView.getColumns().add(stupenStudiaStlpec);
    	columnsVisibility.put("Stupeň štúdia", stupenStudiaStlpec.visibleProperty());
    	
    	subjectsTableView.setItems(predmetModel);
    	
    }
}
