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

import sk.upjs.ed.entity.DoucovanyPredmet;
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
		Map<String, Object> hodnoty = new HashMap<>();
		hodnoty.put("Meno", doucovatel.getMeno());
		hodnoty.put("Priezvisko", doucovatel.getPriezvisko());
		hodnoty.put("Aktivny", doucovatel.isAktivny());
		Long id = simpleJdbcInsert.executeAndReturnKey(hodnoty).longValue();
		doucovatel.setId(id);
		
		insertDoucovanyPredmet(doucovatel);
	}

	@Override
	public List<Doucovatel> getAll() {
		String sql = "SELECT id, Meno, Priezvisko, Aktivny, doucovanepredmety_has_doucovatel.DoucovanePredmety_idDoucovanePredmety AS predmetid "
				+ "FROM Doucovatel LEFT JOIN "
				+ "doucovanepredmety_has_doucovatel ON doucovatel.id = doucovanepredmety_has_doucovatel.doucovatel_idDoucovatel";
		// List<Doucovatel> doucovatelia = jdbcTemplate.query(sql, new
		// BeanPropertyRowMapper<>(Doucovatel.class));
		List<Doucovatel> doucovatelia = jdbcTemplate.query(sql, new ResultSetExtractor<List<Doucovatel>>() {

			@Override
			public List<Doucovatel> extractData(ResultSet rs) throws SQLException, DataAccessException {
				List<Doucovatel> doucovatelia = new ArrayList<>();

				List<DoucovanyPredmet> predmety = DaoFactory.INSTANCE.getPredmetDao().getAll();
				Map<Long, DoucovanyPredmet> mojaMapa = new HashMap<>();
				for (DoucovanyPredmet dp : predmety) {
					mojaMapa.put(dp.getId(), dp);
				}

				Doucovatel doucovatel = null;
				while (rs.next()) {
					Long id = rs.getLong("id");
					if (doucovatel == null || doucovatel.getId() != id) {
						doucovatel = new Doucovatel();
						doucovatel.setId(rs.getLong("id"));
						doucovatel.setMeno(rs.getString("meno"));
						doucovatel.setPriezvisko(rs.getString("priezvisko"));
						doucovatel.setAktivny(rs.getBoolean("aktivny"));
						doucovatel.setPredmety(new ArrayList<>());
						doucovatelia.add(doucovatel);
					}
					Long idPredmetu = rs.getLong("predmetid");
					if(!rs.wasNull()) {
						doucovatel.getPredmety().add(mojaMapa.get(idPredmetu));
					}
				}
				return doucovatelia;

			}
		});
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
			jdbcTemplate.update(sql, doucovatel.getMeno(), doucovatel.getPriezvisko(), doucovatel.isAktivny(),
					doucovatel.getId());
			insertDoucovanyPredmet(doucovatel);
		}

	}
	
	private void insertDoucovanyPredmet(Doucovatel doucovatel) {
		jdbcTemplate.update("DELETE FROM doucovanepredmety_has_doucovatel WHERE Doucovatel_idDoucovatel = ?", doucovatel.getId());
		if (doucovatel.getPredmety().size()>0) {
			StringBuilder sb = new StringBuilder();
			sb.append("INSERT INTO doucovanepredmety_has_doucovatel (DoucovanePredmety_idDoucovanePredmety,Doucovatel_idDoucovatel)");
			sb.append(" VALUES ");
			for (int i = 0; i < doucovatel.getPredmety().size(); i++) {
				sb.append("("+ doucovatel.getPredmety().get(i).getId() + "," + doucovatel.getId()+ "),");
			}
			String insertSQL = sb.substring(0,sb.length()-1);
			jdbcTemplate.update(insertSQL);
		}
	}

	@Override
	public void delete(long id) throws EntityNotFoundException {
		int deleted = jdbcTemplate.update("DELETE FROM doucovatel WHERE id = ?", id);
		if (deleted == 0) {
			throw new EntityNotFoundException(id);
		}
	}

}
