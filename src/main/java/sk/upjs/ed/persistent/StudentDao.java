package sk.upjs.ed.persistent;

import java.util.List;

import sk.upjs.ed.entity.Student;


public interface StudentDao {
	
	void add(Student student);
	
	List<Student> getAll();

	void delete(Long id);

	void save(Student student);

}
