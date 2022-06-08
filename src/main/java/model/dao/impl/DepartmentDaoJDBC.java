package model.dao.impl;

import db.DB;
import db.DBException;
import model.dao.DepartmentDAO;
import model.entities.Department;

import java.sql.*;
import java.util.List;

public class DepartmentDaoJDBC implements DepartmentDAO {

    private Connection conn;
    public DepartmentDaoJDBC(Connection conn){
        this.conn = conn;
    }

    @Override
    public void insert(Department obj) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "INSERT INTO department "
                    + "(Name)"
                    + "VALUES "
                    + "(?)",
                    Statement.RETURN_GENERATED_KEYS);

            st.setString(1,obj.getName());

            int rowsAffected = st.executeUpdate();
            if( rowsAffected > 0){
                ResultSet rs = st.getGeneratedKeys();
                if(rs.next()){
                    int id = rs.getInt(1);
                    obj.setId(id);
                }
                DB.closeResultSet(rs);
            }else {
                throw  new DBException("Erro insperado, nenhuma linha afetada");
            }

        }catch (SQLException e){
            throw  new DBException(e.getMessage());
        }
    }

    @Override
    public void update(Deprecated obj) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Deprecated findById(Integer id) {
        return null;
    }

    @Override
    public List<Department> findAll() {
        return null;
    }
}
