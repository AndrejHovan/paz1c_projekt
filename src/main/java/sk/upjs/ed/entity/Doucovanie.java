package sk.upjs.ed.entity;

import java.time.LocalDateTime;

import javax.print.attribute.standard.DateTimeAtCompleted;

public class Doucovanie {
	
	private Long id;
	private LocalDateTime zaciatok;
	private int trvanie; // v minutach
	private double cena;
	private String okruh;
	private DoucovanyPredmet predmet;
	private Doucovatel doucovatel;
	private String lokacia;
	private Student student;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public LocalDateTime getZaciatok() {
		return zaciatok;
	}
	public void setZaciatok(LocalDateTime zaciatok) {
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
	
	
	

}
