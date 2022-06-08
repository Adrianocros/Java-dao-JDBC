package model.dao;

import model.dao.impl.SellerDaoJDBC;

public class DAOFactory {
    public static SellerDAO createSellerDao(){
        //Para nao precisar expor a implementação, deixa a interface
       return new SellerDaoJDBC();
    }
}
