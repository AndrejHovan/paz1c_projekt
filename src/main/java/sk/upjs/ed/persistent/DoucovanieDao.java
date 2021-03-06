package sk.upjs.ed.persistent;

import java.util.List;

import sk.upjs.ed.entity.Doucovanie;


public interface DoucovanieDao {

	void add(Doucovanie doucovanie);

	List<Doucovanie> getAll();
	
	//malo by getnut vsetky doucovania STARSIE ako momentalny datum
	List<Doucovanie> getMinule(); 

	//malo by getnut vsetky doucovania NOVSIE ako momentalny datum
	List<Doucovanie> getBuduce(); 	
	
	void save(Doucovanie doucovanie);

	void delete(long id);
	
}
