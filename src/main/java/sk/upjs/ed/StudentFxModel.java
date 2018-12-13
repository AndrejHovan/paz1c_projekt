package sk.upjs.ed;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import sk.upjs.ed.entity.Student;
import sk.upjs.ed.entity.StupenStudia;

public class StudentFxModel {
	private Long id;
	private Student student;
	private StringProperty meno = new SimpleStringProperty();
	private StringProperty priezvisko = new SimpleStringProperty();
	private BooleanProperty jeAktivny = new SimpleBooleanProperty();
	//private StupenStudia stupenStudia = new SimpleObjectProperty();
	private StringProperty telefon = new SimpleStringProperty();
	private StringProperty email = new SimpleStringProperty();
	//este predmety tu maju byt

	public StudentFxModel(Student student) {
		this.student = student;
		setId(student.getId());
		setMeno(student.getMeno());
		setPriezvisko(student.getPriezvisko());
		setEmail(student.getEmail());
		setTelefon(student.getTelefon());
		setAktivny(student.isAktivny());
	}
	
	public Student getStudent() {
		student.setMeno(getMeno());
		student.setPriezvisko(getPriezvisko());
		student.setEmail(getEmail());
		student.setTelefon(getTelefon());
		student.setAktivny(getAktivny());
		student.getId();
		return student;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
	
	public String getEmail() {
		return email.get();
	}
	public void setEmail(String email) {
		this.email.set(email);
	}
	public StringProperty emailProperty() {
		return email;
	}	
	
	public String getTelefon() {
		return telefon.get();
	}
	public void setTelefon(String telefon) {
		this.telefon.set(telefon);
	}
	public StringProperty telefonProperty() {
		return telefon;
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

	

	
}
