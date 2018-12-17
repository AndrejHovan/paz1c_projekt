package sk.upjs.ed;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.List;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sk.upjs.ed.entity.DoucovanyPredmet;
import sk.upjs.ed.entity.Doucovatel;
import sk.upjs.ed.entity.Student;
import sk.upjs.ed.entity.StupenStudia;
import sk.upjs.ed.persistent.DaoFactory;
import sk.upjs.ed.persistent.DoucovanyPredmetDao;
import sk.upjs.ed.persistent.DoucovatelDao;

public class TeacherEditController {

	private DoucovanyPredmetDao predmetDao = DaoFactory.INSTANCE.getPredmetDao();
	private DoucovatelDao doucovatelDao = DaoFactory.INSTANCE.getDoucovatelDao();
	private Doucovatel doucovatel;
	private DoucovatelFxModel doucovatelModel;
	private ObservableList<DoucovanyPredmet> predmetyModel;
	private ObjectProperty<DoucovanyPredmet> selectedPredmet = new SimpleObjectProperty<>();
	private Map<String, BooleanProperty> columnsVisibility = new LinkedHashMap<>();


    @FXML
    private TextField nameTextField;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private CheckBox isActiveCheckBox;

    @FXML
    private Button saveButton;

    @FXML
    private TableView<DoucovanyPredmet> teacherEditTableView;

    @FXML
    private Button addSubjectButton;
    
    @FXML
    private Button deleteButton;

    public TeacherEditController(Doucovatel doucovatel) {
    	this.doucovatel = doucovatel;
    	this.doucovatelModel = new DoucovatelFxModel(doucovatel);
    	
    }

	@FXML
    void initialize() {

		predmetyModel =  (ObservableList<DoucovanyPredmet>)doucovatelModel.getPredmety();//
		deleteButton.setDisable(true);
		
    	//stlpec pre id
    	TableColumn<DoucovanyPredmet, Long> idStlpec = new TableColumn<>("ID");
    	idStlpec.setCellValueFactory(new PropertyValueFactory<>("id"));
    	teacherEditTableView.getColumns().add(idStlpec);
    	columnsVisibility.put("ID", idStlpec.visibleProperty());

    	//stlpec pre nazov predmetu
    	TableColumn<DoucovanyPredmet, String> menoStlpec = new TableColumn<>("Názov");
    	menoStlpec.setCellValueFactory(new PropertyValueFactory<>("nazov"));
    	teacherEditTableView.getColumns().add(menoStlpec);
    	columnsVisibility.put("Názov", menoStlpec.visibleProperty());
    	
    	
    	//stlpec pre stupen studia trosku komplikovanejsie lebo Enum
    	TableColumn<DoucovanyPredmet, StupenStudia> stupenStudiaStlpec = new TableColumn<>("Stupeň Štúdia");
    	stupenStudiaStlpec.setCellValueFactory(new PropertyValueFactory<>("StupenStudia"));
    	teacherEditTableView.getColumns().add(stupenStudiaStlpec);
    	columnsVisibility.put("Stupeň štúdia", stupenStudiaStlpec.visibleProperty());
    	
    	teacherEditTableView.setItems(predmetyModel);

		
    	nameTextField.textProperty().bindBidirectional(doucovatelModel.menoProperty());
    	lastNameTextField.textProperty().bindBidirectional(doucovatelModel.priezviskoProperty());
    	isActiveCheckBox.selectedProperty().bindBidirectional(doucovatelModel.aktivnyProperty());
    	//doucovatelModel.setPredmety(teacherEditTableView.getItems());

    	addSubjectButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				AddSubjectController addSubjectController = 
							new AddSubjectController(new DoucovanyPredmet(), doucovatelModel);            
					showModalWindow(addSubjectController, "AddSubject.fxml");
			}
		});
    	
    	deleteButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				List<Doucovatel> vsetciDoucovatelia = FXCollections.observableArrayList(doucovatelDao.getAll());
				int pocetDoucovatelovSPredmetom = 0;
				for (Doucovatel d : vsetciDoucovatelia) {
					for (DoucovanyPredmet dp : d.getPredmety()) {
						if(dp.getId() == selectedPredmet.get().getId()) {
							pocetDoucovatelovSPredmetom++;
						}
							
					}
					if (pocetDoucovatelovSPredmetom > 1)
						break;
				}
				if(pocetDoucovatelovSPredmetom == 1)
					predmetDao.delete(selectedPredmet.get().getId());
				doucovatelModel.getPredmety().remove(selectedPredmet.get());
			}
		});
    	
    	saveButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				doucovatelDao.save(doucovatelModel.getDoucovatel());
				saveButton.getScene().getWindow().hide();
			}
		});
    	
    	teacherEditTableView.getSelectionModel().
		selectedItemProperty().addListener(new ChangeListener<DoucovanyPredmet>() {
			@Override
			public void changed(ObservableValue<? extends DoucovanyPredmet> observable, 
					DoucovanyPredmet oldValue,
					DoucovanyPredmet newValue) {
				if (newValue == null) {
					deleteButton.setDisable(true);
				} else {
					deleteButton.setDisable(false);
				}
				selectedPredmet.set(newValue);
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
