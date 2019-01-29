	package sk.upjs.ed.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.print.attribute.standard.DateTimeAtCompleted;

public class Doucovanie {
	
	private Long id;
	private LocalDate zaciatok;
	private String cas;
	private int trvanie; // v minutach
	private double cena;
	private String okruh;
	private DoucovanyPredmet predmet;
	private Doucovatel doucovatel;
	private String lokacia;
	private Student student;
	private String celeMenoStudenta;
	private String celeMenoDoucovatela;
	private String nazovPredmetu;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public LocalDate getZaciatok() {
		return zaciatok;
	}
	public void setZaciatok(LocalDate zaciatok) {
		this.zaciatok = zaciatok;
	}
	public int getTrvanie() {
		return trvanie;
	}
	public void setTrvanie(int trvanie) {
		this.trvanie = trvanie;
	}
	public double getCena() {
		return cena;
	}
	public void setCena(double cena) {
		this.cena = cena;
	}
	public DoucovanyPredmet getPredmet() {
		return predmet;
	}
	public void setPredmet(DoucovanyPredmet predmet) {
		this.predmet = predmet;
	}
	public Doucovatel getDoucovatel() {
		return doucovatel;
	}
	public void setDoucovatel(Doucovatel doucovatel) {
		this.doucovatel = doucovatel;
	}
	public String getLokacia() {
		return lokacia;
	}
	public void setLokacia(String lokacia) {
		this.lokacia = lokacia;
	}
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public String getOkruh() {
		return okruh;
	}
	public void setOkruh(String okruh) {
		this.okruh = okruh;
	}
	public String getCas() {
		return cas;
	}
	public void setCas(String cas) {
		this.cas = cas;
	}
	
	public String getCeleMenoStudenta() {
		if (student != null) 
			return student.getMeno() + " " + student.getPriezvisko();
		else 
			return "";
	}
	public void setCeleMenoStudenta(String celeMenoStudenta) {
		this.celeMenoStudenta = celeMenoStudenta;
	}
	public String getCeleMenoDoucovatela() {
		if (doucovatel != null) 
			return doucovatel.getMeno() + " " + doucovatel.getPriezvisko();
		else 
			return "";
		
		
	}
	public void setCeleMenoDoucovatela(String celeMenoDoucovatela) {
		this.celeMenoDoucovatela = celeMenoDoucovatela;
	}
	public String getNazovPredmetu() {
		if (predmet != null)
			return predmet.getNazov() + ", " + predmet.getStupenStudia();
		else 
			return "";
	}
	public void setNazovPredmetu(String nazovPredmetu) {
		this.nazovPredmetu = nazovPredmetu;
	}
	
	
	

}
