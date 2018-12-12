package sk.upjs.ed.persistent;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import sk.upjs.ed.entity.Doucovanie;
import sk.upjs.ed.entity.DoucovanyPredmet;

public class MySqlDoucovanyPredmetDao implements DoucovanyPredmetDao {

	private JdbcTemplate jdbcTemplate;
	
	public MySqlDoucovanyPredmetDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public void add(DoucovanyPredmet predmet) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<DoucovanyPredmet> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(DoucovanyPredmet predmet) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(long id) {
		// TODO Auto-generated method stub
		
	}

}
