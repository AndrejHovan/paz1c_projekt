package sk.upjs.ed.persistent;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import sk.upjs.ed.entity.Doucovanie;

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(Doucovanie doucovanie) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(long id) {
		// TODO Auto-generated method stub
		
	}

}
