package sk.upjs.ed.persistent;

import java.util.List;

import sk.upjs.ed.entity.Student;


public interface StudentDao {
	
	List<Student> getAll();

}
