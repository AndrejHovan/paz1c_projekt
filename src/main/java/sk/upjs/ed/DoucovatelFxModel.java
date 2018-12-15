package sk.upjs.ed;

import java.util.List;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import sk.upjs.ed.entity.DoucovanyPredmet;
import sk.upjs.ed.entity.Doucovatel;

public class DoucovatelFxModel {

	private Long id;
	private Doucovatel doucovatel;
	private StringProperty meno = new SimpleStringProperty();
	private StringProperty priezvisko = new SimpleStringProperty();
	private BooleanProperty jeAktivny = new SimpleBooleanProperty();
	private ListProperty<DoucovanyPredmet> predmety = new SimpleListProperty<DoucovanyPredmet>(FXCollections.observableArrayList());

	public DoucovatelFxModel(Doucovatel doucovatel) {
		this.doucovatel = doucovatel;
		setId(doucovatel.getId());
		setMeno(doucovatel.getMeno());
		setPriezvisko(doucovatel.getPriezvisko());
		setAktivny(doucovatel.isAktivny());
		//if(doucovatel.getPredmety() != null) {
		setPredmety(doucovatel.getPredmety());
		//}
	}
	
	public Doucovatel getDoucovatel() {
		doucovatel.setMeno(getMeno());
		doucovatel.setPriezvisko(getPriezvisko());
		doucovatel.setAktivny(getAktivny());
		doucovatel.getId();
		doucovatel.getPredmety();
		return doucovatel;
	}	
	
	public void setDoucovatel(Doucovatel d) {
		doucovatel.setMeno(d.getMeno());
		doucovatel.setPriezvisko(d.getPriezvisko());
		doucovatel.setAktivny(d.isAktivny());
		doucovatel.setId(d.getId());
		doucovatel.setPredmety(d.getPredmety());
	}	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setAktivny(boolean aktivny) {
		this.jeAktivny.set(aktivny);
	}
	public Boolean getAktivny() {
		return jeAktivny.get();
	}
	public BooleanProperty aktivnyProperty() {
		return jeAktivny;
	}	
	public String getMeno() {
		return meno.get();
	}
	public void setMeno(String meno) {
		this.meno.set(meno);
	}
	public StringProperty menoProperty() {
		return meno;
	}
	public String getPriezvisko() {
		return priezvisko.get();
	}
	public void setPriezvisko(String priezvisko) {
		this.priezvisko.set(priezvisko);
	}
	public StringProperty priezviskoProperty() {
		return priezvisko;
	}	
	public List<DoucovanyPredmet> getPredmety() {
		return predmety.get();
	}
	public void setPredmety(List<DoucovanyPredmet> predmety) {
		this.predmety.setAll(predmety);
	}
	public ListProperty<DoucovanyPredmet> predmetyProperty() {
		return predmety;
	}	
}
