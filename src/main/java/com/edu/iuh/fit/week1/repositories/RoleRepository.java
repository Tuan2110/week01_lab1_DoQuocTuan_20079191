package com.edu.iuh.fit.week1.repositories;

import com.edu.iuh.fit.week1.models.Account;
import com.edu.iuh.fit.week1.models.Role;
import com.edu.iuh.fit.week1.models.Status;

import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;

public class RoleRepository {
    public ArrayList<Role> getAllRoles() throws SQLException, ClassNotFoundException {
        ArrayList<Role> roles = null;
        Connection con = ConnectDB.getInstance().getConnection();
        String sql = "SELECT * from role";
        try {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            roles = new ArrayList<>();
            while(rs.next()) {
                int roleId = rs.getInt(1);
                String roleName = rs.getString(2);
                String description = rs.getString(3);
                Status status = Status.valueOf(rs.getString(4));
                Role role = new Role(roleId, roleName, description, status);
                roles.add(role);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return roles;
    }
    public Optional<Role> getRoleByName(String accountId) throws SQLException, ClassNotFoundException {
        Connection con = ConnectDB.getInstance().getConnection();
        PreparedStatement stm = null;
        String sql = "SELECT * from role where role_name = ?";
        try {
                stm = con.prepareStatement(sql);
                stm.setString(1,accountId);
                ResultSet rs = stm.executeQuery();
                while(rs.next()) {
                    int roleId = rs.getInt(1);
                    String roleName = rs.getString(2);
                    String description = rs.getString(3);
                    Status status = Status.valueOf(rs.getString(4));
                    Role role = new Role(roleId, roleName, description, status);
                    return Optional.of(role);
                }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
