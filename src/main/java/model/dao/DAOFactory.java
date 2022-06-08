package model.dao;

import db.DB;
import model.dao.impl.DepartmentDaoJDBC;
import model.dao.impl.SellerDaoJDBC;

public class DAOFactory {
    public static SellerDAO createSellerDao(){
        //Para nao precisar expor a implementação, deixa a interface
       return new SellerDaoJDBC(DB.getConnection());

    }

    public static DepartmentDAO createSellerDAO(){
        return new DepartmentDaoJDBC(DB.getConnection());
    }
}
