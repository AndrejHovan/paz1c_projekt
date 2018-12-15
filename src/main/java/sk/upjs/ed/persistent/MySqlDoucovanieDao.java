package sk.upjs.ed.persistent;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import sk.upjs.ed.entity.Doucovanie;
import sk.upjs.ed.entity.Student;

public class MySqlDoucovanieDao implements DoucovanieDao{

	private JdbcTemplate jdbcTemplate;
	
	public MySqlDoucovanieDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public void add(Doucovanie doucovanie) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Doucovanie> getAll() {
		
		//tento riadok treba dat spravne
		String sql = "SELECT id, Zaciatok, Trvanie, Cas, Cena, Okruh, Doucovatel, Lokacia FROM doucovanie";
		List<Doucovanie> doucovania = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Doucovanie.class));
		return doucovania;
	}

	@Override
	public void save(Doucovanie doucovanie) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Doucovanie> getMinule() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Doucovanie> getBuduce() {
		// TODO Auto-generated method stub
		return null;
	}

}
