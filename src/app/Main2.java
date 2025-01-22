package app;


import entities.Department;
import model.DaoFactory;
import model.DepartmentDao;

import java.util.List;

public class Main2 {
    public static void main(String[] args) {


        DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
        Department department = departmentDao.findById(1);
        System.out.println(department);
        List<Department> departments = departmentDao.findAll();

        for (Department department1 : departments) {
            System.out.println(department1);
        }

       System.out.println("Department added");
        department = departmentDao.findById(6);

        department.setName("Google");

        departmentDao.update(department);

        System.out.println("Department updated");


        departmentDao.deleteById(7);

        System.out.println("Department deleted");
    }
}
