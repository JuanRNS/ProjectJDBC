package app;


import entities.Department;
import entities.Seller;
import model.DaoFactory;
import model.SellerDao;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        SellerDao seller = DaoFactory.createSellerDao();

        Seller sel = seller.findById(3);

        System.out.println(sel);
        System.out.println("--------------test2----------");
        Department department = new Department(2,null);
        List<Seller> list = seller.findByDepartment(department);

        for (Seller s : list) {
            System.out.println(s);
        }

        System.out.println("--------------test3----------");
        List<Seller> list2 = seller.findAll();
        for (Seller s : list2) {
            System.out.println(s);
        }

        }
    }
