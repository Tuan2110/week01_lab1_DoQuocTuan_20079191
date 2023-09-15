package com.edu.iuh.fit.week1.repositories;

import com.edu.iuh.fit.week1.models.Account;
import com.edu.iuh.fit.week1.models.Logs;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class LogRepository {
    public ArrayList<Logs> getAllLogs() throws SQLException, ClassNotFoundException {
        ArrayList<Logs> logs = null;
        Connection con = ConnectDB.getInstance().getConnection();
        String sql = "SELECT * from log";
        try {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            logs = new ArrayList<>();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            while(rs.next()) {
                int id = rs.getInt(1);
                Account account = new Account(rs.getInt(2));
                String logInTime = rs.getString(3);
                String logOutTime = rs.getString(4);
                String note = rs.getString(5);
                Logs log = new Logs(id,account, LocalDateTime.parse(logInTime,formatter), LocalDateTime.parse(logOutTime,formatter),note);
                logs.add(log);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return logs;
    }
    public boolean insertLog(Logs log) throws SQLException, ClassNotFoundException {
        Connection con = ConnectDB.getInstance().getConnection();
        PreparedStatement stm = null;
        int n=0;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String sql = "insert into log(account_id,login_time,logout_time,notes) values(?,?,?,?)";
        try {
            stm = con.prepareStatement(sql);
            stm.setInt(1,log.getAccount().getAccountId());
            stm.setString(2,log.getLoginTime().toString());
            stm.setString(3,log.getLoginTime().toString());
            stm.setString(4,log.getNotes());
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
    public boolean updateLog(Logs logs) throws SQLException, ClassNotFoundException {
        Connection con = ConnectDB.getInstance().getConnection();
        PreparedStatement stm = null;
        int n=0;
        String sql = "Update log set logout_time = ?,notes=? where id=?";
        try {
            stm = con.prepareStatement(sql);
            stm.setString(1,logs.getLogoutTime().toString());
            stm.setString(2,logs.getNotes());
            stm.setInt(3,logs.getId());
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
