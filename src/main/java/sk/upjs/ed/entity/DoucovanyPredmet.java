package sk.upjs.ed.entity;

import java.util.List;

public class DoucovanyPredmet {
	
	private Long id;
	private String nazov;
	private StupenStudia stupenStudia;
	//private String odvetvie;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNazov() {
		return nazov;
	}
	public void setNazov(String nazov) {
		this.nazov = nazov;
	}
	public StupenStudia getStupenStudia() {
		return stupenStudia;
	}
	public void setStupenStudia(StupenStudia stupenStudia) {
		this.stupenStudia = stupenStudia;
	}
	/*public String getOdvetvie() {
		return odvetvie;
	}
	public void setOdvetvie(String odvetvie) {
		this.odvetvie = odvetvie;
	}
	*/
	
	@Override
	public String toString() {
		return "DoucovanyPredmet [id=" + id + ", nazov=" + nazov + ", stupenStudia=" + stupenStudia + "]";// ", odvetvie="
				//+ odvetvie  //, docovatelia= " + doucovatelia + "]";
	}
	
	

}
