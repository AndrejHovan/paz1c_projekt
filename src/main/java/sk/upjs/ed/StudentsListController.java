package sk.upjs.ed;

import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import sk.upjs.ed.entity.Doucovatel;
import sk.upjs.ed.entity.Student;
import sk.upjs.ed.entity.StupenStudia;
import sk.upjs.ed.persistent.DaoFactory;
import sk.upjs.ed.persistent.StudentDao;


public class StudentsListController {

	private StudentDao studentDao = DaoFactory.INSTANCE.getStudentDao();
	private ObservableList<Student> studentsModel;
	private Map<String, BooleanProperty> columnsVisibility = new LinkedHashMap<>();
	private ObjectProperty<Student> selectedStudent = new SimpleObjectProperty<>();
	@FXML
	private TableView<Student> studentsTableView;

	@FXML
    private Button addStudentButton;

    @FXML
    private Button removeStudentButton;
    
    @FXML
    void initialize() {
    	studentsModel = FXCollections.observableArrayList(studentDao.getAll());

    	//stlpec pre id
    	TableColumn<Student, Long> idStlpec = new TableColumn<>("ID");
    	idStlpec.setCellValueFactory(new PropertyValueFactory<>("id"));
    	studentsTableView.getColumns().add(idStlpec);
    	columnsVisibility.put("ID", idStlpec.visibleProperty());

    	//stlpec pre meno
    	TableColumn<Student, String> menoStlpec = new TableColumn<>("Meno");
    	menoStlpec.setCellValueFactory(new PropertyValueFactory<>("meno"));
    	studentsTableView.getColumns().add(menoStlpec);
    	columnsVisibility.put("Meno", menoStlpec.visibleProperty());
    	
    	//stlpec pre priezvisko
    	TableColumn<Student, String> priezviskoStlpec = new TableColumn<>("Priezvisko");
    	priezviskoStlpec.setCellValueFactory(new PropertyValueFactory<>("priezvisko"));
    	studentsTableView.getColumns().add(priezviskoStlpec);
    	columnsVisibility.put("Priezvisko", priezviskoStlpec.visibleProperty());
    	
    	//stlpec pre stupen studia trosku komplikovanejsie lebo Enum
    	TableColumn<Student, StupenStudia> stupenStudiaStlpec = new TableColumn<>("Stupeň Štúdia");
    	stupenStudiaStlpec.setCellValueFactory(new PropertyValueFactory<>("Stupen"));
    	studentsTableView.getColumns().add(stupenStudiaStlpec);
    	columnsVisibility.put("Stupeň štúdia", stupenStudiaStlpec.visibleProperty());
    
    	//stlpec pre telefonne cislo
    	TableColumn<Student, String> telefonStlpec = new TableColumn<>("Tel. kontakt");
    	telefonStlpec.setCellValueFactory(new PropertyValueFactory<>("telefon"));
    	studentsTableView.getColumns().add(telefonStlpec);
    	columnsVisibility.put("Tel. kontakt", telefonStlpec.visibleProperty());
    	
    	//stlpec pre email
    	TableColumn<Student, String> emailCol = new TableColumn<>("E-mail");
    	emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
    	studentsTableView.getColumns().add(emailCol);
    	columnsVisibility.put("E-mail", emailCol.visibleProperty());
    	
    	TableColumn<Student, Boolean> aktivnyCol = new TableColumn<>("Aktívny");
    	aktivnyCol.setCellValueFactory(new PropertyValueFactory<>("aktivny"));
    	studentsTableView.getColumns().add(aktivnyCol);
    	columnsVisibility.put("Aktívny", aktivnyCol.visibleProperty());

    	
    	studentsTableView.setItems(studentsModel);
    	studentsTableView.setEditable(true);
    	
    	//Pridaval som funkcionalitu na button, snazil som sa presne tak, ako pri doucovatelovi
    	addStudentButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				StudentEditController editController = 
							new StudentEditController(new Student());            
					showModalWindow(editController, "StudentsEdit.fxml");
					// tento kod sa spusti az po zatvoreni okna
					studentsModel.setAll(studentDao.getAll());
			}
		});
    	
    	//Rovnako s mazanim studentov
    	removeStudentButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				studentDao.delete(selectedStudent.get().getId());
	        	//List<Student> students = studentDao.getAll();
				studentsModel.setAll(studentDao.getAll());
			}
		});
    	
    	studentsTableView.getSelectionModel().
		selectedItemProperty().addListener(new ChangeListener<Student>() {
			@Override
			public void changed(ObservableValue<? extends Student> observable, 
					Student oldValue,
					Student newValue) {
				/*if (newValue == null) {
					editButton.setDisable(true);
				} else {
					editButton.setDisable(false);
				}*/
				selectedStudent.set(newValue);
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
