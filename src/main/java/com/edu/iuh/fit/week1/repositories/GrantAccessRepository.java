package com.edu.iuh.fit.week1.repositories;

import com.edu.iuh.fit.week1.models.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;

public class GrantAccessRepository {
    public ArrayList<GrantAccess> getAllGrantAccesses() throws SQLException, ClassNotFoundException {
        ArrayList<GrantAccess> grantAccesses = null;
        Connection con = ConnectDB.getInstance().getConnection();
        String sql = "SELECT * from grant_access";
        try {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            grantAccesses = new ArrayList<>();
            while(rs.next()) {
                Role role = new Role(rs.getInt(1));
                Account account = new Account(rs.getInt(2));
                Grant grant = Grant.valueOf(rs.getString(3));
                String note = rs.getString(4);
                GrantAccess grantAccess = new GrantAccess(role,account,grant,note);
                grantAccesses.add(grantAccess);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return grantAccesses;
    }
    public Optional<GrantAccess> getGrantAccessByRoleIdAndAcId(int roleId,int accountId) throws SQLException, ClassNotFoundException {
        Connection con = ConnectDB.getInstance().getConnection();
        PreparedStatement stm = null;
        String sql = "SELECT * from grant_access where role_id = ? and account_id = ?";
        try {
            stm = con.prepareStatement(sql);
            stm.setInt(1,roleId);
            stm.setInt(2,accountId);
            ResultSet rs = stm.executeQuery();
            while(rs.next()) {
                Role role = new Role(rs.getInt(1));
                Account account = new Account(rs.getInt(2));
                Grant grant = Grant.valueOf(rs.getString(3));
                String note = rs.getString(4);
                GrantAccess grantAccess = new GrantAccess(role,account,grant,note);
                return Optional.of(grantAccess);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                stm.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return Optional.empty();
    }
    public boolean insertGrantAccess(GrantAccess grantAccess) throws SQLException, ClassNotFoundException {
        Connection con = ConnectDB.getInstance().getConnection();
        PreparedStatement stm = null;
        int n=0;
        String sql = "Insert into grant_access values(?,?,?,?)";
        try {
            stm = con.prepareStatement(sql);
            stm.setInt(1,grantAccess.getRole().getRoleId());
            stm.setInt(2,grantAccess.getAccount().getAccountId());
            stm.setString(3, grantAccess.getGrant().toString());
            stm.setString(4,grantAccess.getNote());
            n=stm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                stm.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return n>0;
    }
        public boolean deleteGrantAccess(int role,int account) throws SQLException, ClassNotFoundException {
        Connection con = ConnectDB.getInstance().getConnection();
        PreparedStatement stm = null;
        int n=0;
        String sql = "Delete from grant_access where role_id=? and account_id=?";
        try {
            stm = con.prepareStatement(sql);
            stm.setInt(1,role);
            stm.setInt(2,account);
            n=stm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                stm.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return n>0;
    }
    public boolean deleteGrantAccessByAcId(int account) throws SQLException, ClassNotFoundException {
        Connection con = ConnectDB.getInstance().getConnection();
        PreparedStatement stm = null;
        int n=0;
        String sql = "Delete from grant_access where account_id=?";
        try {
            stm = con.prepareStatement(sql);
            stm.setInt(1,account);
            n=stm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                stm.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return n>0;
    }
}
