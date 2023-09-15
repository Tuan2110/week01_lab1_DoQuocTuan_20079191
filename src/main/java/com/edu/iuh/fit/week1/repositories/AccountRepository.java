package com.edu.iuh.fit.week1.repositories;

import com.edu.iuh.fit.week1.models.Account;
import com.edu.iuh.fit.week1.models.Status;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AccountRepository {
    public ArrayList<Account> getAllAccount() throws SQLException, ClassNotFoundException {
        ArrayList<Account> accounts = null;
        Connection con = ConnectDB.getInstance().getConnection();
        String sql = "select * from account";
        try {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            accounts = new ArrayList<>();
            while (rs.next()){
                int accountId = rs.getInt(1);
                String fullName = rs.getString(2);
                String password = rs.getString(3);
                String email = rs.getString(4);
                String phone = rs.getString(5);
                Status status = Status.valueOf(rs.getString(6));
                Account account = new Account(accountId,fullName,password,email,phone,status);
                accounts.add(account);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return accounts;
    }
    public boolean insertAccount(Account account) throws SQLException, ClassNotFoundException {
        Connection con = ConnectDB.getInstance().getConnection();
        PreparedStatement stm = null;
        int n=0;
        String sql = "insert into account(full_name,PASSWORD,email,phone,status) values(?,?,?,?,?)";
        try {
            stm = con.prepareStatement(sql);
            stm.setString(1,account.getFullName());
            stm.setString(2,account.getPassword());
            stm.setString(3,account.getEmail());
            stm.setString(4,account.getPhone());
            stm.setString(5,account.getStatus().toString());
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
    public Optional<Account> login(String email,String password) throws SQLException, ClassNotFoundException {
        Account account = null;
        Connection con = ConnectDB.getInstance().getConnection();
        PreparedStatement stm = null;
        String sql = "Select * from account where email = ? and password = ?";
        try {
            stm = con.prepareStatement(sql);
            stm.setString(1,email);
            stm.setString(2,password);
            ResultSet rs = stm.executeQuery();
            while(rs.next()) {
                int accountId = rs.getInt(1);
                String fullName = rs.getString(2);
                String pass = rs.getString(3);
                String em = rs.getString(4);
                String phone = rs.getString(5);
                Status status = Status.valueOf(rs.getString(6));
                account = new Account(accountId,fullName,pass,em,phone,status);
                return Optional.of(account);
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
    public Optional<Account> getAccountByEmail(String email) throws SQLException, ClassNotFoundException {
        Account account = null;
        Connection con = ConnectDB.getInstance().getConnection();
        PreparedStatement stm = null;
        String sql = "Select * from account where email = ?";
        try {
            stm = con.prepareStatement(sql);
            stm.setString(1,email);
            ResultSet rs = stm.executeQuery();
            while(rs.next()) {
                int accountId = rs.getInt(1);
                String fullName = rs.getString(2);
                String pass = rs.getString(3);
                String em = rs.getString(4);
                String phone = rs.getString(5);
                Status status = Status.valueOf(rs.getString(6));
                account = new Account(accountId,fullName,pass,em,phone,status);
                return Optional.of(account);
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
    public Optional<Account> getAccountById(int id) throws SQLException, ClassNotFoundException {
        Account account = null;
        Connection con = ConnectDB.getInstance().getConnection();
        PreparedStatement stm = null;
        String sql = "Select * from account where account_id = ?";
        try {
            stm = con.prepareStatement(sql);
            stm.setInt(1,id);
            ResultSet rs = stm.executeQuery();
            while(rs.next()) {
                int accountId = rs.getInt(1);
                String fullName = rs.getString(2);
                String pass = rs.getString(3);
                String em = rs.getString(4);
                String phone = rs.getString(5);
                Status status = Status.valueOf(rs.getString(6));
                account = new Account(accountId,fullName,pass,em,phone,status);
                return Optional.of(account);
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
    public List<Account> getAccountsByRole(String roleName) throws SQLException, ClassNotFoundException {
        List accounts = null;
        Connection con = ConnectDB.getInstance().getConnection();
        PreparedStatement stm = null;
        String sql = "SELECT account.*\n" +
                "FROM     account INNER JOIN\n" +
                "                  grant_access ON account.account_id = grant_access.account_id \n" +
                "\t\t\t\t\t\tINNER JOIN\n" +
                "                  role ON grant_access.role_id = role.role_id\n" +
                "                  WHERE role_name = ?";
        try {
            stm = con.prepareStatement(sql);
            stm.setString(1,roleName);
            ResultSet rs = stm.executeQuery();
            accounts = new ArrayList<>();
            while(rs.next()) {
                int accountId = rs.getInt(1);
                String fullName = rs.getString(2);
                String pass = rs.getString(3);
                String em = rs.getString(4);
                String phone = rs.getString(5);
                Status status = Status.valueOf(rs.getString(6));
                Account account = new Account(accountId,fullName,pass,em,phone,status);
                accounts.add(account);
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
        return accounts;
    }

    public boolean updateAccount(Account account) throws SQLException, ClassNotFoundException {
        Connection con = ConnectDB.getInstance().getConnection();
        PreparedStatement stm = null;
        int n=0;
        String sql = "Update account set full_name = ?,phone=?,status=? where email=?";
        try {
            stm = con.prepareStatement(sql);
            stm.setString(1,account.getFullName());
            stm.setString(2,account.getPhone());
            stm.setString(3,account.getStatus().toString());
            stm.setString(4, account.getEmail());
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
    public boolean deleteAccount(int accountId) throws SQLException, ClassNotFoundException {
        Connection con = ConnectDB.getInstance().getConnection();
        PreparedStatement stm = null;
        int n=0;
        String sql = "Delete from account where account_id=?";
        try {
            stm = con.prepareStatement(sql);
            stm.setInt(1,accountId);
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
