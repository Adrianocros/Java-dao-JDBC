package Application;

import model.dao.DAOFactory;
import model.dao.SellerDAO;
import model.entities.Department;
import model.entities.Seller;

import java.util.Date;

public class Program {
    public static void main(String[] args) {

        SellerDAO sellerDAO = DAOFactory.createSellerDao();

        System.out.println("=== Teste 01 Sellet FindById ===");
        Seller seller = sellerDAO.findById(3);

        System.out.println(seller);

    }
}
