package sk.upjs.ed.entity;

import java.util.List;

public class Doucovatel {
	
	private Long id;
	private String meno;
	private String priezvisko;
	private boolean aktivny;
	private List<DoucovanyPredmet> predmety;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMeno() {
		return meno;
	}
	public void setMeno(String meno) {
		this.meno = meno;
	}
	public String getPriezvisko() {
		return priezvisko;
	}
	public void setPriezvisko(String priezvisko) {
		this.priezvisko = priezvisko;
	}
	public boolean isAktivny() {
		return aktivny;
	}
	public void setAktivny(boolean aktivny) {
		this.aktivny = aktivny;
	}
	public List<DoucovanyPredmet> getPredmety() {
		return predmety;
	}
	public void setPredmety(List<DoucovanyPredmet> predmety) {
		this.predmety = predmety;
	}
	@Override
	public String toString() {
		return "Doucovatel [id=" + id + ", meno=" + meno + ", priezvisko=" + priezvisko + ", aktivny=" + aktivny
				+ ", predmety=" + predmety + "]";
	}
	
	
	
}
