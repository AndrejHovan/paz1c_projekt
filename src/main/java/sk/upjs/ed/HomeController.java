package sk.upjs.ed;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
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
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import javafx.util.StringConverter;


public class HomeController {

	//private boolean historySceneOpened = false;
	
	
	
	
    @FXML
    private Button historyButton;

    @FXML
    private Button studentsButton;

    @FXML
    private Button teachersButton;

    @FXML
    private Button subjectsButton;

    @FXML
    private Button futureButton;

    @FXML
    void initialize() {
    	
    	historyButton.setOnAction(new EventHandler<ActionEvent>() {

			//@Override WHAAAT?
    		//na cviceniach je override, tu ked ho dame hadze error
			public void handle(ActionEvent event) {
				//if(!historySceneOpened) {
					HistoryListController historyController = new HistoryListController();            
					showWindow(historyController, "HistoryList.fxml");
					//historySceneOpened = true;
				//}

			}
			
		});
    	
    	studentsButton.setOnAction(new EventHandler<ActionEvent>() {

			//@Override //WHAAAT?
    		//na cviceniach je override, tu ked ho dame hadze error
			public void handle(ActionEvent event) {
				StudentsListController studentsController = new StudentsListController();            
				showWindow(studentsController, "StudentsList.fxml");
			}
			
		});
    	teachersButton.setOnAction(new EventHandler<ActionEvent>() {

			//@Override WHAAAT?
    		//na cviceniach je override, tu ked ho dame hadze error
			public void handle(ActionEvent event) {
				TeachersListController teachersController = new TeachersListController();            
				showWindow(teachersController, "TeachersList.fxml");
			}
			
		});
    	subjectsButton.setOnAction(new EventHandler<ActionEvent>() {

			//@Override WHAAAT?
    		//na cviceniach je override, tu ked ho dame hadze error
			public void handle(ActionEvent event) {
				SubjectsListController subjectsController = new SubjectsListController();            
				showWindow(subjectsController, "SubjectsList.fxml");
			}
			
		});
    	futureButton.setOnAction(new EventHandler<ActionEvent>() {

			//@Override WHAAAT?
    		//na cviceniach je override, tu ked ho dame hadze error
			public void handle(ActionEvent event) {
				FutureLessonsListController futureController = new FutureLessonsListController();            
				showWindow(futureController, "FutureLessonsList.fxml");
			}
			
		});
    }
    
    private void showWindow(Object controller, String fxml) {
		try {
			FXMLLoader fxmlLoader = new	FXMLLoader(getClass().getResource(fxml));
			fxmlLoader.setController(controller);
			Parent rootPane	= fxmlLoader.load();
			Scene scene	= new Scene(rootPane);
			
			Stage dialog = new Stage();
			dialog.setScene(scene);
			dialog.show();
			//dialog.initModality(Modality.APPLICATION_MODAL);
			//dialog.showAndWait();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
