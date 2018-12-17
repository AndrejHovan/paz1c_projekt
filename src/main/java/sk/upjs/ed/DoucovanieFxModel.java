package sk.upjs.ed;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javafx.beans.Observable;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import sk.upjs.ed.entity.Doucovanie;
import sk.upjs.ed.entity.DoucovanyPredmet;
import sk.upjs.ed.entity.Doucovatel;
import sk.upjs.ed.entity.Student;

public class DoucovanieFxModel {

	private Long id;
	private Doucovanie doucovanie;
	

	private ObjectProperty<LocalDate> zaciatok = new SimpleObjectProperty<>();
	private IntegerProperty trvanie = new SimpleIntegerProperty(); // v minutach
	private DoubleProperty cena = new SimpleDoubleProperty();
	private StringProperty lokacia = new SimpleStringProperty();
	private StringProperty okruh = new SimpleStringProperty();
	private StringProperty cas = new SimpleStringProperty();
	private ObjectProperty<Student> student = new SimpleObjectProperty<Student>(); 
	private ObjectProperty<Doucovatel> doucovatel = new SimpleObjectProperty<Doucovatel>(); 
	private ObjectProperty<DoucovanyPredmet> predmet = new SimpleObjectProperty<DoucovanyPredmet>(); 
	
	
	
	public DoucovanieFxModel(Doucovanie doucovanie) {
		this.doucovanie = doucovanie;
		
		setId(doucovanie.getId());
		setZaciatok(doucovanie.getZaciatok());
		setCas(doucovanie.getCas());
		setTrvanie(doucovanie.getTrvanie());
		setCena(doucovanie.getCena());
		setLokacia(doucovanie.getLokacia());
		setOkruh(doucovanie.getOkruh());
		setPredmet(doucovanie.getPredmet());  
		setStudent(doucovanie.getStudent());
		setDoucovatel(doucovanie.getDoucovatel());
		
	}
	
	public Doucovanie getDoucovanie() {
		doucovanie.setZaciatok(getZaciatok());
		doucovanie.setCas(getCas());
		doucovanie.setTrvanie(getTrvanie());
		doucovanie.setCena(getCena());
		doucovanie.setLokacia(getLokacia());
		doucovanie.setOkruh(getOkruh());
		doucovanie.setPredmet(getPredmet());
		doucovanie.setStudent(getStudent());
		doucovanie.setDoucovatel(getDoucovatel());		
		doucovanie.setId(getId());
		return doucovanie;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	public void setDoucovanie(Doucovanie doucovanie) {
		this.doucovanie = doucovanie;
	}
		
	
	public LocalDate getZaciatok() {
		return zaciatok.get();
	}
	
	public void setZaciatok(LocalDate zaciatok) {
		this.zaciatok.set(zaciatok);
	}
	
	public Property<LocalDate> zaciatokProperty() {
		return zaciatok;
	}

	public int getTrvanie() {
		return trvanie.get();
	}

	public void setTrvanie(int trvanie) {
		this.trvanie.set(trvanie);
	}
	
	public IntegerProperty trvanieProperty() {
		return trvanie;
	}

	public double getCena() {
		return cena.get();
	}
	
	public void setCena(double cena) {
		this.cena.set(cena);
	}
	
	public DoubleProperty cenaProperty() {
		return cena;
	}
	
	public String getLokacia() {
		return lokacia.get();
	}

	public void setLokacia(String lokacia) {
		this.lokacia.set(lokacia);
	}
	
	public StringProperty lokaciaProperty() {
		return lokacia;
	}
	
	public String getCas() {
		return cas.get();
	}

	public void setCas(String cas) {
		this.cas.set(cas);
	}
	
	public StringProperty casProperty() {
		return cas;
	}
	public String getOkruh() {
		return okruh.get();
	}

	public void setOkruh(String okruh) {
		this.okruh.set(okruh);
	}
	
	public StringProperty okruhProperty() {
		return okruh;
	}
	
	public Doucovatel getDoucovatel() {
		return doucovatel.get();
	}

	public void setDoucovatel(Doucovatel doucovatel) {
		this.doucovatel.set(doucovatel);
	}

	public ObjectProperty<Doucovatel> doucovatelProperty() {
		return doucovatel;
	}
	
	public Student getStudent() {
		return student.get();
	}

	public void setStudent(Student student) {
		this.student.set(student);
	}
	
	public ObjectProperty<Student> studentProperty() {
		return student;
	}
	
	public DoucovanyPredmet getPredmet() {
		return predmet.get();
	}

	public void setPredmet(DoucovanyPredmet predmet) {
		this.predmet.set(predmet);
	}
	
	public ObjectProperty<DoucovanyPredmet> predmetProperty() {
		return predmet;
	}
	
}

