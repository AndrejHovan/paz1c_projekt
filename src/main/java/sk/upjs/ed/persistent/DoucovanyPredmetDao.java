package sk.upjs.ed.persistent;

import java.util.List;

import sk.upjs.ed.entity.DoucovanyPredmet;;

public interface DoucovanyPredmetDao {

	List<DoucovanyPredmet> getAll();

	void add(DoucovanyPredmet predmet);

	void save(DoucovanyPredmet predmet);

	void delete(long id);

	//DoucovanyPredmet getById(long id);
	
}
