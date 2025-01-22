package model.dao.impl;

import com.mysql.cj.x.protobuf.MysqlxPrepare;
import db.DB;
import db.DbException;
import entities.Department;
import entities.Seller;
import model.SellerDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellerDaoJDBC implements SellerDao {

    private Connection conn;

    public SellerDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Seller seller) {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("INSERT INTO seller "
            + "(Name, Email, BirthDate, BaseSalary, DepartmentId) "
            + "VALUES "
            + "(?, ?, ?, ?, ?) ",
                    Statement.RETURN_GENERATED_KEYS);

            ps.setString(1,seller.getName());
            ps.setString(2, seller.getEmail());
            ps.setDate(3,new java.sql.Date(seller.getBirthday().getTime()));
            ps.setDouble(4,seller.getBaseSalary());
            ps.setInt(5,seller.getDepartment().getId());

            int rows = ps.executeUpdate();
            System.out.println("Linhas afetadas " + rows);
            if (rows > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    seller.setId(id);
                    System.out.println("id gerado" + id);
                }
                DB.closeResultSet(rs);
            }
            else {
                throw new DbException("Insert failed");
            }

        }
        catch (SQLException e){
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(ps);
        }
    }

    @Override
    public void update(Seller seller) {
        PreparedStatement ps = null;

        try{
        ps = conn.prepareStatement("UPDATE seller " +
                "SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? " +
                "WHERE Id = ?");

        ps.setString(1,seller.getName());
        ps.setString(2, seller.getEmail());
        ps.setDate(3,new java.sql.Date(seller.getBirthday().getTime()));
        ps.setDouble(4,seller.getBaseSalary());
        ps.setInt(5,seller.getDepartment().getId());
        ps.setInt(6,seller.getId());

        ps.executeUpdate();

        }
        catch (SQLException e){
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(ps);
        }
    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("DELETE FROM seller WHERE Id = ? ");

            ps.setInt(1,id);
            ps.executeUpdate();
        }
        catch (SQLException e){
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(ps);
        }
    }

    @Override
    public Seller findById(Integer id) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            ps = conn.prepareStatement("SELECT seller. *, department.Name as DepName "
            + "FROM seller INNER JOIN department "
            + "ON seller .DepartmentId = department.Id "
            + "WHERE seller.Id = ?");

            ps.setInt(1, id);
            rs = ps.executeQuery();

            if(rs.next()){
                Department dep = instantiateDepartment(rs);
                Seller sel = instatiateSeller(rs,dep);
                return sel;
            }
            return null;
        }catch (SQLException e){
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(ps);
            DB.closeResultSet(rs);
        }


    }

    private Seller instatiateSeller(ResultSet rs, Department dep) throws SQLException {
        Seller sel = new Seller();
        sel.setDepartment(dep);
        sel.setId(rs.getInt("Id"));
        sel.setName(rs.getString("Name"));
        sel.setEmail(rs.getString("Email"));
        sel.setBaseSalary(rs.getDouble("BaseSalary"));
        sel.setBirthday(rs.getDate("BirthDate"));
        return sel;
    }

    private Department instantiateDepartment(ResultSet rs) throws SQLException {
        Department dep = new Department();
        dep.setId(rs.getInt("DepartmentId"));
        dep.setName(rs.getString("DepName"));
        return dep;
    }

    @Override
    public List<Seller> findAll() {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            ps = conn.prepareStatement(
                    "SELECT seller. *, department.Name as DepName "
                            + "FROM seller INNER JOIN department "
                            + "ON seller.DepartmentId = department.Id "
                            + "ORDER BY Name");

            rs = ps.executeQuery();

            List<Seller> listSellers = new ArrayList<>();
            Map<Integer, Department> map = new HashMap<>();
            while (rs.next()){

                Department dep = map.get(rs.getInt("DepartmentId"));

                if(dep == null){
                    dep = instantiateDepartment(rs);
                    map.put(rs.getInt("DepartmentId"), dep);
                }
                Seller sel = instatiateSeller(rs,dep);
                listSellers.add(sel);
            }
            return listSellers;
        }catch (SQLException e){
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(ps);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Seller> findByDepartment(Department department) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            ps = conn.prepareStatement(
                    "SELECT seller. *, department.Name as DepName "
                    + "FROM seller INNER JOIN department "
                    + "ON seller.DepartmentId = department.Id "
                    + "WHERE DepartmentId = ? "
                    + "ORDER BY Name");


            ps.setInt(1, department.getId());
            rs = ps.executeQuery();

            List<Seller> listSellers = new ArrayList<>();
            Map<Integer, Department> map = new HashMap<>();
            while (rs.next()){

                Department dep = map.get(rs.getInt("DepartmentId"));

                if(dep == null){
                    dep = instantiateDepartment(rs);
                    map.put(rs.getInt("DepartmentId"), dep);
                }
                Seller sel = instatiateSeller(rs,dep);
                listSellers.add(sel);
            }
            return listSellers;
        }catch (SQLException e){
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(ps);
            DB.closeResultSet(rs);
        }



    }
}
