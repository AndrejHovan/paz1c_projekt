package sk.upjs.ed.persistent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import sk.upjs.ed.entity.Doucovanie;
import sk.upjs.ed.entity.DoucovanyPredmet;
import sk.upjs.ed.entity.Student;

public class MySqlDoucovanyPredmetDao implements DoucovanyPredmetDao {

	private JdbcTemplate jdbcTemplate;
	
	public MySqlDoucovanyPredmetDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public void add(DoucovanyPredmet predmet) {
		SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
		simpleJdbcInsert.withTableName("doucovanepredmety");
		simpleJdbcInsert.usingGeneratedKeyColumns("id");
		simpleJdbcInsert.usingColumns("Nazov", "Stupen"); 
		Map<String,Object> hodnoty = new HashMap<>();
		hodnoty.put("Nazov",predmet.getNazov());
		hodnoty.put("Stupen", predmet.getStupenStudia());
		Long id = simpleJdbcInsert.executeAndReturnKey(hodnoty).longValue();
		predmet.setId(id);
	}

	@Override
	public List<DoucovanyPredmet> getAll() {
		String sql = "SELECT id, Nazov, Stupen FROM doucovanepredmety"; //vola sa to tak?v
		List<DoucovanyPredmet> predmety = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(DoucovanyPredmet.class));
		return predmety;
	}

	@Override
	public void save(DoucovanyPredmet predmet) {
		if (predmet == null) 
			throw new NullPointerException("predmet nemôže byť null");
		if (predmet.getId() == null) {
			add(predmet);
		} else {
			String sql = "UPDATE doucovanepredmety SET nazov = ?, stupen = ? WHERE id = ?";
			jdbcTemplate.update(sql, predmet.getNazov(), predmet.getStupenStudia(), predmet.getId());
		}		
	}

	@Override
	public void delete(long id) throws EntityNotFoundException{
		int deleted = jdbcTemplate.update("DELETE FROM doucovanepredmety WHERE id = ?", id);
		if (deleted == 0) {
			//urobit jednu exception triedu pre vsetky entity
			throw new EntityNotFoundException(id);
		}
		
	}

}
