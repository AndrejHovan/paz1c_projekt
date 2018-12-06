package sk.upjs.ed.persistent;

import java.util.List;

import sk.upjs.ed.entity.Doucovatel;

public interface DoucovatelDao {

	void add(Doucovatel doucovatel);

	List<Doucovatel> getAll();
	
	void save(Doucovatel doucovatel);

	void delete(long id);
	
}
