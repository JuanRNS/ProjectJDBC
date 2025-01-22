package app;


import entities.Department;
import entities.Seller;
import model.DaoFactory;
import model.SellerDao;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        SellerDao seller = DaoFactory.createSellerDao();
        Seller sel = seller.findById(3);
        Department department = new Department(2,null);
        System.out.println(sel);
        System.out.println("--------------test2----------");
        List<Seller> list = seller.findByDepartment(department);

        for (Seller s : list) {
            System.out.println(s);
        }

        System.out.println("--------------test3----------");
        List<Seller> list2 = seller.findAll();
        for (Seller s : list2) {
            System.out.println(s);
        }

        System.out.println("--------------test4----------");

        Seller newSeller = new Seller(null,"Alguem" ,"Alguem@gmail.com" , new Date(),4000.0 ,department);
        seller.insert(newSeller);
        System.out.println(newSeller.getId());

        System.out.println("--------------test5----------");
        sel = seller.findById(1);

        sel.setName("Jonas");

        seller.update(sel);

        System.out.println("Updated Jonas");

        System.out.println("--------------test6----------");
        System.out.println("Digite o id para deletar");
        int id = sc.nextInt();
        seller.deleteById(id);
        System.out.println("Deleted");

        }

    }
