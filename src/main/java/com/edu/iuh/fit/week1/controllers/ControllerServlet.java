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
import com.edu.iuh.fit.week1.services.AccountService;
import com.edu.iuh.fit.week1.services.GrantAccessService;
import com.edu.iuh.fit.week1.services.LogService;
import com.edu.iuh.fit.week1.services.RoleService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import jakarta.websocket.Session;

@WebServlet(name = "controllerServlet", value = "/week1")
public class ControllerServlet extends HttpServlet {

    private final AccountService accountService = new AccountService();
    private final GrantAccessService grantAccessService = new GrantAccessService();
    private final RoleService roleService = new RoleService();

    private final LogService logService = new LogService();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String action = request.getParameter("action");
        try {
            if (action.equals("home")) {
                request.setAttribute("accounts", accountService.getAllAccount());
                request.setAttribute("grantAccesses", grantAccessService.getAllGrantAccesses());
                request.setAttribute("roles", roleService.getAllRoles());
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/index.jsp");
                rd.forward(request, response);
            } else if (action.equals("log")) {
                request.setAttribute("accounts", accountService.getAllAccount());
                request.setAttribute("logs", logService.getAllLogs());
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/log.jsp");
                rd.forward(request, response);
            } else if (action.equals("role")) {
                String url = "";
                url = "/role.jsp";
                request.setAttribute("accounts", accountService.getAccountsByRole("ADMIN"));
                request.setAttribute("roles", roleService.getAllRoles());
                RequestDispatcher rd = getServletContext().getRequestDispatcher(url);
                rd.forward(request, response);
            } else if (action.equals("change-role")) {
                String url = "";
                String accountId = request.getParameter("account-id");
                String roleName = request.getParameter("role-name");
                url = "/role.jsp";
                request.setAttribute("role-name", roleName);
                request.setAttribute("accounts", accountService.getAccountsByRole(roleName));
                request.setAttribute("roles", roleService.getAllRoles());
                RequestDispatcher rd = getServletContext().getRequestDispatcher(url);
                rd.forward(request, response);
            } else if (action.equals("log-out")) {
                HttpSession session = request.getSession();
                int id = Integer.parseInt(request.getParameter("log-id"));
                logService.updateLog("LogOut", id, LocalDateTime.now());
                session.invalidate();
                String url = "/login.jsp";
                RequestDispatcher rd = getServletContext().getRequestDispatcher(url);
                rd.forward(request, response);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            if (action.equals("login")) {
                login(request, response);
            } else if (action.equals("register")) {
                register(request, response);
            } else if (action.equals("update-account")) {
                updateAccount(request, response);
            } else if (action.equals("delete-account")) {
                deleteAccount(request, response);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteAccount(HttpServletRequest request, HttpServletResponse response) throws SQLException, ClassNotFoundException, ServletException, IOException {
        String accountId = request.getParameter("account-id-delete");
        String url = "";
        try {
            if (accountService.deleteAccount(Integer.parseInt(accountId))) {
                url = "/index.jsp";
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        request.setAttribute("accounts", accountService.getAllAccount());
        request.setAttribute("grantAccesses", grantAccessService.getAllGrantAccesses());
        request.setAttribute("roles", roleService.getAllRoles());
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
            Account account = new Account(Integer.parseInt(accountId), fullName, null, email, phone, Status.valueOf(status));
            if (accountService.updateAccount(account)) {
                url = "/index.jsp";
            }
            grantAccessService.deleteGrantAccessByAcId(Integer.parseInt(accountId));
            if (roles != null) {
                for (String roleName : roles) {
                    Optional<Role> role = roleService.getRoleByName(roleName);
                    grantAccessService.insertGrantAccess(new GrantAccess(role.get(), account, Grant.ENABLE, ""));
                }
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        request.setAttribute("accounts", accountService.getAllAccount());
        request.setAttribute("grantAccesses", grantAccessService.getAllGrantAccesses());
        request.setAttribute("roles", roleService.getAllRoles());
        RequestDispatcher rd = getServletContext().getRequestDispatcher(url);
        rd.forward(request, response);
    }

    private void register(HttpServletRequest request, HttpServletResponse response) throws SQLException, ClassNotFoundException, ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String fullName = request.getParameter("full-name");
        String phone = request.getParameter("phone");
        String cfPassword = request.getParameter("confirm-password");

        request.setAttribute("email", email);
        request.setAttribute("password", password);
        request.setAttribute("full-name", fullName);
        request.setAttribute("phone", phone);
        request.setAttribute("cf-password", cfPassword);
        String url = "";
        boolean error = false;
        if (!password.equals(cfPassword)) {
            request.setAttribute("error-cf-pw", "Password and confirm password is not match");
            error = true;
        }
        if (accountService.getAccountByEmail(email).isPresent()) {
            error = true;
            request.setAttribute("error-email", "Email is already exist");
        }
        if (!email.matches("\\w+@\\w+(\\.\\w+)+")) {
            error = true;
            request.setAttribute("error-email", "Email is not valid");
        }
        if (!fullName.matches("[a-zA-Z ]+")) {
            error = true;
            request.setAttribute("error-full-name", "Full name must contain only letters");
        }
        if (!phone.matches("\\d{10}")) {
            error = true;
            request.setAttribute("error-phone", "Phone must contain 10 digits");
        }
        if (error) {
            url = "/signup.jsp";
        } else {
            url = "/login.jsp";
            accountService.insertAccount(new Account(fullName, password, email, phone, Status.ACTIVE));
        }
        RequestDispatcher rd = getServletContext().getRequestDispatcher(url);
        rd.forward(request, response);
    }

    private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException, ClassNotFoundException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        HttpSession session = request.getSession();
        String url = "";
        Optional<Account> account = accountService.login(email, password);
        if (account.isPresent()) {
            session.setAttribute("account", account.get());
            Logs log = new Logs(account.get(), LocalDateTime.now(), null, "Online");
            logService.insertLog(log);
            session.setAttribute("log_id", logService.getLogId(account.get().getAccountId(), "Online"));
            url = "/index.jsp";
        } else {
            url = "/login.jsp";
            request.setAttribute("error", "Email or password is incorrect");
            request.setAttribute("value_email", email);
            request.setAttribute("value_password", password);
        }
        request.setAttribute("accounts", accountService.getAllAccount());
        request.setAttribute("grantAccesses", grantAccessService.getAllGrantAccesses());
        request.setAttribute("roles", roleService.getAllRoles());
        RequestDispatcher rd = getServletContext().getRequestDispatcher(url);
        rd.forward(request, response);
    }
}