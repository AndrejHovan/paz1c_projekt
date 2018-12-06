package sk.upjs.ed.persistent;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import sk.upjs.ed.entity.Student;

public class MySqlStudentDao implements StudentDao {

	private JdbcTemplate jdbcTemplate;
	
	public MySqlStudentDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public List<Student> getAll() {
		String sql = "SELECT id, meno, priezvisko, stupenStudia, telefon, email FROM student";
		List<Student> studenti = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Student.class));
		return studenti;
	}

}
