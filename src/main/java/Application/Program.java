package Application;

import model.entities.Department;
import model.entities.Seller;

import java.util.Date;

public class Program {
    public static void main(String[] args) {
        Department obj = new Department(1,"Livros");
        Seller seller = new Seller(1,"AdrianoB","adriano@gmail.com",new Date(),3000.00,obj);

        System.out.println(seller);
        System.out.println();
        System.out.println(obj);

    }
}
