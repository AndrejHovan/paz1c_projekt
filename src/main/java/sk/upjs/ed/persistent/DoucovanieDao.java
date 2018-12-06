package sk.upjs.ed.persistent;

import java.util.List;

import sk.upjs.ed.entity.Doucovanie;


public interface DoucovanieDao {

	void add(Doucovanie doucovanie);

	List<Doucovanie> getAll();
	
	/*List<Doucovanie> getMinule(); //pre history okno

	List<Doucovanie> getBuduce(); //pre buduce okno, v oboch tychto metodach sa bude vyuzivat get all najprv
	//a potom sa to prefiltruje ze ktore su minule a ktore buduce, ale toto vsetko moze byt az v mySqlDoucovanieDao...
	*/ 
	
	void save(Doucovanie doucovanie);

	void delete(long id);
	
}
