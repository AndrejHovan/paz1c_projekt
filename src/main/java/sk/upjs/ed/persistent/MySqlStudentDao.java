package sk.upjs.ed.persistent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import sk.upjs.ed.entity.Student;

public class MySqlStudentDao implements StudentDao {

	private JdbcTemplate jdbcTemplate;
	
	public MySqlStudentDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public void add(Student student) {
		SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
		simpleJdbcInsert.withTableName("Student");
		simpleJdbcInsert.usingGeneratedKeyColumns("id");
		//simpleJdbcInsert.usingColumns("Meno", "Priezvisko", "Stupeň štúdia", "Tel. kontakt", "Email");
		simpleJdbcInsert.usingColumns("Meno", "Priezvisko", "StupenStudia","Telefon", "Email", "Aktivny"); 
		//aktivny neexistuje v databaze v nasej scheme taky stlpec nebol.. preto to nejde..  
		// uz existuje pridal som to tam ale teraz je problem s tym ze som dal ze to
		//nemoze byt null takze nieco sa tam babre aj ked zaskrtnem ze je aktivny
		Map<String,Object> hodnoty = new HashMap<>();
		hodnoty.put("Meno",student.getMeno());
		hodnoty.put("Priezvisko",student.getPriezvisko());
		hodnoty.put("StupenStudia", student.getStupenStudia());
		hodnoty.put("Telefon",student.getTelefon());
		hodnoty.put("Email",student.getEmail());
		hodnoty.put("Aktivny",student.isAktivny());
		Long id = simpleJdbcInsert.executeAndReturnKey(hodnoty).longValue();
		student.setId(id);
		
	}
	
	@Override
	public List<Student> getAll() {
		//String sql = "SELECT id, Meno, Priezvisko, StupenStudia, Tel. kontakt, Email, Aktivny FROM student";
		//ked pridam aktivny alebo stupen studia tak hazdze invalid syntax vynimku
		String sql = "SELECT id, Meno, Priezvisko, StupenStudia, Telefon, Email, Aktivny FROM student";
		List<Student> studenti = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Student.class));
		return studenti;
	}
	
	/*@Override
	public Student getById(long id) {
		String sql = "SELECT Meno, Priezvisko, StupenStudia, Telefon, Email, Aktivny FROM student WHERE id = '" + id + "'" ;
		Student student = (Student) jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Student.class));
		return student;
	}*/

	@Override
	public void save(Student student) throws NullPointerException{
		if (student == null) 
			throw new NullPointerException("študent nemôže byť null");
		if (student.getId() == null) {
			add(student);
		} else {
			//toto neviem presne ako vyplnit
			//String sql = "UPDATE student SET meno = ?, priezvisko = ?, stupenStudia = ?, telefon = ?, email = ? WHERE id = ?";
			String sql = "UPDATE student SET meno = ?, priezvisko = ?, stupenStudia = ?, telefon = ?, email = ?, aktivny = ? WHERE id = ?";
			//jdbcTemplate.update(sql, student.getMeno(), student.getPriezvisko(), student.getStupenStudia(), student.getTelefon(), student.getEmail(), student.getId());
			jdbcTemplate.update(sql, student.getMeno(), student.getPriezvisko(), student.getStupenStudia(), student.getTelefon(), student.getEmail(), student.isAktivny(),student.getId());
		}

		
	}
	
	@Override
	public void delete(long id) throws EntityNotFoundException {
		int deleted = jdbcTemplate.update("DELETE FROM student WHERE id = ?", id);
		if (deleted == 0) {
			//urobit jednu exception triedu pre vsetky entity
			throw new EntityNotFoundException(id);
		}
		
	}

}
