package com.edu.iuh.fit.week1.controllers;

import java.io.*;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.edu.iuh.fit.week1.models.*;
import com.edu.iuh.fit.week1.repositories.AccountRepository;
import com.edu.iuh.fit.week1.repositories.GrantAccessRepository;
import com.edu.iuh.fit.week1.repositories.LogRepository;
import com.edu.iuh.fit.week1.repositories.RoleRepository;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import jakarta.websocket.Session;

@WebServlet(name = "controllerServlet", value = "/week1")
public class ControllerServlet extends HttpServlet {

    private final AccountRepository accountRepository = new AccountRepository();
    private final GrantAccessRepository grantAccessRepository = new GrantAccessRepository();
    private final RoleRepository roleRepository = new RoleRepository();

    private final LogRepository logRepository = new LogRepository();
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String action = request.getParameter("action");
        if(action.equals("home")){
            try {
                request.setAttribute("accounts",accountRepository.getAllAccount());
                request.setAttribute("grantAccesses",grantAccessRepository.getAllGranAccesses());
                request.setAttribute("roles",roleRepository.getAllRoles());
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/index.jsp");
            rd.forward(request, response);
        }else if(action.equals("log")){
            try {
                request.setAttribute("accounts",accountRepository.getAllAccount());
                request.setAttribute("logs",logRepository.getAllLogs());
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/log.jsp");
            rd.forward(request, response);
        }else if(action.equals("role")){
            String url = "";
            url = "/role.jsp";
            try {
                request.setAttribute("accounts",accountRepository.getAccountsByRole("ADMIN"));
                request.setAttribute("roles",roleRepository.getAllRoles());
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            RequestDispatcher rd = getServletContext().getRequestDispatcher(url);
            rd.forward(request, response);
        }else if(action.equals("change-role")){
            String url = "";
            String accountId = request.getParameter("account-id");
            String roleName = request.getParameter("role-name");
            try {
                url = "/role.jsp";
                request.setAttribute("role-name",roleName);
                request.setAttribute("accounts",accountRepository.getAccountsByRole(roleName));
                request.setAttribute("roles",roleRepository.getAllRoles());
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            RequestDispatcher rd = getServletContext().getRequestDispatcher(url);
            rd.forward(request, response);
        }else if(action.equals("log-out")){
            HttpSession session = request.getSession();
            int id = Integer.parseInt(request.getParameter("log-id"));
            try {
                logRepository.updateLog(new Logs(id,null,LocalDateTime.MIN,LocalDateTime.now(),"Offline"));
                session.invalidate();
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if(action.equals("login")){
            try {
                login(request,response);
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }else if(action.equals("register")){
            try {
                register(request,response);
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }else if(action.equals("update-account")){
            try {
                updateAccount(request,response);
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }else if(action.equals("delete-account")) {
            try {
                deleteAccount(request, response);
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void deleteAccount(HttpServletRequest request, HttpServletResponse response) throws SQLException, ClassNotFoundException, ServletException, IOException {
        String accountId = request.getParameter("account-id-delete");
        String url = "";
        try {
            if(accountRepository.deleteAccount(Integer.parseInt(accountId))){
                url = "/index.jsp";
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        request.setAttribute("accounts",accountRepository.getAllAccount());
        request.setAttribute("grantAccesses",grantAccessRepository.getAllGranAccesses());
        request.setAttribute("roles",roleRepository.getAllRoles());
        RequestDispatcher rd = getServletContext().getRequestDispatcher(url);
        rd.forward(request, response);
    }

    private void updateAccount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException, ClassNotFoundException {
            String accountId = request.getParameter("account-id");
            String fullName = request.getParameter("full-name");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            String status = request.getParameter("status");
            String[] roles = request.getParameterValues("role");
            String url = "";
            try {
                Account account = new Account(Integer.parseInt(accountId),fullName,null,email,phone, Status.valueOf(status));
                if(accountRepository.updateAccount(account)){
                    url = "/index.jsp";
                }
                grantAccessRepository.deleteGrantAccessByAcId(Integer.parseInt(accountId));
                if(roles!=null) {
                    for (String roleName : roles) {
                        Optional<Role> role = roleRepository.getRoleByName(roleName);
                        grantAccessRepository.insertGrantAccess(new GrantAccess(role.get(), account, Grant.ENABLE, ""));
                    }
                }

            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        request.setAttribute("accounts",accountRepository.getAllAccount());
        request.setAttribute("grantAccesses",grantAccessRepository.getAllGranAccesses());
        request.setAttribute("roles",roleRepository.getAllRoles());
        RequestDispatcher rd = getServletContext().getRequestDispatcher(url);
        rd.forward(request, response);
    }

    private void register(HttpServletRequest request, HttpServletResponse response) throws SQLException, ClassNotFoundException, ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String fullName = request.getParameter("full-name");
        String phone = request.getParameter("phone");
        String cfPassword = request.getParameter("confirm-password");
        String url = "";
        if(!password.equals(cfPassword)){
            url = "/signup.jsp";
            request.setAttribute("error","Password and confirm password is not match");
        }else if(accountRepository.getAccountByEmail(email).isPresent()){
            url = "/signup.jsp";
            request.setAttribute("error","Email is already exist");
        }else{
            url = "/login.jsp";
            accountRepository.insertAccount(new Account(fullName,password,email,phone, Status.DELETED));
        }
        RequestDispatcher rd = getServletContext().getRequestDispatcher(url);
        rd.forward(request, response);
    }

    private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException, ClassNotFoundException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String url = "";
        Optional<Account> account = accountRepository.login(email,password);
        if(account.isPresent()){
            url = "/index.jsp";
        }else {
            url = "/login.jsp";
            request.setAttribute("error","Email or password is incorrect");
        }
        Logs log = new Logs(account.get(), LocalDateTime.now(),LocalDateTime.MIN,"Online");
        logRepository.insertLog(log);
        HttpSession session = request.getSession();
        session.setAttribute("logs",log);
        System.out.println(log);
        request.setAttribute("accounts",accountRepository.getAllAccount());
        request.setAttribute("grantAccesses",grantAccessRepository.getAllGranAccesses());
        request.setAttribute("roles",roleRepository.getAllRoles());
        RequestDispatcher rd = getServletContext().getRequestDispatcher(url);
        rd.forward(request, response);
    }
}