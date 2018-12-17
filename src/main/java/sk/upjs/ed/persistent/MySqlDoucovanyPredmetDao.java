package sk.upjs.ed.persistent;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import sk.upjs.ed.entity.Doucovanie;
import sk.upjs.ed.entity.DoucovanyPredmet;
import sk.upjs.ed.entity.Doucovatel;
import sk.upjs.ed.entity.Student;
import sk.upjs.ed.entity.StupenStudia;

public class MySqlDoucovanyPredmetDao implements DoucovanyPredmetDao {

	private JdbcTemplate jdbcTemplate;

	public MySqlDoucovanyPredmetDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void add(DoucovanyPredmet predmet) {
		List<DoucovanyPredmet> vsetkyPredmety = getAll();
		boolean predmetUzExistuje = false;
		for (DoucovanyPredmet dp : vsetkyPredmety) {
			if (predmet.getNazov().equals(dp.getNazov()) && predmet.getStupenStudia() == dp.getStupenStudia()) {
				predmetUzExistuje = true;
				break;
			}
		}
		if (!predmetUzExistuje) {
			SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
			simpleJdbcInsert.withTableName("doucovanepredmety");
			simpleJdbcInsert.usingGeneratedKeyColumns("id");
			simpleJdbcInsert.usingColumns("Nazov", "StupenStudia");
			Map<String, Object> hodnoty = new HashMap<>();
			hodnoty.put("Nazov", predmet.getNazov());
			hodnoty.put("StupenStudia", predmet.getStupenStudia());
			Long id = simpleJdbcInsert.executeAndReturnKey(hodnoty).longValue();
			predmet.setId(id);
		}

	}

	/*
	 * @Override public DoucovanyPredmet getById(long id) { String sql =
	 * "SELECT Nazov, StupenStudia FROM doucovanepredmety WHERE id = '" + id + "'" ;
	 * DoucovanyPredmet predmet = (DoucovanyPredmet) jdbcTemplate.query(sql, new
	 * BeanPropertyRowMapper<>(DoucovanyPredmet.class)); return predmet; }
	 */
	@Override
	public List<DoucovanyPredmet> getAll() {
		String sql = "SELECT id, Nazov, StupenStudia FROM doucovanepredmety"; 
		List<DoucovanyPredmet> predmety = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(DoucovanyPredmet.class));
		return predmety;
	}
	
	@Override
	public Long getCurrentAI() {
		String sql = "SELECT T.AUTO_INCREMENT " + 
				"FROM information_schema.TABLES T " + 
				"WHERE T.TABLE_SCHEMA = 'evidenciadoucovani' " + 
				"  AND T.TABLE_NAME = 'doucovanepredmety'";
		Long lastAI =  jdbcTemplate.queryForObject(sql,Long.class);
		return lastAI;
	}

	@Override
	public List<DoucovanyPredmet> getAllForList() {
		String sql = "SELECT id, Nazov, StupenStudia, doucovanepredmety_has_doucovatel.doucovanepredmety_iddoucovanepredmety "
				+ "FROM doucovanepredmety " + "I JOIN doucovanepredmety_has_doucovatel "
				+ "ON id = doucovanepredmety_has_doucovatel.doucovanepredmety_iddoucovanepredmety " + "ORDER BY id";
		// List<DoucovanyPredmet> predmety = jdbcTemplate.query(sql, new
		// BeanPropertyRowMapper<>(DoucovanyPredmet.class));
		// return predmety;
		List<DoucovanyPredmet> predmety = jdbcTemplate.query(sql, new ResultSetExtractor<List<DoucovanyPredmet>>() {

			@Override
			public List<DoucovanyPredmet> extractData(ResultSet rs) throws SQLException, DataAccessException {
				List<DoucovanyPredmet> predmety = new ArrayList<>();
				List<Long> idcka = new ArrayList<>();
				DoucovanyPredmet predmet = null;
				while (rs.next()) {
					Long id = rs.getLong("id");
					if (predmet == null || !idcka.contains(id)) {
						predmet = new DoucovanyPredmet();
						predmet.setId(rs.getLong("id"));
						predmet.setNazov(rs.getString("nazov"));
						predmet.setStupenStudia(StupenStudia.valueOf(rs.getString("stupenstudia")));
						predmety.add(predmet);
						idcka.add(predmet.getId());
					}
				}
				return predmety;

			}
		});
		return predmety;
	}

	@Override
	public void save(DoucovanyPredmet predmet) {
		if (predmet == null)
			throw new NullPointerException("predmet nemôže byť null");
		if (predmet.getId() == null) {
			add(predmet);
		} else {
			String sql = "UPDATE doucovanepredmety SET nazov = ?, stupenStudia = ? WHERE id = ?";
			jdbcTemplate.update(sql, predmet.getNazov(), predmet.getStupenStudia(), predmet.getId());
		}
	}

	@Override
	public void delete(long id) throws EntityNotFoundException {
		jdbcTemplate.update(
				"DELETE FROM doucovanepredmety_has_doucovatel WHERE DoucovanePredmety_idDoucovanePredmety= ?", id);
		int deleted = jdbcTemplate.update("DELETE FROM doucovanepredmety WHERE id = ?", id);
		if (deleted == 0) {
			// urobit jednu exception triedu pre vsetky entity
			throw new EntityNotFoundException(id);
		}

	}

}
