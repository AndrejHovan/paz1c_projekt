package sk.upjs.ed;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import sk.upjs.ed.entity.Doucovanie;
import sk.upjs.ed.entity.Doucovatel;
import sk.upjs.ed.entity.Student;

public class HistoriaDoucovaniFxModel {

	private Long id;
	private Doucovanie doucovanie;
	
	private ObjectProperty<LocalDate> zaciatok = new SimpleObjectProperty<>();
	private IntegerProperty trvanie = new SimpleIntegerProperty(); // v minutach
	private DoubleProperty cena = new SimpleDoubleProperty();
	private StringProperty okruh = new SimpleStringProperty();  //neskor nahradi predmet
	private StringProperty lokacia = new SimpleStringProperty();
	
	private ObjectProperty<Student> student = new SimpleObjectProperty<>(); //spravne?
	private ObjectProperty<Doucovatel> doucovatel = new SimpleObjectProperty<>(); //spravne?
	//este by mal byt predmet
	
	public HistoriaDoucovaniFxModel(Doucovanie doucovanie) {
		this.doucovanie = doucovanie;
		
		setId(doucovanie.getId());
		setZaciatok(doucovanie.getZaciatok());
		setTrvanie(doucovanie.getTrvanie());
		setCena(doucovanie.getCena());
		setOkruh(doucovanie.getOkruh());  //neskor nahradi predmet
		setLokacia(doucovanie.getLokacia());
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
		
	
	public LocalDate getZaciatok() {
		return zaciatok.get();
	}
	
	public void setZaciatok(LocalDate zaciatok) {
		this.zaciatok.set(zaciatok);
	}
	
	public ObjectProperty<LocalDate> zaciatokProperty() {
		return zaciatok;
	}

	public void setZaciatok(ObjectProperty<LocalDate> zaciatok) {
		this.zaciatok = zaciatok;
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

	public String getOkruh() {
		return okruh.get();
	}

	public void setOkruh(String okruh) {
		this.okruh.set(okruh);
	}

	public StringProperty okruhProperty() {
		return okruh;
	}
	
	public ObjectProperty<Doucovatel> getDoucovatel() {
		return doucovatel;
	}

	public void setDoucovatel(ObjectProperty<Doucovatel> doucovatel) {
		this.doucovatel = doucovatel;
	}

	public StringProperty getLokacia() {
		return lokacia;
	}

	public void setLokacia(StringProperty lokacia) {
		this.lokacia = lokacia;
	}

	public ObjectProperty<Student> getStudent() {
		return student;
	}

	public void setStudent(ObjectProperty<Student> student) {
		this.student = student;
	}
	
}


