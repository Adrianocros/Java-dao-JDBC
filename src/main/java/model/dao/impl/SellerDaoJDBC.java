package model.dao.impl;

import db.DB;
import db.DBException;
import model.dao.SellerDAO;
import model.entities.Department;
import model.entities.Seller;

import javax.imageio.IIOException;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellerDaoJDBC  implements SellerDAO {

    //dependencia da conexao
    private Connection conn;
    public SellerDaoJDBC(Connection conn){
        this.conn = conn;
    }

    @Override
    public void insert(Seller obj) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
              "INSERT INTO seller "
                + "(Name,Email,BirthDate,BaseSalary,DepartmentId) "
                + "VALUES "
                + "(?,?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            st.setString(1,obj.getName());
            st.setString(2,obj.getEmail());
            st.setDate(3,new java.sql.Date(obj.getBirthDate().getTime()));
            st.setDouble(4,obj.getBaseSalary());
            st.setInt(5,obj.getDepartment().getId());

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
            throw new DBException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void update(Seller obj) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "UPDATE seller "
                    + "SET Name = ?,Email = ?,BirthDate = ?,BaseSalary = ?,DepartmentId = ? "
                    + "WHERE Id = ?");

            st.setString(1,obj.getName());
            st.setString(2,obj.getEmail());
            st.setDate(3,new java.sql.Date(obj.getBirthDate().getTime()));
            st.setDouble(4,obj.getBaseSalary());
            st.setInt(5,obj.getDepartment().getId());
            st.setInt(6,obj.getId());

            int rowsAffected = st.executeUpdate();

        }catch (SQLException e){
            throw new DBException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
        }

    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "DELETE FROM seller WHERE Id = ? ");

            st.setInt(1,id);

            int rowsAffected = st.executeUpdate();

            if (rowsAffected == 0 ){
                throw new DBException("Não foi possivel deletar o ID informado! ");
            }

        }catch (SQLException e){
            throw new DBException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
        }

    }

    @Override
    public Seller findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT seller.*,department.Name as DepName "
                    + "FROM seller INNER JOIN department "
                    + "ON seller.departmentId = department.Id "
                    + "WHERE seller.Id = ? " );
            st.setInt(1,id);
            rs = st.executeQuery();

            //Verifica se veio resultado
            if (rs.next()){
                //Instanciamos o departamento e setamos os valores dele
                Department dep = instantieteDepartment(rs);
                Seller obj = instantieteSeller(rs, dep);
                return obj;

            }return null;

        }catch (SQLException e){
            throw new DBException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }
    private Seller instantieteSeller(ResultSet rs, Department dep) throws SQLException{
        Seller obj = new  Seller();
        obj.setId(rs.getInt("Id"));
        obj.setName(rs.getString("Name"));
        obj.setEmail(rs.getString("Email"));
        obj.setBaseSalary(rs.getDouble("BaseSalary"));
        obj.setBirthDate(rs.getDate("Birthdate"));
        obj.setDepartment(dep);
        return obj;
    }

    private Department instantieteDepartment(ResultSet rs) throws SQLException {
        Department dep = new  Department();
        dep.setId(rs.getInt("DepartmentId"));
        dep.setName(rs.getString("DepName"));
        return dep;
    }


    @Override
    public List<Seller> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT seller.*,department.Name as DepName "
                            + "FROM seller INNER JOIN department "
                            + "ON seller.departmentId = department.Id "
                            + "ORDER BY Name" );

            rs = st.executeQuery();

            List<Seller> list = new ArrayList<>();
            Map<Integer, Department> map  = new HashMap<>();

            //Verifica se veio resultado e se o Depto existe e faz
            while (rs.next()){

                Department dep = map.get(rs.getInt("DepartmentId"));

                //Instancia todos os vendedores sem repetição de departamento
                if(dep == null){
                    dep = instantieteDepartment(rs);
                    map.put(rs.getInt("DepartmentId"),dep);
                }
                Seller obj = instantieteSeller(rs, dep);
                list.add(obj);
            }
            return list;
        }catch (SQLException e){
            throw new DBException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Seller> findByDepartment(Department department) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT seller.*,department.Name as DepName "
                    + "FROM seller INNER JOIN department "
                    + "ON seller.departmentId = department.Id "
                    + "WHERE DepartmentId = ? "
                    + "ORDER BY Name" );
            st.setInt(1,department.getId());
            rs = st.executeQuery();

            List<Seller> list = new ArrayList<>();
            Map<Integer, Department> map  = new HashMap<>();

            //Verifica se veio resultado e se o Depto existe e faz
            while (rs.next()){

                Department dep = map.get(rs.getInt("DepartmentId"));

                //Caso nao existe o dep o map retorna null instancia e retorna a referencia para o mesmo obj
                if(dep == null){
                    dep = instantieteDepartment(rs);
                    map.put(rs.getInt("DepartmentId"),dep);
                }
                Seller obj = instantieteSeller(rs, dep);
                list.add(obj);
            }
            return list;

        }catch (SQLException e){
            throw new DBException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }
}
