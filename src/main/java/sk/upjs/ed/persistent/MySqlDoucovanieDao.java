package sk.upjs.ed.persistent;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import sk.upjs.ed.entity.Doucovanie;
import sk.upjs.ed.entity.Student;

public class MySqlDoucovanieDao implements DoucovanieDao{

	private JdbcTemplate jdbcTemplate;
	
	public MySqlDoucovanieDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public void add(Doucovanie doucovanie) {
		SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
		simpleJdbcInsert.withTableName("Doucovanie");
		simpleJdbcInsert.usingGeneratedKeyColumns("id");
		simpleJdbcInsert.usingColumns("Zaciatok", "Trvanie", "Cena", "Okruh", "Lokacia", "Cas", "Student_idStudent", "doucovatel_id", "DoucovanePredmety_idDoucovanePredmety");
		Map<String, Object> hodnoty = new HashMap<>();
		hodnoty.put("Zaciatok", doucovanie.getZaciatok());
		hodnoty.put("Trvanie", doucovanie.getTrvanie());
		hodnoty.put("Cena", doucovanie.getCena());
		hodnoty.put("Okruh", doucovanie.getOkruh());
		hodnoty.put("Lokacia", doucovanie.getLokacia());
		hodnoty.put("Cas", doucovanie.getCas());
		hodnoty.put("Student_idStudent", doucovanie.getStudent().getId());
		hodnoty.put("doucovatel_id", doucovanie.getDoucovatel().getId());
		hodnoty.put("DoucovanePredmety_idDoucovanePredmety", doucovanie.getPredmet().getId());
		
		
		Long id = simpleJdbcInsert.executeAndReturnKey(hodnoty).longValue();
		doucovanie.setId(id);
		
	}

	@Override
	public List<Doucovanie> getAll() {
		
		//String sql = "SELECT id, Meno, Priezvisko, Aktivny, doucovanepredmety_has_doucovatel.DoucovanePredmety_idDoucovanePredmety AS predmetid "
				//+ "FROM Doucovatel LEFT JOIN "
				//+ "doucovanepredmety_has_doucovatel ON doucovatel.id = doucovanepredmety_has_doucovatel.doucovatel_idDoucovatel";
		//
		String sql = "SELECT id, Zaciatok, Trvanie, Cas, Cena, Okruh, Lokacia, Student_idStudent, DoucovanePredmety_idDoucovanePredmety, doucovatel_id  FROM doucovanie"; 					
		//List<Doucovanie> doucovania = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Doucovanie.class));
		return jdbcTemplate.query(sql, new RowMapper<Doucovanie>() {

			@Override
			public Doucovanie mapRow(ResultSet rs, int rowNum) throws SQLException {
				Doucovanie doucovanie = new Doucovanie();
				doucovanie.setId(rs.getLong("id"));
				doucovanie.setZaciatok(LocalDate.parse(rs.getString("zaciatok")));
				doucovanie.setTrvanie(Integer.parseInt(rs.getString("trvanie")));
				doucovanie.setCas(rs.getString("cas"));
				doucovanie.setCena(Double.parseDouble(rs.getString("cena")));
				doucovanie.setOkruh(rs.getString("okruh"));
				doucovanie.setLokacia(rs.getString("lokacia"));
				return doucovanie;
			}
		});
				
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
