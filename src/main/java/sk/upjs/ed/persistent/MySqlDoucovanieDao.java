package sk.upjs.ed.persistent;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import sk.upjs.ed.entity.Doucovanie;
import sk.upjs.ed.entity.DoucovanyPredmet;
import sk.upjs.ed.entity.Doucovatel;
import sk.upjs.ed.entity.Student;

public class MySqlDoucovanieDao implements DoucovanieDao {

	private JdbcTemplate jdbcTemplate;

	public MySqlDoucovanieDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void add(Doucovanie doucovanie) {
		SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
		simpleJdbcInsert.withTableName("Doucovanie");
		simpleJdbcInsert.usingGeneratedKeyColumns("id");
		simpleJdbcInsert.usingColumns("Zaciatok", "Trvanie", "Cena", "Okruh", "Lokacia", "Cas", "Student_idStudent",
				"doucovatel_id", "DoucovanePredmety_idDoucovanePredmety");
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
		String sql = "SELECT doucovanie.id, doucovanie.Zaciatok, doucovanie.Trvanie, doucovanie.Cas, "
				+ "doucovanie.Cena, doucovanie.Okruh, doucovanie.Lokacia, doucovanie.Student_idStudent "
				+ ", doucovanie.DoucovanePredmety_idDoucovanePredmety, doucovanie.doucovatel_id  FROM doucovanie "
				+ "LEFT JOIN doucovatel ON doucovatel_id = doucovatel.id "
				+ "LEFT JOIN student ON Student_idStudent = student.id "
				+ "LEFT JOIN doucovanepredmety ON DoucovanePredmety_idDoucovanePredmety = doucovanepredmety.id";
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
				//vezmeme si vsetkych studentov a najdeme prave toho, ktoreho ma to doucovanie
				List<Student> vsetciStudenti = DaoFactory.INSTANCE.getStudentDao().getAll();
				long idStudenta = rs.getLong("Student_idStudent");
				for (Student student : vsetciStudenti) { 
					if (student.getId() == idStudenta) {
						doucovanie.setStudent(student);
						break;
					}
				}
				//to iste s doucovatelom
				List<Doucovatel> vsetciDoucovatelia = DaoFactory.INSTANCE.getDoucovatelDao().getAll();
				long idDoucovatela = rs.getLong("doucovatel_id");
				for (Doucovatel doucovatel : vsetciDoucovatelia) {
					if (doucovatel.getId() == idDoucovatela) {
						doucovanie.setDoucovatel(doucovatel);
						break;
					}
				}
				//to iste s predmetmi
				List<DoucovanyPredmet> vsetkyPredmety = DaoFactory.INSTANCE.getPredmetDao().getAll();
				long idPredmetu = rs.getLong("DoucovanePredmety_idDoucovanePredmety");
				for (DoucovanyPredmet predmet : vsetkyPredmety) {
					if (predmet.getId() == idPredmetu) {
						doucovanie.setPredmet(predmet);
						break;
					}
				} 
				return doucovanie;
			}
		});

	}

	@Override
	public void save(Doucovanie doucovanie) {
		if (doucovanie == null)
			throw new NullPointerException("doučovanie nemôže byť null");
		if (doucovanie.getId() == null) {
			add(doucovanie);
		} else {
			String sql = "UPDATE doucovanie SET zaciatok = ?, trvanie = ?, cena = ?, okruh = ?, lokacia = ?, cas = ?, Student_idStudent = ?, doucovatel_id = ?, DoucovanePredmety_idDoucovanePredmety = ? WHERE id = ?";
			jdbcTemplate.update(sql, doucovanie.getZaciatok(), doucovanie.getTrvanie(), doucovanie.getCena(),
					doucovanie.getOkruh(), doucovanie.getLokacia(), doucovanie.getCas(),
					doucovanie.getStudent().getId(), doucovanie.getDoucovatel().getId(),
					doucovanie.getPredmet().getId(), doucovanie.getId());

		}

	}

	@Override
	public void delete(long id) throws EntityNotFoundException {
		int deleted = jdbcTemplate.update("DELETE FROM doucovanie WHERE id = ?", id);
		if (deleted == 0) {
			throw new EntityNotFoundException(id);
		}

	}
	
	@Override
	public List<Doucovanie> getMinule() {
		String sql = "SELECT doucovanie.id, doucovanie.Zaciatok, doucovanie.Trvanie, doucovanie.Cas, "
				+ "doucovanie.Cena, doucovanie.Okruh, doucovanie.Lokacia, doucovanie.Student_idStudent "
				+ ", doucovanie.DoucovanePredmety_idDoucovanePredmety, doucovanie.doucovatel_id  FROM doucovanie "
				+ "LEFT JOIN doucovatel ON doucovatel_id = doucovatel.id "
				+ "LEFT JOIN student ON Student_idStudent = student.id "
				+ "LEFT JOIN doucovanepredmety ON DoucovanePredmety_idDoucovanePredmety = doucovanepredmety.id "
				+ "WHERE doucovanie.Zaciatok < CURDATE()";
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
				//vezmeme si vsetkych studentov a najdeme prave toho, ktoreho ma to doucovanie
				List<Student> vsetciStudenti = DaoFactory.INSTANCE.getStudentDao().getAll();
				long idStudenta = rs.getLong("Student_idStudent");
				for (Student student : vsetciStudenti) { 
					if (student.getId() == idStudenta) {
						doucovanie.setStudent(student);
						break;
					}
				}
				//to iste s doucovatelom
				List<Doucovatel> vsetciDoucovatelia = DaoFactory.INSTANCE.getDoucovatelDao().getAll();
				long idDoucovatela = rs.getLong("doucovatel_id");
				for (Doucovatel doucovatel : vsetciDoucovatelia) {
					if (doucovatel.getId() == idDoucovatela) {
						doucovanie.setDoucovatel(doucovatel);
						break;
					}
				}
				//to iste s predmetmi
				List<DoucovanyPredmet> vsetkyPredmety = DaoFactory.INSTANCE.getPredmetDao().getAll();
				long idPredmetu = rs.getLong("DoucovanePredmety_idDoucovanePredmety");
				for (DoucovanyPredmet predmet : vsetkyPredmety) {
					if (predmet.getId() == idPredmetu) {
						doucovanie.setPredmet(predmet);
						break;
					}
				} 
				return doucovanie;
			}
		});
	}
	

	
	// to iste co get all len sa odfiltruju doucovania ktore este neboli
	/*
	@Override
	public List<Doucovanie> getMinule() {
		//LEFT JOIN na fetch id ostatnych entit
		String sql = "SELECT doucovanie.id, doucovanie.Zaciatok, doucovanie.Trvanie, doucovanie.Cas, "
				+ "doucovanie.Cena, doucovanie.Okruh, doucovanie.Lokacia, doucovanie.Student_idStudent "
				+ ", doucovanie.DoucovanePredmety_idDoucovanePredmety, doucovanie.doucovatel_id  FROM doucovanie "
				+ "LEFT JOIN doucovatel ON doucovatel_id = doucovatel.id "
				+ "LEFT JOIN student ON Student_idStudent = student.id "
				+ "LEFT JOIN doucovanepredmety ON DoucovanePredmety_idDoucovanePredmety = doucovanepredmety.id";
		List<Doucovanie> doucovania = jdbcTemplate.query(sql, new RowMapper<Doucovanie>() {

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
				List<Student> vsetciStudenti = DaoFactory.INSTANCE.getStudentDao().getAll();
				long idStudenta = rs.getLong("Student_idStudent");
				for (Student student : vsetciStudenti) {
					if (student.getId() == idStudenta) {
						doucovanie.setStudent(student);
						break;
					}
				}
				List<Doucovatel> vsetciDoucovatelia = DaoFactory.INSTANCE.getDoucovatelDao().getAll();
				long idDoucovatela = rs.getLong("doucovatel_id");
				for (Doucovatel doucovatel : vsetciDoucovatelia) {
					if (doucovatel.getId() == idDoucovatela) {
						doucovanie.setDoucovatel(doucovatel);
						break;
					}
				}
				List<DoucovanyPredmet> vsetkyPredmety = DaoFactory.INSTANCE.getPredmetDao().getAll();
				long idPredmetu = rs.getLong("DoucovanePredmety_idDoucovanePredmety");
				for (DoucovanyPredmet predmet : vsetkyPredmety) {
					if (predmet.getId() == idPredmetu) {
						doucovanie.setPredmet(predmet);
						break;
					}
				} 
				return doucovanie;
			}
		});//filtracia
		List<Doucovanie> naZmazanie = new ArrayList<Doucovanie>();
		for (Doucovanie d : doucovania)
			if (d.getZaciatok().isAfter(LocalDate.now()))
				naZmazanie.add(d);
		doucovania.removeAll(naZmazanie);
		return doucovania;
	}
	*/
	@Override
	public List<Doucovanie> getBuduce() {
		String sql = "SELECT doucovanie.id, doucovanie.Zaciatok, doucovanie.Trvanie, doucovanie.Cas, "
				+ "doucovanie.Cena, doucovanie.Okruh, doucovanie.Lokacia, doucovanie.Student_idStudent "
				+ ", doucovanie.DoucovanePredmety_idDoucovanePredmety, doucovanie.doucovatel_id  FROM doucovanie "
				+ "LEFT JOIN doucovatel ON doucovatel_id = doucovatel.id "
				+ "LEFT JOIN student ON Student_idStudent = student.id "
				+ "LEFT JOIN doucovanepredmety ON DoucovanePredmety_idDoucovanePredmety = doucovanepredmety.id "
				+ "WHERE doucovanie.Zaciatok >= CURDATE()";
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
				//vezmeme si vsetkych studentov a najdeme prave toho, ktoreho ma to doucovanie
				List<Student> vsetciStudenti = DaoFactory.INSTANCE.getStudentDao().getAll();
				long idStudenta = rs.getLong("Student_idStudent");
				for (Student student : vsetciStudenti) { 
					if (student.getId() == idStudenta) {
						doucovanie.setStudent(student);
						break;
					}
				}
				//to iste s doucovatelom
				List<Doucovatel> vsetciDoucovatelia = DaoFactory.INSTANCE.getDoucovatelDao().getAll();
				long idDoucovatela = rs.getLong("doucovatel_id");
				for (Doucovatel doucovatel : vsetciDoucovatelia) {
					if (doucovatel.getId() == idDoucovatela) {
						doucovanie.setDoucovatel(doucovatel);
						break;
					}
				}
				//to iste s predmetmi
				List<DoucovanyPredmet> vsetkyPredmety = DaoFactory.INSTANCE.getPredmetDao().getAll();
				long idPredmetu = rs.getLong("DoucovanePredmety_idDoucovanePredmety");
				for (DoucovanyPredmet predmet : vsetkyPredmety) {
					if (predmet.getId() == idPredmetu) {
						doucovanie.setPredmet(predmet);
						break;
					}
				} 
				return doucovanie;
			}
		});

	}

	/*
	// skoro to iste ako getMinule len na konci je .isBefore
	@Override
	public List<Doucovanie> getBuduce() {
		String sql = "SELECT doucovanie.id, doucovanie.Zaciatok, doucovanie.Trvanie, doucovanie.Cas, "
				+ "doucovanie.Cena, doucovanie.Okruh, doucovanie.Lokacia, doucovanie.Student_idStudent "
				+ ", doucovanie.DoucovanePredmety_idDoucovanePredmety, doucovanie.doucovatel_id  FROM doucovanie "
				+ "LEFT JOIN doucovatel ON doucovatel_id = doucovatel.id "
				+ "LEFT JOIN student ON Student_idStudent = student.id "
				+ "LEFT JOIN doucovanepredmety ON DoucovanePredmety_idDoucovanePredmety = doucovanepredmety.id";
		List<Doucovanie> doucovania = jdbcTemplate.query(sql, new RowMapper<Doucovanie>() {

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
				List<Student> vsetciStudenti = DaoFactory.INSTANCE.getStudentDao().getAll();
				long idStudenta = rs.getLong("Student_idStudent");
				for (Student student : vsetciStudenti) {
					if (student.getId() == idStudenta) {
						doucovanie.setStudent(student);
						break;
					}
				}
				List<Doucovatel> vsetciDoucovatelia = DaoFactory.INSTANCE.getDoucovatelDao().getAll();
				long idDoucovatela = rs.getLong("doucovatel_id");
				for (Doucovatel doucovatel : vsetciDoucovatelia) {
					if (doucovatel.getId() == idDoucovatela) {
						doucovanie.setDoucovatel(doucovatel);
						break;
					}
				}
				List<DoucovanyPredmet> vsetkyPredmety = DaoFactory.INSTANCE.getPredmetDao().getAll();
				long idPredmetu = rs.getLong("DoucovanePredmety_idDoucovanePredmety");
				for (DoucovanyPredmet predmet : vsetkyPredmety) {
					if (predmet.getId() == idPredmetu) {
						doucovanie.setPredmet(predmet);
						break;
					}
				} 
				return doucovanie;
			}
		});
		List<Doucovanie> naZmazanie = new ArrayList<Doucovanie>();
		for (Doucovanie d : doucovania)
			if (d.getZaciatok().isBefore(LocalDate.now()))
				naZmazanie.add(d);
		doucovania.removeAll(naZmazanie);
		return doucovania;
	}
	*/
}
