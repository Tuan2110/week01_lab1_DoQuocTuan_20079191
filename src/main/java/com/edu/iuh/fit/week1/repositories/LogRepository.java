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
                Logs log = null;
                if(logOutTime==null)
                    log = new Logs(id,account, LocalDateTime.parse(logInTime,formatter),note);
                else{
                    log = new Logs(id,account, LocalDateTime.parse(logInTime,formatter), LocalDateTime.parse(logOutTime,formatter),note);
                }
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
        String sql = "insert into log(account_id,login_time,notes) values(?,?,?)";
        try {
            stm = con.prepareStatement(sql);
            stm.setInt(1,log.getAccount().getAccountId());
            stm.setString(2,log.getLoginTime().toString());
            stm.setString(3,log.getNotes());
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
    public int getLogIdByAccountIdAndNote(int accountId,String  note)throws SQLException, ClassNotFoundException {
        Connection con = ConnectDB.getInstance().getConnection();
        PreparedStatement stm = null;
        int logId=0;
        String sql = "select id from log where account_id=? and notes=?";
        try {
            stm = con.prepareStatement(sql);
            stm.setInt(1,accountId);
            stm.setString(2,note);
            ResultSet rs = stm.executeQuery();
            while(rs.next()){
                logId = rs.getInt(1);
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
        return logId;
    }
    public boolean updateLog(String note,int id,LocalDateTime logOutTime) throws SQLException, ClassNotFoundException {
        Connection con = ConnectDB.getInstance().getConnection();
        PreparedStatement stm = null;
        int n=0;
        String sql = "Update log set logout_time = ?,notes=? where id=?";
        try {
            stm = con.prepareStatement(sql);
            stm.setString(1,logOutTime.toString());
            stm.setString(2,note);
            stm.setInt(3,id);
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
