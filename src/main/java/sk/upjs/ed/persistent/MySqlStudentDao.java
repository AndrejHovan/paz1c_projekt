package sk.upjs.ed.persistent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import sk.upjs.ed.entity.Student;
import sk.upjs.ed.entity.StupenStudia;

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
		simpleJdbcInsert.usingColumns("Meno", "Priezvisko", "StupenStudia","Telefon", "Email", "Aktivny"); 
		//Vytvorime mapu, ktora mapuje hodnoty k spravnym stlpcom
		Map<String,Object> hodnoty = new HashMap<>();
		hodnoty.put("Meno",student.getMeno());
		hodnoty.put("Priezvisko",student.getPriezvisko());
		hodnoty.put("StupenStudia", student.getStupenStudia());
		hodnoty.put("Telefon",student.getTelefon());
		hodnoty.put("Email",student.getEmail());
		hodnoty.put("Aktivny",student.isAktivny());
		//vlozime mapu do databazy
		Long id = simpleJdbcInsert.executeAndReturnKey(hodnoty).longValue();
		student.setId(id);
		
	}
	
	@Override
	public List<Student> getAll() {
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
			//toto neviem presne ako vyplnit    ,   student.getStupenStudia(),, StupenStudia = ?
			//String sql = "UPDATE student SET meno = ?, priezvisko = ?, stupenStudia = ?, telefon = ?, email = ? WHERE id = ?";
			String sql = "UPDATE student SET meno = ?, priezvisko = ?, telefon = ?, email = ?, aktivny = ? WHERE id = ?";
			//jdbcTemplate.update(sql, student.getMeno(), student.getPriezvisko(), student.getStupenStudia(), student.getTelefon(), student.getEmail(), student.getId());
			jdbcTemplate.update(sql, student.getMeno(), student.getPriezvisko(), student.getTelefon(), student.getEmail(), student.isAktivny(),student.getId());
			String sql2 = "UPDATE student SET stupenStudia = '" + student.getStupenStudia() + "' WHERE id = " + student.getId();
			jdbcTemplate.update(sql2);
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

	@Override
	public List<Student> getAllActive() {
		String sql = "SELECT id, Meno, Priezvisko, StupenStudia, Telefon, Email, Aktivny FROM student WHERE Aktivny = 1";
		List<Student> studenti = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Student.class));
		return studenti;
	}

}
