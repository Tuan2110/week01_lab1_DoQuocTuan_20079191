package com.edu.iuh.fit.week1.services;

import com.edu.iuh.fit.week1.models.Logs;
import com.edu.iuh.fit.week1.repositories.LogRepository;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class LogService {
    LogRepository logRepository;
    public LogService(){
        logRepository = new LogRepository();
    }
    public List<Logs> getAllLogs() throws SQLException, ClassNotFoundException {
        return logRepository.getAllLogs();
    }
    public boolean insertLog(Logs log) throws SQLException, ClassNotFoundException {
        return logRepository.insertLog(log);
    }
    public boolean updateLog(String note, int id, LocalDateTime logOutTime) throws SQLException, ClassNotFoundException {
        return logRepository.updateLog(note,id,logOutTime);
    }
    public int getLogId(int accountId, String note) throws SQLException, ClassNotFoundException {
        return logRepository.getLogIdByAccountIdAndNote(accountId,note);
    }

}
