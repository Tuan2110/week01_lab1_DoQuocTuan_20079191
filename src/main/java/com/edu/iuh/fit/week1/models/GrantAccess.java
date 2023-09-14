package com.edu.iuh.fit.week1.models;

public class GrantAccess {
    private Role role;
    private Account account;
    private Grant grant;
    private String note;

    public GrantAccess(Role role, Account account, Grant grant, String note) {
        this.role = role;
        this.account = account;
        this.grant = grant;
        this.note = note;
    }

    public GrantAccess() {
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Grant getGrant() {
        return grant;
    }

    public void setGrant(Grant grant) {
        this.grant = grant;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "GrantAccess{" +
                "role=" + role +
                ", account=" + account +
                ", grant=" + grant +
                ", note='" + note + '\'' +
                '}';
    }
}
