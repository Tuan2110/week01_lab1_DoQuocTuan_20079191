package com.edu.iuh.fit.week1.models;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Logs {
    private int id;
    private Account account;
    private LocalDateTime loginTime;
    private LocalDateTime logoutTime;
    private String notes;

    public Logs(int id, Account account, LocalDateTime loginTime, LocalDateTime logoutTime, String notes) {
        this.id = id;
        this.account = account;
        this.loginTime = loginTime;
        this.logoutTime = logoutTime;
        this.notes = notes;
    }
    public Logs(int id, Account account, LocalDateTime loginTime, String notes) {
        this.id = id;
        this.account = account;
        this.loginTime = loginTime;
        this.notes = notes;
    }

    public Logs(Account account, LocalDateTime loginTime, LocalDateTime logoutTime, String notes) {
        this.account = account;
        this.loginTime = loginTime;
        this.logoutTime = logoutTime;
        this.notes = notes;
    }

    public Logs(int id) {
        this.id = id;
    }

    public Logs() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account accountId) {
        this.account = accountId;
    }

    public LocalDateTime getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(LocalDateTime loginTime) {
        this.loginTime = loginTime;
    }

    public LocalDateTime getLogoutTime() {
        return logoutTime;
    }

    public void setLogoutTime(LocalDateTime logoutTime) {
        this.logoutTime = logoutTime;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "Logs{" +
                "id=" + id +
                ", account=" + account +
                ", loginTime=" + loginTime +
                ", logoutTime=" + logoutTime +
                ", notes='" + notes + '\'' +
                '}';
    }
}
