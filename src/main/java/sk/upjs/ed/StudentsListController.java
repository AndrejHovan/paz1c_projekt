package sk.upjs.ed;

import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import javafx.util.StringConverter;
import sk.upjs.ed.entity.Student;
import sk.upjs.ed.entity.StupenStudia;
import sk.upjs.ed.persistent.DaoFactory;
import sk.upjs.ed.persistent.StudentDao;


public class StudentsListController {

	private StudentDao studentDao = DaoFactory.INSTANCE.getStudentDao();
	private ObservableList<Student> studentsModel;
	private Map<String, BooleanProperty> columnsVisibility = new LinkedHashMap<>();
	private ObjectProperty<Student> selectedParticipant = new SimpleObjectProperty<>();
	@FXML
	private TableView<Student> studentsTableView;

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
    	stupenStudiaStlpec.setCellValueFactory(new PropertyValueFactory<>("stupenStudia"));
    	stupenStudiaStlpec.setCellFactory(new Callback<TableColumn<Student,StupenStudia>, TableCell<Student,StupenStudia>>() {
			
			@Override
			public TableCell<Student,StupenStudia> call(TableColumn<Student,StupenStudia> param) {
				return new ComboBoxTableCell<>(new StringConverter<StupenStudia>() {

					@Override
					public String toString(StupenStudia object) {
						return object==null ? "" : object.name();
					}

					@Override
					public StupenStudia fromString(String string) {
						return string==null ? null : StupenStudia.valueOf(string);
					}
				});
			}
		});
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
    	//emailCol.setCellFactory(TextFieldTableCell.forTableColumn());
    	studentsTableView.getColumns().add(emailCol);
    	columnsVisibility.put("E-mail", emailCol.visibleProperty());
    	
    	studentsTableView.setItems(studentsModel);
    	studentsTableView.setEditable(true);
    }
}
