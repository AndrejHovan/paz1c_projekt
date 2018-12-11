package sk.upjs.ed.persistent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import sk.upjs.ed.entity.Doucovatel;

public class MySqlDoucovatelDao implements DoucovatelDao {

	private JdbcTemplate jdbcTemplate;
	
	public MySqlDoucovatelDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public void add(Doucovatel doucovatel) {
		SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
		simpleJdbcInsert.withTableName("Doucovatel");
		simpleJdbcInsert.usingGeneratedKeyColumns("id");
		simpleJdbcInsert.usingColumns("Meno", "Priezvisko", "Aktivny");
		Map<String,Object> hodnoty = new HashMap<>();
		hodnoty.put("Meno",doucovatel.getMeno());
		hodnoty.put("Priezvisko",doucovatel.getPriezvisko());
		hodnoty.put("Aktivny",doucovatel.isAktivny());
		Long id = simpleJdbcInsert.executeAndReturnKey(hodnoty).longValue();
		doucovatel.setId(id);
	}

	@Override
	public List<Doucovatel> getAll() {
		String sql = "SELECT id, Meno, Priezvisko, Aktivny FROM Doucovatel";
		List<Doucovatel> doucovatelia = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Doucovatel.class));
		return doucovatelia;
	}

	@Override
	public void save(Doucovatel doucovatel) throws NullPointerException {
		if (doucovatel == null) 
			throw new NullPointerException("doučovatel nemôže byť null");
		if (doucovatel.getId() == null) {
			add(doucovatel);
		} else {
			String sql = "UPDATE doucovatel SET meno = ?, priezvisko = ?, aktivny = ? WHERE id = ?";
			jdbcTemplate.update(sql, doucovatel.getMeno(), doucovatel.getPriezvisko(), doucovatel.isAktivny(), doucovatel.getId());
		}

	}

	@Override
	public void delete(long id) throws DoucovateltNotFoundException {
		int deleted = jdbcTemplate.update("DELETE FROM doucovatel WHERE id = ?", id);
		if (deleted == 0) {
			throw new DoucovateltNotFoundException(id);
		}
	}

}
