package Application;

import model.dao.DAOFactory;
import model.dao.SellerDAO;
import model.entities.Department;
import model.entities.Seller;

import java.util.Date;
import java.util.List;

public class Program {
    public static void main(String[] args) {

        SellerDAO sellerDAO = DAOFactory.createSellerDao();

        System.out.println("=== Teste 01 Sellet FindById ===");
        Seller seller = sellerDAO.findById(3);
        System.out.println(seller);

        System.out.println("\n=== Teste 02 Sellet FindByDepartment ===");
        Department department = new Department(2, null);
        List<Seller> list = sellerDAO.findByDepartment(department);
        for(Seller obj : list){
            System.out.println(obj);
        }
        System.out.println("\n=== Teste 03 Seller findAll ===");
        list = sellerDAO.findAll();
        for(Seller obj : list){
            System.out.println(obj);
        }

        System.out.println("\n=== Teste 04 Seller Insert ===");
        Seller newSeller = new Seller(null,"Maria","maria@hotmail.com",new Date(), 4400.00,department);
        sellerDAO.insert(newSeller);
        System.out.println("Novo vendedor cadastrado: " + newSeller.getId() + " - Nome: " + newSeller.getName());
    }
}
