package sk.upjs.ed;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import sk.upjs.ed.entity.Doucovatel;

public class DoucovatelFxModel {
	private Doucovatel doucovatel;
	private StringProperty meno = new SimpleStringProperty();
	private StringProperty priezvisko = new SimpleStringProperty();
	private BooleanProperty jeAktivny = new SimpleBooleanProperty();
	//este predmety tu maju byt

	public DoucovatelFxModel(Doucovatel doucovatel) {
		this.doucovatel = doucovatel;
		setMeno(doucovatel.getMeno());
		setPriezvisko(doucovatel.getPriezvisko());
		setAktivny(doucovatel.isAktivny());
	}
	
	public Doucovatel getDoucovatel() {
		doucovatel.setMeno(getMeno());
		doucovatel.setPriezvisko(getPriezvisko());
		doucovatel.setAktivny(getAktivny());
		return doucovatel;
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
}
