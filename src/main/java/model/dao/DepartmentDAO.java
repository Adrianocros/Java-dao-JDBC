package model.dao;

import model.entities.Department;

import java.util.List;

public interface DepartmentDAO {

    //Operação por inserir no banco de dodos este objeto que definiu como paramentro
    void insert(Department obj);
    void update(Deprecated obj);
    void deleteById(Integer id);
    Deprecated findById(Integer id);

    List<Department> findAll();

}
