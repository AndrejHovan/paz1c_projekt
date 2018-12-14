package sk.upjs.ed;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import sk.upjs.ed.entity.DoucovanyPredmet;
import sk.upjs.ed.entity.Doucovatel;
import sk.upjs.ed.entity.StupenStudia;
import sk.upjs.ed.persistent.DaoFactory;
import sk.upjs.ed.persistent.DoucovanyPredmetDao;

public class AddSubjectController {
	
	private DoucovanyPredmetDao predmetDao = DaoFactory.INSTANCE.getPredmetDao();
	private DoucovanyPredmet predmet;
	private PredmetFxModel predmetModel;
	//private Doucovatel doucovatel;
	private DoucovatelFxModel doucovatelModel;
	
    @FXML
    private Button addButton;

    @FXML
    private TextField predmetTextField;

    @FXML
    private ComboBox<StupenStudia> stupenComboBox;
    
    public AddSubjectController(DoucovatelFxModel doucovatelModel, DoucovanyPredmet predmet) {
    	this.predmet = predmet;
    	this.doucovatelModel = doucovatelModel;
    	this.predmetModel = new PredmetFxModel(predmet);
    }

    @FXML
    void initialize() {
    	//nastavujeme hodnoty pre combobox (mozne vybratelne)
    			stupenComboBox.getItems().setAll(StupenStudia.values());
    			//defaultne nastavime strednu skolu
    			stupenComboBox.getSelectionModel().select(StupenStudia.values()[1]);
    			//aby to aj FXmodel videl tak mu to pripomenieme..
    			predmetModel.setStupenStudia(stupenComboBox.getSelectionModel().getSelectedItem());
    			//ak zmenime vyber tak sa zmeni aj v FXmodeli
    			stupenComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<StupenStudia>(){

    				@Override
    				public void changed(ObservableValue<? extends StupenStudia> observable, StupenStudia oldValue,
    						StupenStudia newValue) {
    					if(newValue != null) {
    						predmetModel.setStupenStudia(newValue);
    					}
    					
    				}
    				
    			});
    			predmetTextField.textProperty().bindBidirectional(predmetModel.nazovProperty());
    	    	
    	    	addButton.setOnAction(new EventHandler<ActionEvent>() {
    				@Override
    				public void handle(ActionEvent event) {
    					List<DoucovanyPredmet> vsetkyPredmety = predmetDao.getAll();
    					boolean nasloSa = false;
    					for (DoucovanyPredmet dp : vsetkyPredmety) {
							if (dp.getNazov().equals(predmetModel.getPredmet().getNazov()) && 
									dp.getStupenStudia() == predmetModel.getPredmet().getStupenStudia()) {
									doucovatelModel.addPredmet(dp);
									nasloSa = true;
									break;
							}
						}
    					if(!nasloSa) {
							predmetDao.add(predmetModel.getPredmet());
							doucovatelModel.addPredmet(predmetModel.getPredmet());
    					}
    					
    					addButton.getScene().getWindow().hide();
    				}
    			});
    	    	
    }
}
