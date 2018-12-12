package sk.upjs.ed;
import java.time.LocalDateTime;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import sk.upjs.ed.entity.Doucovanie;
import sk.upjs.ed.entity.DoucovanyPredmet;
import sk.upjs.ed.entity.Doucovatel;
import sk.upjs.ed.entity.Student;

public class DoucovanieFxModel {

	private Long id;
	private Doucovanie doucovanie;
	

	private ObjectProperty<LocalDateTime> zaciatok = new SimpleObjectProperty<>();
	private IntegerProperty trvanie = new SimpleIntegerProperty(); // v minutach
	private DoubleProperty cena = new SimpleDoubleProperty();
	private StringProperty lokacia = new SimpleStringProperty();
	
	private ObjectProperty<Student> student = new SimpleObjectProperty<>(); //spravne?
	private ObjectProperty<Doucovatel> doucovatel = new SimpleObjectProperty<>(); //spravne?
	private ObjectProperty<DoucovanyPredmet> predmet = new SimpleObjectProperty<>(); //spravne?
	
	//este by mal byt predmet
	
	public DoucovanieFxModel(Doucovanie doucovanie) {
		this.doucovanie = doucovanie;
		
		setId(doucovanie.getId());
		setZaciatok(doucovanie.getZaciatok());
		setTrvanie(doucovanie.getTrvanie());
		setCena(doucovanie.getCena());
		setLokacia(doucovanie.getLokacia());
		setPredmet(doucovanie.getPredmet());  
		setStudent(doucovanie.getStudent());
		setDoucovatel(doucovanie.getDoucovatel());
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Doucovanie getDoucovanie() {
		return doucovanie;
	}

	public void setDoucovanie(Doucovanie doucovanie) {
		this.doucovanie = doucovanie;
	}
		
	
	public LocalDateTime getZaciatok() {
		return zaciatok.get();
	}
	
	public void setZaciatok(LocalDateTime zaciatok) {
		this.zaciatok.set(zaciatok);
	}
	
	public ObjectProperty<LocalDateTime> zaciatokProperty() {
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

