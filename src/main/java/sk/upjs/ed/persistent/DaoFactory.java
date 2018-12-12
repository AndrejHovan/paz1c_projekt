package sk.upjs.ed.persistent;

import org.springframework.jdbc.core.JdbcTemplate;

import com.mysql.cj.jdbc.MysqlDataSource;

public enum DaoFactory {
INSTANCE;
	
	private JdbcTemplate jdbcTemplate;
	private StudentDao studentDao;
	private DoucovatelDao doucovatelDao;
	private DoucovanieDao doucovanieDao;
	private DoucovanyPredmetDao predmetDao;
	
	public StudentDao getStudentDao() {
		if (studentDao == null)
			studentDao = new MySqlStudentDao(getJdbcTemplate());
		return studentDao;
	}
	
	public DoucovatelDao getDoucovatelDao() {
		if (doucovatelDao == null)
			doucovatelDao = new MySqlDoucovatelDao(getJdbcTemplate());
		return doucovatelDao;
	}
	public DoucovanieDao getDoucovanieDao() {
		if (doucovanieDao == null)
			doucovanieDao = new MySqlDoucovanieDao(getJdbcTemplate());
		return doucovanieDao;
		
	}
	
	public DoucovanyPredmetDao getPredmetDao() {
		if (predmetDao == null)
			predmetDao = new MySqlDoucovanyPredmetDao(getJdbcTemplate());
		return predmetDao;
	}
	/*public WorkshopDao getWorkshopDao() {
		if (workshopDao == null) {
			workshopDao = new MysqlWorkshopDao(getJdbcTemplate());
		}
		return workshopDao;
	}*/
	
	private JdbcTemplate getJdbcTemplate() {
		if (jdbcTemplate == null) {
			MysqlDataSource dataSource = new MysqlDataSource();
			dataSource.setUser("ed2");
			dataSource.setPassword("paz1cprojekt");
			dataSource.setDatabaseName("evidenciadoucovani");
			dataSource.setUrl("jdbc:mysql://localhost/evidenciadoucovani?serverTimezone=Europe/Bratislava");
			jdbcTemplate = new JdbcTemplate(dataSource);
		}
		return jdbcTemplate;
	}
}
