package com.edu.iuh.fit.week1.controllers;

import java.io.*;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.edu.iuh.fit.week1.models.*;
import com.edu.iuh.fit.week1.repositories.AccountRepository;
import com.edu.iuh.fit.week1.repositories.GrantAccessRepository;
import com.edu.iuh.fit.week1.repositories.RoleRepository;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "controllerServlet", value = "/week1")
public class ControllerServlet extends HttpServlet {

    private final AccountRepository accountRepository = new AccountRepository();
    private final GrantAccessRepository grantAccessRepository = new GrantAccessRepository();
    private final RoleRepository roleRepository = new RoleRepository();
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String action = request.getParameter("action");
        if(action.equals("role")){
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
        try {
            if(accountRepository.login(email,password).isPresent()){
                url = "/index.jsp";
            }else {
                url = "/login.jsp";
                request.setAttribute("error","Email or password is incorrect");
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
}