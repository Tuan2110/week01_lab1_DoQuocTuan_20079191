package com.edu.iuh.fit.week1.services;

import com.edu.iuh.fit.week1.models.Account;
import com.edu.iuh.fit.week1.repositories.AccountRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AccountService {
    AccountRepository accountRepository;
    public AccountService() {
        accountRepository = new AccountRepository();
    }
    public ArrayList<Account> getAllAccount() throws SQLException, ClassNotFoundException {
        return accountRepository.getAllAccount();
    }
    public boolean insertAccount(Account account) throws SQLException, ClassNotFoundException {
        return accountRepository.insertAccount(account);
    }
    public Optional<Account> login(String email, String password) throws SQLException, ClassNotFoundException {
        return accountRepository.login(email,password);
    }
    public  Optional<Account> getAccountById(int id) throws SQLException, ClassNotFoundException {
        return accountRepository.getAccountById(id);
    }
    public Optional<Account> getAccountByEmail(String email) throws SQLException, ClassNotFoundException {
        return accountRepository.getAccountByEmail(email);
    }
    public List<Account> getAccountsByRole(String role) throws SQLException, ClassNotFoundException {
        return accountRepository.getAccountsByRole(role);
    }
    public boolean updateAccount(Account account) throws SQLException, ClassNotFoundException {
        return accountRepository.updateAccount(account);
    }
    public boolean deleteAccount(int id) throws SQLException, ClassNotFoundException {
        return accountRepository.deleteAccount(id);
    }
}
