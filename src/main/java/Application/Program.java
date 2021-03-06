package Application;

import model.dao.DAOFactory;
import model.dao.SellerDAO;
import model.entities.Department;
import model.entities.Seller;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

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

        System.out.println("\n=== Teste 05 Seller UPDATE ===");
        seller = sellerDAO.findById(1);
        seller.setName("Whinderson");
        sellerDAO.update(seller);
        System.out.println("Update realizado! ");

        System.out.println("\n=== Teste 06 Seller DELETE ===");
        System.out.print("Informe o ID para ser Deletado: ");
        int id = scanner.nextInt();
        sellerDAO.deleteById(id);
        System.out.println("Vendedor deletado! ");
    }

}
