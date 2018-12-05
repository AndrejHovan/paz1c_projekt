package sk.upjs.ed.entity;

public class Student {
	
	private Long id;
	private String meno;
	private String priezvisko;
	private StupenStudia stupenStudia;
	private String telefon;
	private String email;
	
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
	public StupenStudia getStupenStudia() {
		return stupenStudia;
	}
	public void setStupenStudia(StupenStudia stupenStudia) {
		this.stupenStudia = stupenStudia;
	}
	public String getTelefon() {
		return telefon;
	}
	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Override
	public String toString() {
		return "Student [id=" + id + ", meno=" + meno + ", priezvisko=" + priezvisko + ", stupenStudia=" + stupenStudia
				+ ", telefon=" + telefon + ", email=" + email + "]";
	}
		
}
