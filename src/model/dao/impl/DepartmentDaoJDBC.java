package model.dao.impl;

import db.DB;
import db.DbException;
import entities.Department;
import entities.Seller;
import model.DepartmentDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DepartmentDaoJDBC implements DepartmentDao {

    private Connection conn;

    public DepartmentDaoJDBC(Connection connection) {
        this.conn = connection;
    }

    @Override
    public void insert(Department department) {
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("INSERT INTO department "
            + "(Name) "
            + "VALUES "
            + "(?)");

            ps.setString(1, department.getName());
            int row = ps.executeUpdate();

            System.out.println("Inserted row: " + row);

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public void update(Department department) {
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("UPDATE department " +
                    "SET Name = ? " +
                    "WHERE Id = ?");

            ps.setString(1,department.getName());
            ps.setInt(2,department.getId());
            ps.executeUpdate();
        }
        catch (SQLException e) {
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
            ps = conn.prepareStatement("DELETE FROM department WHERE Id = ? ");

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
    public Department findById(Integer id) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            ps = conn.prepareStatement("SELECT * "
                    + "FROM department "
                    + "WHERE Id = ?");

            ps.setInt(1, id);
            rs = ps.executeQuery();

            if(rs.next()){
                Department dep = instantiateDepartment(rs);
                return dep;
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

    @Override
    public List<Department> findAll() {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            ps = conn.prepareStatement(
                    "SELECT *  "
                            + "FROM department "
                            + "ORDER BY Name");

            rs = ps.executeQuery();

            List<Department> listDepartment = new ArrayList<>();
            Map<Integer, Department> map = new HashMap<>();
            while (rs.next()){

                Department dep = map.get(rs.getInt("Id"));

                if(dep == null){
                    dep = instantiateDepartment(rs);
                    map.put(rs.getInt("Id"), dep);
                }
                Department dep2 = instantiateDepartment(rs);
                listDepartment.add(dep2);
            }
            return listDepartment;
        }catch (SQLException e){
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(ps);
            DB.closeResultSet(rs);
        }
    }

    private Department instantiateDepartment(ResultSet rs) throws SQLException {
        Department dep = new Department();
        dep.setId(rs.getInt("Id"));
        dep.setName(rs.getString("Name"));
        return dep;
    }
}
