package sk.upjs.ed;


import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import sk.upjs.ed.entity.DoucovanyPredmet;
import sk.upjs.ed.entity.Doucovatel;
import sk.upjs.ed.entity.StupenStudia;

public class PredmetFxModel {
	
	private Long id;
	private DoucovanyPredmet predmet;
	private StringProperty nazov = new SimpleStringProperty();
	private ObjectProperty<StupenStudia> stupenStudia = new SimpleObjectProperty<StupenStudia>();
	
	public PredmetFxModel(DoucovanyPredmet predmet) {
		this.predmet = predmet;
		setId(predmet.getId());
		setNazov(predmet.getNazov());
		setStupenStudia(predmet.getStupenStudia());
		
	}
	
	public DoucovanyPredmet getPredmet() {
		predmet.setNazov(getNazov());
		predmet.setStupenStudia(getStupenStudia());
		predmet.setId(getId());
		return predmet;
	}
	
	public void setPredmet(DoucovanyPredmet dp) {
		setNazov(dp.getNazov());
		setStupenStudia(dp.getStupenStudia());
		setId(dp.getId());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNazov() {
		return nazov.get();
	}
	public void setNazov(String meno) {
		this.nazov.set(meno);
	}
	public StringProperty nazovProperty() {
		return nazov;
	}
	
	public void setStupenStudia(StupenStudia ss) {
		this.stupenStudia.set(ss);
	}
	public StupenStudia getStupenStudia() {
		return stupenStudia.get();
	}
	public ObjectProperty<StupenStudia> stupenStudiaProperty() {
		return stupenStudia;
	}	


}
