package Application;

import model.dao.DAOFactory;
import model.dao.DepartmentDAO;
import model.entities.Department;
import model.entities.Seller;

import java.util.Date;

public class ProgramDois {
    public static void main(String[] args) {

        DepartmentDAO departmentDAO = DAOFactory.createSellerDAO();

        System.out.println("\n=== Teste 01 Seller Insert ===");
        Department newDepartment = new Department(null,"Medicina");
        departmentDAO.insert(newDepartment);
        System.out.println("Novo departamento cadastrado: " + newDepartment.getId() + " - Nome: " + newDepartment.getName());

        System.out.println("\n=== Teste 02 Seller update ===");
    }
}
