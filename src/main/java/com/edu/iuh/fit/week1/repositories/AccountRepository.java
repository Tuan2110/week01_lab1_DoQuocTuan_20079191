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
//    public NhanVien getNhanVienTheoMa(String ma) {
//        NhanVien nv= null;
//        ConnectDB.getInstance();
//        Connection con = ConnectDB.getConnection();
//        PreparedStatement stm = null;
//        String sql = "Select * from NhanVien where maNV = ?";
//        try {
//            stm = con.prepareStatement(sql);
//            stm.setString(1,ma);
//            ResultSet rs = stm.executeQuery();
//            while(rs.next()) {
//                String maNV = rs.getString(1);
//                String ho = rs.getString(2);
//                String ten = rs.getString(3);
//                int tuoi = rs.getInt(4);
//                boolean phai = rs.getBoolean(5);
//                PhongBan pb = new PhongBan(rs.getString(6));
//                double tienLuong = rs.getDouble(7);
//                nv = new NhanVien(maNV, ho, ten, tuoi, phai, pb, tienLuong);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }finally {
//            try {
//                stm.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//        return nv;
//    }
//    public boolean themNhanVien(NhanVien nv) {
//        ConnectDB.getInstance();
//        Connection con = ConnectDB.getConnection();
//        PreparedStatement stm = null;
//        int n=0;
//        String sql = "Insert into NhanVien values(?,?,?,?,?,?,?)";
//        try {
//            stm = con.prepareStatement(sql);
//            stm.setString(1,nv.getMaNV());
//            stm.setString(2,nv.getHo());
//            stm.setString(3,nv.getTen());
//            stm.setInt(4, nv.getTuoi());
//            stm.setBoolean(5, nv.isPhai());
//            stm.setString(6, nv.getPb().getMaPhong());
//            stm.setDouble(7, nv.getTienLuong());
//            n=stm.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }finally {
//            try {
//                stm.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//        return n>0;
//    }
//    public boolean suaNhanVien(NhanVien nv) {
//        ConnectDB.getInstance();
//        Connection con = ConnectDB.getConnection();
//        PreparedStatement stm = null;
//        int n=0;
//        String sql = "Update NhanVien set ho = ?,ten=?,tuoi=?,phai=?,maPhong=?,tienLuong=? where maNV=?";
//        try {
//            stm = con.prepareStatement(sql);
//            stm.setString(7,nv.getMaNV());
//            stm.setString(1,nv.getHo());
//            stm.setString(2,nv.getTen());
//            stm.setInt(3, nv.getTuoi());
//            stm.setBoolean(4, nv.isPhai());
//            stm.setString(5, nv.getPb().getMaPhong());
//            stm.setDouble(6, nv.getTienLuong());
//            n=stm.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }finally {
//            try {
//                stm.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//        return n>0;
//    }
//    public boolean xoaNhanVien(String nv) {
//        ConnectDB.getInstance();
//        Connection con = ConnectDB.getConnection();
//        PreparedStatement stm = null;
//        int n=0;
//        String sql = "Delete NhanVien where maNV=?";
//        try {
//            stm = con.prepareStatement(sql);
//            stm.setString(1,nv);
//            n=stm.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }finally {
//            try {
//                stm.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//        return n>0;
//    }
}
